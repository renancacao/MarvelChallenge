package com.rcacao.marvelchallenge.domain.usecases

import com.rcacao.marvelchallenge.data.repository.comics.ComicsRepository
import com.rcacao.marvelchallenge.domain.model.DataResult
import com.rcacao.marvelchallenge.domain.model.comics.ComicsModel
import javax.inject.Inject

class GetComicsUseCase @Inject constructor(private val repository: ComicsRepository) {
    suspend operator fun invoke(charId: String): DataResult<List<ComicsModel>> =
        repository.getComics(charId)
}