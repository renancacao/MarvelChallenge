package com.rcacao.marvelchallenge.data.repository

import com.rcacao.marvelchallenge.data.api.MarvelWebService
import com.rcacao.marvelchallenge.data.mapper.Mapper
import com.rcacao.marvelchallenge.domain.model.DataResult
import com.rcacao.marvelchallenge.data.model.comics.ComicsDataResponse
import com.rcacao.marvelchallenge.domain.model.comics.ComicsModel
import javax.inject.Inject

class ComicsRepository @Inject constructor(
    private val webService: MarvelWebService,
    private val comicsMapper: Mapper<ComicsDataResponse, List<ComicsModel>>
) {

    suspend fun getComics(charId: String): DataResult<List<ComicsModel>> {
        return try {
            DataResult.Success(comicsMapper.map(webService.loadComics(charId)))
        } catch (ex: Exception) {
            DataResult.Error(ex)
        }
    }

}
