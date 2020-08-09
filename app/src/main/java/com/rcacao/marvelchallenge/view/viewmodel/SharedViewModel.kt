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
import com.rcacao.marvelchallenge.view.model.UpdateCharactersEvent
import com.rcacao.marvelchallenge.view.model.UpdateFavoriteEvent
import kotlinx.coroutines.launch
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

    private val mutableFavoriteEvent = MutableLiveData<Event<UpdateFavoriteEvent>>()
    val favoriteEvent: LiveData<Event<UpdateFavoriteEvent>>
        get() = mutableFavoriteEvent

    private val mutableCharactersEvent = MutableLiveData<Event<UpdateCharactersEvent>>()
    val updateItem: LiveData<Event<UpdateCharactersEvent>>
        get() = mutableCharactersEvent

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

    fun selectCharacter(
        fromFavorites: Boolean,
        itemPosition: Int,
        characterModel: CharacterModel,
        transitionName: String
    ) {
        currentPosition = itemPosition
        mutableTransitionName.value = transitionName
        mutableSelectedCharacter.value = characterModel
        mutableNavigationEvent.value = Event(NavigationEvent.NavigateToDetails(fromFavorites))
    }

    fun configureDetailsToolbar() {
        mutableSelectedCharacter.value?.let {
            mutableToolbarState.value =
                ToolbarState.DetailsToolbar(it.name, it.isFavorite)
        }
    }

    fun starFromFavorite(
        characterModel: CharacterModel,
        pos: Int
    ) {
        viewModelScope.launch {
            when (deleteFavoriteUseCase(characterModel)) {
                is DataResult.Success -> {
                    updateFavoritesUi(pos)
                }
            }
        }
    }

    fun starFromCharacters(characterModel: CharacterModel, pos: Int) {
        val save: Boolean = !characterModel.isFavorite
        val useCase: UseCase<CharacterModel, DataResult<Unit>> = getLocalAction(save)
        viewModelScope.launch {
            when (useCase(characterModel)) {
                is DataResult.Success -> {
                    updateCharactersUi(pos, save)
                }
            }
        }
    }

    fun starFromToolbar(characterModel: CharacterModel, pos: Int, fromFavorites: Boolean) {
        val save: Boolean = !characterModel.isFavorite
        val useCase: UseCase<CharacterModel, DataResult<Unit>> = getLocalAction(save)
        viewModelScope.launch {
            when (useCase(characterModel)) {
                is DataResult.Success -> {
                    updateUi(save, fromFavorites, pos)
                }
            }
        }
    }

    private fun getLocalAction(save: Boolean): UseCase<CharacterModel, DataResult<Unit>> {
        return if (save) saveFavoriteUseCase else deleteFavoriteUseCase
    }


    private fun updateUi(save: Boolean, fromFavorites: Boolean, pos: Int) {
        mutableUpdateActionFav.value = Event(save)
        if (!fromFavorites) {
            updateCharactersUi(pos, save)
        } else {
            updateFavoritesUi(pos)
        }
    }

    private fun updateCharactersUi(pos: Int, save: Boolean) {
        mutableCharactersEvent.value = Event(UpdateCharactersEvent.UpdateItem(pos, save))
        mutableFavoriteEvent.value = Event(UpdateFavoriteEvent.UpdateList)
    }

    private fun updateFavoritesUi(pos: Int) {
        mutableFavoriteEvent.value = Event(UpdateFavoriteEvent.FavoriteRemove(pos))
        mutableCharactersEvent.value = Event(UpdateCharactersEvent.UpdateList)
    }

}