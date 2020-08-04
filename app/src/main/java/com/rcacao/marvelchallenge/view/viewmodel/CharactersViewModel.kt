package com.rcacao.marvelchallenge.view.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.rcacao.marvelchallenge.data.repository.CharactersRepository
import com.rcacao.marvelchallenge.domain.model.CharacterModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CharactersViewModel @ViewModelInject @Inject constructor(private val repository: CharactersRepository) :
    ViewModel() {

    fun searchCharacter(queryString: String): Flow<PagingData<CharacterModel>> {
        return repository.getCharacters(queryString).cachedIn(viewModelScope)
    }

}