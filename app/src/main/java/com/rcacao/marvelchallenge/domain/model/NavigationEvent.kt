package com.rcacao.marvelchallenge.domain.model

sealed class NavigationEvent {
    object NavigateToDetails : NavigationEvent()
}
