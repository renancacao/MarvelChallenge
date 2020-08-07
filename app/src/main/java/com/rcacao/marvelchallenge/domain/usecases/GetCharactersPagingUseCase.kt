package com.rcacao.marvelchallenge.domain.usecases

import androidx.paging.PagingData
import com.rcacao.marvelchallenge.data.repository.characters.CharactersRepository
import com.rcacao.marvelchallenge.domain.model.character.CharacterModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class GetCharactersPagingUseCase @Inject constructor(private val repository: CharactersRepository) :
    UseCase<GetCharactersPagingUseCase.CharacterPagingRequest, Flow<@JvmSuppressWildcards PagingData<CharacterModel>>> {


    private var currentResult: Flow<PagingData<CharacterModel>>? = null

    override suspend operator fun invoke(requestData: CharacterPagingRequest): Flow<PagingData<CharacterModel>> {
        val lastResult: Flow<PagingData<CharacterModel>>? = currentResult
        if (requestData.sameQuery && lastResult != null) {
            return lastResult
        }
        val newResult: Flow<PagingData<CharacterModel>> =
            repository.getCharacters(requestData.queryString)
        currentResult = newResult
        return newResult
    }

    data class CharacterPagingRequest(
        val queryString: String,
        val sameQuery: Boolean
    )
}