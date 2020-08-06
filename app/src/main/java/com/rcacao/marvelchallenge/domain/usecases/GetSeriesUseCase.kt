package com.rcacao.marvelchallenge.domain.usecases

import com.rcacao.marvelchallenge.data.repository.series.SeriesRepository
import com.rcacao.marvelchallenge.domain.model.DataResult
import com.rcacao.marvelchallenge.domain.model.series.SeriesModel
import javax.inject.Inject

class GetSeriesUseCase @Inject constructor(private val repository: SeriesRepository) {
    suspend operator fun invoke(charId: String): DataResult<List<SeriesModel>> =
        repository.getSeries(charId)
}