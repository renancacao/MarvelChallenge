package com.rcacao.marvelchallenge.view.model.details.favorites

sealed class FavoritesStateUi {
    object Loading : FavoritesStateUi()
    object Loaded : FavoritesStateUi()
    object Empty : FavoritesStateUi()
    data class Error(val errorMsg: String) : FavoritesStateUi()
}
