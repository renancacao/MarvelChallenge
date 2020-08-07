package com.rcacao.marvelchallenge.domain.usecases

import com.rcacao.marvelchallenge.data.repository.comics.ComicsRepository
import com.rcacao.marvelchallenge.domain.model.DataResult
import com.rcacao.marvelchallenge.domain.model.comics.ComicsModel
import javax.inject.Inject

class GetComicsUseCase @Inject constructor(private val repository: ComicsRepository) :
    UseCase<String, DataResult<List<ComicsModel>>> {
    override suspend operator fun invoke(requestData: String): DataResult<List<ComicsModel>> =
        repository.getComics(requestData)
}