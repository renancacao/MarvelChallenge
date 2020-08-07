package com.rcacao.marvelchallenge.domain.usecases

import androidx.paging.PagingData
import com.rcacao.marvelchallenge.data.repository.characters.CharactersRepository
import com.rcacao.marvelchallenge.domain.model.character.CharacterModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class GetCharactersPagingUseCase @Inject constructor(private val repository: CharactersRepository) {

    private var currentResult: Flow<PagingData<CharacterModel>>? = null

    suspend operator fun invoke(
        queryString: String,
        sameQuery: Boolean
    ): Flow<PagingData<CharacterModel>> {
        val lastResult: Flow<PagingData<CharacterModel>>? = currentResult
        if (sameQuery && lastResult != null) {
            return lastResult
        }
        val newResult: Flow<PagingData<CharacterModel>> =
            repository.getCharacters(queryString)
        currentResult = newResult
        return newResult
    }

}