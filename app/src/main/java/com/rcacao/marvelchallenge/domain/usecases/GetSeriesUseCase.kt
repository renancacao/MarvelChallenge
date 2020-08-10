package com.rcacao.marvelchallenge.domain.usecases

import com.rcacao.marvelchallenge.data.repository.series.SeriesRepository
import com.rcacao.marvelchallenge.domain.model.DataResult
import com.rcacao.marvelchallenge.domain.model.series.SeriesModel
import com.rcacao.marvelchallenge.utils.ConnectionHelper
import com.rcacao.marvelchallenge.utils.NoNetworkingException
import javax.inject.Inject

class GetSeriesUseCase @Inject constructor(
    private val repository: SeriesRepository,
    private val connectionHelper: ConnectionHelper
) :
    UseCase<String, DataResult<@JvmSuppressWildcards List<SeriesModel>>> {
    override suspend operator fun invoke(requestData: String): DataResult<List<SeriesModel>> {
        return if (!connectionHelper.isConnected()) {
            DataResult.Error(NoNetworkingException())
        } else {
            repository.getSeries(requestData)
        }
    }
}
