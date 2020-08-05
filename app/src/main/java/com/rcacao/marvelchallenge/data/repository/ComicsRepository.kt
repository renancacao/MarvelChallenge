package com.rcacao.marvelchallenge.data.repository

import com.rcacao.marvelchallenge.data.api.MarvelWebService
import com.rcacao.marvelchallenge.data.mapper.Mapper
import com.rcacao.marvelchallenge.domain.model.DataResult
import com.rcacao.marvelchallenge.data.model.comic.ComicsDataResponse
import javax.inject.Inject

class ComicsRepository @Inject constructor(
    private val webService: MarvelWebService,
    private val comicsMapper:
    Mapper<ComicsDataResponse, String>
) {

    suspend fun getComics(charId: String): DataResult<String> {
        return try {
            DataResult.Success(comicsMapper.map(webService.loadComics(charId)))
        } catch (ex: Exception) {
            DataResult.Error(ex)
        }
    }

}
