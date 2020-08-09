package com.rcacao.marvelchallenge.data.repository.characters

import androidx.paging.PagingSource
import com.rcacao.marvelchallenge.data.ApiHelper
import com.rcacao.marvelchallenge.data.api.MarvelWebService
import com.rcacao.marvelchallenge.data.model.character.CharacterResponse
import com.rcacao.marvelchallenge.data.model.character.CharactersDataResponse
import com.rcacao.marvelchallenge.domain.NoNetworkingException
import com.rcacao.marvelchallenge.utils.ConnectionHelper
import com.rcacao.marvelchallenge.utils.EmptyListException
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException

class CharacterPagingSource(
    private val query: String,
    private val webService: MarvelWebService,
    private val apiHelper: ApiHelper,
    private val connectionHelper: ConnectionHelper
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
            if (!connectionHelper.isConnected()) {
                return LoadResult.Error(NoNetworkingException())
            }
            val response: CharactersDataResponse =
                loadCharacters(ts, hash, offset, limit, orderBy, key, query)
            val characters: List<CharacterResponse> = response.data?.characters ?: emptyList()
            if (characters.isEmpty()) {
                return LoadResult.Error(EmptyListException())
            }
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