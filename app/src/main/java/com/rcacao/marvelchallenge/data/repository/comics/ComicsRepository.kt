package com.rcacao.marvelchallenge.data.repository.comics

import com.rcacao.marvelchallenge.data.api.MarvelWebService
import com.rcacao.marvelchallenge.data.mapper.Mapper
import com.rcacao.marvelchallenge.data.model.comics.ComicsDataResponse
import com.rcacao.marvelchallenge.domain.model.DataResult
import com.rcacao.marvelchallenge.domain.model.comics.ComicsModel
import com.rcacao.marvelchallenge.utils.ApiHelper
import javax.inject.Inject

class ComicsRepository @Inject constructor(
    private val webService: MarvelWebService,
    private val apiHelper: ApiHelper,
    private val comicsMapper: Mapper<ComicsDataResponse, List<ComicsModel>>
) {

    suspend fun getComics(charId: String): DataResult<List<ComicsModel>> {
        return try {
            DataResult.Success(comicsMapper.map(loadComics(charId)))
        } catch (ex: Exception) {
            DataResult.Error(ex)
        }
    }

    private suspend fun loadComics(charId: String): ComicsDataResponse {
        val ts: String = apiHelper.getTimeStamp()
        val hash: String = apiHelper.getHash(ts)
        val key: String = apiHelper.getPublicKey()
        return webService.loadComics(charId, ts, hash, key)
    }

}
