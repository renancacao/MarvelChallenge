package com.rcacao.marvelchallenge.data.repository.series

import com.rcacao.marvelchallenge.data.ApiHelper
import com.rcacao.marvelchallenge.data.api.MarvelWebService
import com.rcacao.marvelchallenge.data.mapper.Mapper
import com.rcacao.marvelchallenge.data.model.series.SeriesDataResponse
import com.rcacao.marvelchallenge.domain.model.DataResult
import com.rcacao.marvelchallenge.domain.model.series.SeriesModel
import javax.inject.Inject

class SeriesRepositoryImpl @Inject constructor(
    private val webService: MarvelWebService,
    private val apiHelper: ApiHelper,
    private val comicsMapper: Mapper<SeriesDataResponse, List<SeriesModel>>
) : SeriesRepository {

    override suspend fun getSeries(charId: String): DataResult<List<SeriesModel>> {
        return try {
            DataResult.Success(comicsMapper.map(loadSeries(charId)))
        } catch (ex: Exception) {
            DataResult.Error(ex)
        }
    }

    private suspend fun loadSeries(charId: String): SeriesDataResponse {
        val ts: String = apiHelper.getTimeStamp()
        val hash: String = apiHelper.getHash(ts)
        val key: String = apiHelper.getPublicKey()
        return webService.loadSeries(charId, ts, hash, key)
    }

}
