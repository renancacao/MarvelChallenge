package com.rcacao.marvelchallenge.data.datasource

import androidx.paging.PositionalDataSource
import com.rcacao.marvelchallenge.data.CharacterResponse
import com.rcacao.marvelchallenge.data.CharactersDataResponse
import com.rcacao.marvelchallenge.data.api.MarvelWebService
import com.rcacao.marvelchallenge.utils.ApiHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import javax.inject.Inject


class CharactersDataSource @Inject constructor(
    private val coroutineScope: CoroutineScope,
    private val webService: MarvelWebService,
    private val apiHelper: ApiHelper
) :
    PositionalDataSource<CharacterResponse>() {

    override fun loadRange(
        params: LoadRangeParams,
        callback: LoadRangeCallback<CharacterResponse>
    ) {
        coroutineScope.launch {
            val response: CharactersDataResponse =
                getCharactersResponse(params.startPosition, params.loadSize)
            callback.onResult(response.data?.characters ?: emptyList())
        }
    }

    override fun loadInitial(
        params: LoadInitialParams,
        callback: LoadInitialCallback<CharacterResponse>
    ) {
        coroutineScope.launch {
            val response: CharactersDataResponse =
                getCharactersResponse(params.requestedStartPosition, params.requestedLoadSize)
            callback.onResult(
                response.data?.characters ?: emptyList(),
                0,
                response.data?.total ?: 0
            )
        }
    }

    private suspend fun getCharactersResponse(
        startPosition: Int,
        loadCount: Int
    ): CharactersDataResponse {
        val ts: String = apiHelper.getTimeStamp()
        val hash: String = apiHelper.getHash(ts)
        return webService.loadCharacters(
            ts,
            hash,
            startPosition,
            loadCount,
            apiHelper.getOrderBy(),
            apiHelper.getPublicKey()
        )
    }

    override fun invalidate() {
        super.invalidate()
        coroutineScope.cancel(null)
    }

}