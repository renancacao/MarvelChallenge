package com.rcacao.marvelchallenge.data

import com.rcacao.marvelchallenge.data.api.MarvelWebService
import com.rcacao.marvelchallenge.data.model.character.CharactersDataResponse
import com.rcacao.marvelchallenge.data.model.comics.ComicsDataResponse
import com.rcacao.marvelchallenge.data.model.series.SeriesDataResponse

class FakeWebService(private val characterDataResponse: CharactersDataResponse) : MarvelWebService {
    override suspend fun loadCharacters(
        ts: String,
        hash: String,
        offset: Int,
        limit: Int,
        orderBy: String,
        apikey: String
    ): CharactersDataResponse {
        return characterDataResponse
    }

    override suspend fun loadCharactersByName(
        ts: String,
        hash: String,
        offset: Int,
        limit: Int,
        orderBy: String,
        apikey: String,
        nameStartWith: String
    ): CharactersDataResponse {
        return characterDataResponse
    }

    override suspend fun loadComics(
        charId: String,
        ts: String,
        hash: String,
        apikey: String,
        limit: Int
    ): ComicsDataResponse {
        TODO("Not yet implemented")
    }

    override suspend fun loadSeries(
        charId: String,
        ts: String,
        hash: String,
        apikey: String,
        limit: Int
    ): SeriesDataResponse {
        TODO("Not yet implemented")
    }

}
