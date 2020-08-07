package com.rcacao.marvelchallenge.view.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.rcacao.marvelchallenge.domain.model.character.CharacterModel
import com.rcacao.marvelchallenge.domain.usecases.GetCharactersPagingUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CharactersViewModel @ViewModelInject @Inject constructor(private val getCharactersPagingUseCase: GetCharactersPagingUseCase) :
    ViewModel() {

    var currentPosition: Int = 0
    var currentQuery: String = ""

    suspend fun searchCharacter(queryString: String): Flow<PagingData<CharacterModel>> {
        val sameQuery: Boolean = queryString == currentQuery
        currentQuery = queryString
        return getCharactersPagingUseCase(queryString, sameQuery).cachedIn(viewModelScope)
    }


    fun savePosition(position: Int) {
        currentPosition = position
    }

}