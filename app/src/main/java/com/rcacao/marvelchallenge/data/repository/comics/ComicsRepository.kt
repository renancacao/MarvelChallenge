package com.rcacao.marvelchallenge.data.repository.comics

import com.rcacao.marvelchallenge.domain.model.DataResult
import com.rcacao.marvelchallenge.domain.model.comics.ComicsModel

interface ComicsRepository {
    suspend fun getComics(charId: String): DataResult<List<ComicsModel>>
}
