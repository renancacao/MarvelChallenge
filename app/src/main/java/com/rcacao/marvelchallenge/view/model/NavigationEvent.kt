package com.rcacao.marvelchallenge.view.model

sealed class NavigationEvent {
    data class NavigateToDetails(val fromFavorites: Boolean) : NavigationEvent()
}
