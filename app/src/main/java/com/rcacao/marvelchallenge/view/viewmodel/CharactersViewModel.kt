package com.rcacao.marvelchallenge.view.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.rcacao.marvelchallenge.di.UseCasesModule.DeleteUseCase
import com.rcacao.marvelchallenge.di.UseCasesModule.SaveUseCase
import com.rcacao.marvelchallenge.domain.model.DataResult
import com.rcacao.marvelchallenge.domain.model.character.CharacterModel
import com.rcacao.marvelchallenge.domain.usecases.GetCharactersPagingUseCase.CharacterPagingRequest
import com.rcacao.marvelchallenge.domain.usecases.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

class CharactersViewModel @ViewModelInject @Inject constructor(
    private val getCharactersPagingUseCase: UseCase<CharacterPagingRequest, Flow<PagingData<CharacterModel>>>,
    @SaveUseCase private val saveFavoriteUseCase: UseCase<CharacterModel, DataResult<Unit>>,
    @DeleteUseCase private val deleteFavoriteUseCase: UseCase<CharacterModel, DataResult<Unit>>
) :
    ViewModel() {

    var currentPosition: Int = 0
    var currentQuery: String = ""

    suspend fun searchCharacter(queryString: String): Flow<PagingData<CharacterModel>> {
        val sameQuery: Boolean = queryString == currentQuery
        currentQuery = queryString
        return getCharactersPagingUseCase(CharacterPagingRequest(queryString, sameQuery)).cachedIn(
            viewModelScope
        )
    }

    fun starCharacter(characterModel: CharacterModel) {
        val useCase: UseCase<CharacterModel, DataResult<Unit>> =
            if (!characterModel.isFavorite) saveFavoriteUseCase else deleteFavoriteUseCase
        viewModelScope.launch {
            useCase(characterModel)
            characterModel.isFavorite = !characterModel.isFavorite
        }
    }
}