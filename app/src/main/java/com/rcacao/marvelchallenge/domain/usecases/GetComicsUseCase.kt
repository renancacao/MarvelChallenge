package com.rcacao.marvelchallenge.domain.usecases

import com.rcacao.marvelchallenge.data.repository.ComicsRepository
import com.rcacao.marvelchallenge.domain.model.DataResult
import javax.inject.Inject

class GetComicsUseCase @Inject constructor(private val repository: ComicsRepository) {
    suspend operator fun invoke(charId: String): DataResult<String> = repository.getComics(charId)
}