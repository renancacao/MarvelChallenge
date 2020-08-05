package com.rcacao.marvelchallenge.view.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rcacao.marvelchallenge.data.repository.CharactersRepository
import com.rcacao.marvelchallenge.domain.model.CharacterModel
import javax.inject.Inject

class SharedViewModel @ViewModelInject @Inject constructor(private val repository: CharactersRepository) :
    ViewModel() {

    private val mutableTransitionName = MutableLiveData<String>()
    val transitionName: LiveData<String>
        get() = mutableTransitionName

    private val mutableSelectedCharacter = MutableLiveData<CharacterModel>()
    val selectedCharacter: LiveData<CharacterModel>
        get() = mutableSelectedCharacter

    fun selectCharacter(characterModel: CharacterModel, transitionName: String) {
        mutableTransitionName.value = transitionName
        mutableSelectedCharacter.value = characterModel
    }

}