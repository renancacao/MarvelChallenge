package com.rcacao.marvelchallenge.domain.usecases

import com.rcacao.marvelchallenge.data.repository.comics.ComicsRepository
import com.rcacao.marvelchallenge.domain.NoNetworkingException
import com.rcacao.marvelchallenge.domain.model.DataResult
import com.rcacao.marvelchallenge.domain.model.comics.ComicsModel
import com.rcacao.marvelchallenge.utils.ConnectionHelper
import javax.inject.Inject

class GetComicsUseCase @Inject constructor(
    private val repository: ComicsRepository,
    private val connectionHelper: ConnectionHelper
) :
    UseCase<String, DataResult<@JvmSuppressWildcards List<ComicsModel>>> {
    override suspend operator fun invoke(requestData: String): DataResult<List<ComicsModel>> {
        return if (!connectionHelper.isConnected()) {
            DataResult.Error(NoNetworkingException())
        } else {
            repository.getComics(requestData)
        }
    }

}