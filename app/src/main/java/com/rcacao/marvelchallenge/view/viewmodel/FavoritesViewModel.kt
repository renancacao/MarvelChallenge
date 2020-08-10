package com.rcacao.marvelchallenge.view.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rcacao.marvelchallenge.domain.model.DataResult
import com.rcacao.marvelchallenge.domain.model.character.CharacterModel
import com.rcacao.marvelchallenge.domain.usecases.UseCase
import com.rcacao.marvelchallenge.utils.EmptyListException
import com.rcacao.marvelchallenge.view.model.details.favorites.FavoritesStateUi
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavoritesViewModel @ViewModelInject @Inject constructor(
    private val getFavoritesUseCase: UseCase<String, DataResult<List<CharacterModel>>>,
    private val textErrorHelper: TextErrorHelper
) :
    ViewModel() {

    var currentQuery: String = ""

    private val mutableFavoritesStateUi = MutableLiveData<FavoritesStateUi>()
    val favoritesStateUi: LiveData<FavoritesStateUi>
        get() = mutableFavoritesStateUi

    private val mutableFavoritesList = MutableLiveData<ArrayList<CharacterModel>>()
    val favoritesList: LiveData<ArrayList<CharacterModel>>
        get() = mutableFavoritesList

    fun searchCharacter(queryString: String) {
        mutableFavoritesStateUi.value = FavoritesStateUi.Loading
        currentQuery = queryString
        viewModelScope.launch {
            when (val result: DataResult<List<CharacterModel>> =
                getFavoritesUseCase(queryString)) {
                is DataResult.Success -> {
                    if (result.data.isNotEmpty()) {
                        handleFavoritesListData(result.data as ArrayList<CharacterModel>)
                    } else {
                        handleFavoritesListError(EmptyListException().message)
                    }
                }
                is DataResult.Error -> handleFavoritesListError(result.exception.message)
            }
        }

    }

    private fun handleFavoritesListError(error: String?) {
        mutableFavoritesList.value = ArrayList()
        mutableFavoritesStateUi.value = FavoritesStateUi.Error(textErrorHelper(error))
    }

    private fun handleFavoritesListData(data: ArrayList<CharacterModel>) {
        mutableFavoritesStateUi.value = FavoritesStateUi.Loaded
        mutableFavoritesList.value = data
    }

}
