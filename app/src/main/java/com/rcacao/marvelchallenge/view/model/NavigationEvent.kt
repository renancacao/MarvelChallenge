package com.rcacao.marvelchallenge.view.model

sealed class NavigationEvent {
    object NavigateToDetails : NavigationEvent()
}
