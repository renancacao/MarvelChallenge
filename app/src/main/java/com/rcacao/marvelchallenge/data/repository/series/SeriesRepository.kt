package com.rcacao.marvelchallenge.data.repository.series

import com.rcacao.marvelchallenge.domain.model.DataResult
import com.rcacao.marvelchallenge.domain.model.series.SeriesModel

interface SeriesRepository {
    suspend fun getSeries(charId: String): DataResult<List<SeriesModel>>
}