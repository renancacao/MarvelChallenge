package com.rcacao.marvelchallenge.view.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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

    private val mutableSelectedCharacter = MutableLiveData<CharacterModel>()
    val selectedCharacter: LiveData<CharacterModel>
        get() = mutableSelectedCharacter

    fun searchCharacter(queryString: String): Flow<PagingData<CharacterModel>> {
        return repository.getCharacters(queryString).cachedIn(viewModelScope)
    }

    fun selectCharacter(characterModel: CharacterModel) {
        mutableSelectedCharacter.value = characterModel
    }

}