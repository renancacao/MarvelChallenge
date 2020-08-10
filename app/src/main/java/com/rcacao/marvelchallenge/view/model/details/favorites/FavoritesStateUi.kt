package com.rcacao.marvelchallenge.view.model.details.favorites

sealed class FavoritesStateUi {
    object Loading : FavoritesStateUi()
    object Loaded : FavoritesStateUi()
    data class Error(val errorMsg: String) : FavoritesStateUi()
}
