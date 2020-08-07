package com.rcacao.marvelchallenge.domain.usecases

import androidx.paging.PagingData
import com.rcacao.marvelchallenge.data.repository.characters.CharactersRepository
import com.rcacao.marvelchallenge.domain.model.character.CharacterModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ExperimentalCoroutinesApi
class GetCharactersPagingUseCase @Inject constructor(private val repository: CharactersRepository) :
    UseCase<GetCharactersPagingUseCase.CharacterPagingRequest, Flow<@JvmSuppressWildcards PagingData<CharacterModel>>> {

    override suspend operator fun invoke(requestData: CharacterPagingRequest): Flow<PagingData<CharacterModel>> =
        repository.getCharacters(requestData.queryString, requestData.sameQuery)

    data class CharacterPagingRequest(
        val queryString: String,
        val sameQuery: Boolean
    )
}