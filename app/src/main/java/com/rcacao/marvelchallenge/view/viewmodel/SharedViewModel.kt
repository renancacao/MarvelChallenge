package com.rcacao.marvelchallenge.view.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rcacao.marvelchallenge.domain.model.character.CharacterModel
import com.rcacao.marvelchallenge.view.model.ToolbarState
import javax.inject.Inject

class SharedViewModel @ViewModelInject @Inject constructor() :
    ViewModel() {

    private val mutableToolbarState = MutableLiveData<ToolbarState>()
    val toolbarState: LiveData<ToolbarState>
        get() = mutableToolbarState

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

    fun configureDefaultToolbar() {
        mutableToolbarState.value = ToolbarState.DefaultToolbar
    }

    fun configureDetailsToolbar() {
        mutableToolbarState.value =
            ToolbarState.DetailsToolbar(mutableSelectedCharacter.value?.name ?: "")
    }


}