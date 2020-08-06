package com.rcacao.marvelchallenge.view.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.rcacao.marvelchallenge.data.repository.characters.CharactersRepository
import com.rcacao.marvelchallenge.domain.model.character.CharacterModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CharactersViewModel @ViewModelInject @Inject constructor(private val repository: CharactersRepository) :
    ViewModel() {

    var currentPosition: Int = 0
    var currentQuery: String = ""
    private var currentResult: Flow<PagingData<CharacterModel>>? = null

    suspend fun searchCharacter(queryString: String): Flow<PagingData<CharacterModel>> {
        val lastResult: Flow<PagingData<CharacterModel>>? = currentResult
        if (queryString == currentQuery && lastResult != null) {
            return lastResult
        }
        currentQuery = queryString
        val newResult: Flow<PagingData<CharacterModel>> =
            repository.getCharacters(currentQuery).cachedIn(viewModelScope)
        currentResult = newResult
        return newResult
    }

    fun savePosition(position: Int){
        currentPosition = position
    }

}