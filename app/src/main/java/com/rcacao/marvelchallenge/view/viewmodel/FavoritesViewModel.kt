package com.rcacao.marvelchallenge.view.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rcacao.marvelchallenge.domain.model.DataResult
import com.rcacao.marvelchallenge.domain.model.character.CharacterModel
import com.rcacao.marvelchallenge.domain.usecases.UseCase
import com.rcacao.marvelchallenge.view.model.details.favorites.FavoritesStateUi
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavoritesViewModel @ViewModelInject @Inject constructor(
    private val getFavoritesUseCase: UseCase<String, DataResult<List<CharacterModel>>>
) :
    ViewModel() {

    var currentQuery: String = ""

    private val mutableFavoritesStateUi = MutableLiveData<FavoritesStateUi>()
    val favoritesStateUi: LiveData<FavoritesStateUi>
        get() = mutableFavoritesStateUi

    private val mutableFavoritesList = MutableLiveData<List<CharacterModel>>()
    val favoritesList: LiveData<List<CharacterModel>>
        get() = mutableFavoritesList

    fun searchCharacter(queryString: String) {
        mutableFavoritesStateUi.value = FavoritesStateUi.Loading
        currentQuery = queryString
        viewModelScope.launch {
            when (val result: DataResult<List<CharacterModel>> =
                getFavoritesUseCase(queryString)) {
                is DataResult.Success -> handleFavoritesListData(result.data)
                is DataResult.Error -> handleFavoritesListError(result.exception.localizedMessage)
            }
        }

    }

    private fun handleFavoritesListError(error: String?) {
        mutableFavoritesList.value = emptyList()
        mutableFavoritesStateUi.value = FavoritesStateUi.Error(error ?: "Error loading list")
    }

    private fun handleFavoritesListData(data: List<CharacterModel>) {
        mutableFavoritesStateUi.value = FavoritesStateUi.Loaded
        mutableFavoritesList.value = data
        if (data.isEmpty()) {
            mutableFavoritesStateUi.value = FavoritesStateUi.Empty
        }
    }

}