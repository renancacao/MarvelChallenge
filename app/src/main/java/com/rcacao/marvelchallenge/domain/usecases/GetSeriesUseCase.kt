package com.rcacao.marvelchallenge.domain.usecases

import com.rcacao.marvelchallenge.data.repository.series.SeriesRepository
import com.rcacao.marvelchallenge.domain.model.DataResult
import com.rcacao.marvelchallenge.domain.model.series.SeriesModel
import javax.inject.Inject

class GetSeriesUseCase @Inject constructor(private val repository: SeriesRepository) :
    UseCase<String, DataResult<List<SeriesModel>>> {
    override suspend operator fun invoke(requestData: String): DataResult<List<SeriesModel>> =
        repository.getSeries(requestData)
}