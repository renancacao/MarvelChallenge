package com.rcacao.marvelchallenge.view.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rcacao.marvelchallenge.di.UseCasesModule
import com.rcacao.marvelchallenge.domain.model.DataResult
import com.rcacao.marvelchallenge.domain.model.Event
import com.rcacao.marvelchallenge.domain.model.character.CharacterModel
import com.rcacao.marvelchallenge.domain.usecases.UseCase
import com.rcacao.marvelchallenge.view.model.NavigationEvent
import com.rcacao.marvelchallenge.view.model.ToolbarState
import com.rcacao.marvelchallenge.view.model.UpdateItemEvent
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class SharedViewModel @ViewModelInject @Inject constructor(
    @UseCasesModule.SaveUseCase private val saveFavoriteUseCase: UseCase<CharacterModel, DataResult<Unit>>,
    @UseCasesModule.DeleteUseCase private val deleteFavoriteUseCase: UseCase<CharacterModel, DataResult<Unit>>
) :
    ViewModel() {

    var currentPosition: Int = 0

    private val mutableNavigationEvent = MutableLiveData<Event<NavigationEvent>>()
    val navigationEvent: LiveData<Event<NavigationEvent>>
        get() = mutableNavigationEvent

    private val mutableUpdateItem = MutableLiveData<Event<UpdateItemEvent>>()
    val updateItem: LiveData<Event<UpdateItemEvent>>
        get() = mutableUpdateItem

    private val mutableUpdateActionFav = MutableLiveData<Event<Boolean>>()
    val updateActionFav: LiveData<Event<Boolean>>
        get() = mutableUpdateActionFav

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
        mutableNavigationEvent.value = Event(NavigationEvent.NavigateToDetails)
    }

    fun configureDefaultToolbar() {
        mutableToolbarState.value = ToolbarState.DefaultToolbar
    }

    fun configureDetailsToolbar() {
        mutableSelectedCharacter.value?.let {
            mutableToolbarState.value =
                ToolbarState.DetailsToolbar(it.name, it.isFavorite)
        }
    }

    fun starCharacter(characterModel: CharacterModel, position: Int) {
        val newValue: Boolean = !characterModel.isFavorite
        val useCase: UseCase<CharacterModel, DataResult<Unit>> =
            if (newValue) saveFavoriteUseCase else deleteFavoriteUseCase
        viewModelScope.launch {
            when (useCase(characterModel)) {
                is DataResult.Success -> {
                    updateFavUis(position, newValue)
                }
            }

        }
    }

    private fun updateFavUis(
        position: Int,
        isFav: Boolean
    ) {
        val updateData = UpdateItemEvent(position, isFav)
        mutableUpdateItem.value = Event(updateData)
        Timber.d("FAV : $updateData")
        mutableUpdateActionFav.value = Event(isFav)
    }

}