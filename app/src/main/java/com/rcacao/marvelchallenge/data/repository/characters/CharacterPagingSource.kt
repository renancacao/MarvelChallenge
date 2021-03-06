package com.rcacao.marvelchallenge.data.repository.characters

import androidx.paging.PagingSource
import com.rcacao.marvelchallenge.data.ApiHelper
import com.rcacao.marvelchallenge.data.api.MarvelWebService
import com.rcacao.marvelchallenge.data.model.character.CharacterResponse
import com.rcacao.marvelchallenge.data.model.character.CharactersDataResponse
import com.rcacao.marvelchallenge.utils.ConnectionHelper
import com.rcacao.marvelchallenge.utils.EmptyListException
import com.rcacao.marvelchallenge.utils.NoNetworkingException
import retrofit2.HttpException
import java.io.IOException

class CharacterPagingSource(
    private val query: String,
    private val webService: MarvelWebService,
    private val apiHelper: ApiHelper,
    private val connectionHelper: ConnectionHelper
) :
    PagingSource<Int, CharacterResponse>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CharacterResponse> {

        val offset: Int = (params.key ?: 0)
        val limit: Int = params.loadSize
        val ts: String = apiHelper.getTimeStamp()
        val hash: String = apiHelper.getHash(ts)
        val key: String = apiHelper.getPublicKey()
        val orderBy: String = apiHelper.getOrderBy()

        return try {
            if (!connectionHelper.isConnected()) {
                LoadResult.Error(NoNetworkingException())
            } else {
                val response: CharactersDataResponse =
                    loadCharacters(ts, hash, offset, limit, orderBy, key, query)
                val characters: List<CharacterResponse> = response.data?.characters ?: emptyList()
                if (characters.isEmpty()) {
                    LoadResult.Error(EmptyListException())
                } else {
                    LoadResult.Page(
                        data = characters,
                        prevKey = if (offset == 0) null else offset - limit,
                        nextKey = if (characters.isEmpty()) null else offset + limit
                    )
                }
            }
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
