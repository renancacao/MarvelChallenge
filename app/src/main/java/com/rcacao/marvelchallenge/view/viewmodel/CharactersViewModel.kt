package com.rcacao.marvelchallenge.view.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.rcacao.marvelchallenge.domain.model.character.CharacterModel
import com.rcacao.marvelchallenge.domain.usecases.GetCharactersPagingUseCase.CharacterPagingRequest
import com.rcacao.marvelchallenge.domain.usecases.UseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ExperimentalCoroutinesApi
class CharactersViewModel @ViewModelInject @Inject constructor(
    private val getCharactersPagingUseCase: UseCase<CharacterPagingRequest, Flow<PagingData<CharacterModel>>>
) :
    ViewModel() {

    var currentQuery: String = ""

    suspend fun searchCharacter(queryString: String): Flow<PagingData<CharacterModel>> {
        val sameQuery: Boolean = queryString == currentQuery
        currentQuery = queryString
        return getCharactersPagingUseCase(CharacterPagingRequest(queryString, sameQuery)).cachedIn(
            viewModelScope
        )
    }

}