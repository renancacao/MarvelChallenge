package com.rcacao.marvelchallenge.data.repository.characters

import androidx.paging.PagingData
import com.rcacao.marvelchallenge.data.model.character.CharacterResponse
import com.rcacao.marvelchallenge.domain.model.DataResult
import com.rcacao.marvelchallenge.domain.model.character.CharacterModel
import kotlinx.coroutines.flow.Flow

interface CharactersRepository {
    suspend fun getCharacters(query: String, forceLast: Boolean): Flow<PagingData<CharacterModel>>
    fun getRemoteCharacters(query: String): Flow<PagingData<CharacterResponse>>

    suspend fun saveFavorite(characterModel: CharacterModel): DataResult<Unit>

    suspend fun deleteFavorite(id: String): DataResult<Unit>

    suspend fun getFavorites(query: String): DataResult<ArrayList<CharacterModel>>
}