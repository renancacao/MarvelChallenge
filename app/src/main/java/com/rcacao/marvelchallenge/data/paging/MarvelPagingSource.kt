package com.rcacao.marvelchallenge.data.paging

import androidx.paging.PagingSource
import com.rcacao.marvelchallenge.data.CharacterResponse
import com.rcacao.marvelchallenge.data.CharactersDataResponse
import com.rcacao.marvelchallenge.data.api.MarvelWebService
import com.rcacao.marvelchallenge.utils.ApiHelper
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException

class MarvelPagingSource(
    private val query: String,
    private val webService: MarvelWebService,
    private val apiHelper: ApiHelper
) :
    PagingSource<Int, CharacterResponse>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CharacterResponse> {

        //TODO: verificar se dispara apenas uma chamada ao iniciar o app
        val offset: Int = (params.key ?: 0)
        val limit: Int = params.loadSize
        val ts: String = apiHelper.getTimeStamp()
        val hash: String = apiHelper.getHash(ts)
        val key: String = apiHelper.getPublicKey()
        val orderBy: String = apiHelper.getOrderBy()

        return try {
            Timber.d("Chamada de API")
            val response: CharactersDataResponse =
                loadCharacters(ts, hash, offset, limit, orderBy, key, query)
            val characters: List<CharacterResponse> = response.data?.characters ?: emptyList()
            LoadResult.Page(
                data = characters,
                prevKey = if (offset == 0) null else offset - limit,
                nextKey = if (characters.isEmpty()) null else offset + limit
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }

    }

    private suspend fun loadCharacters(
        ts: String,
        hash: String,
        offset: Int,
        limit: Int,
        orderBy: String,
        key: String,
        query: String
    ): CharactersDataResponse {
        val trimQuery: String = query.trim()
        return if (trimQuery.isNotEmpty()) {
            webService.loadCharactersByName(ts, hash, offset, limit, orderBy, key, trimQuery)
        } else {
            webService.loadCharacters(ts, hash, offset, limit, orderBy, key)
        }
    }

}