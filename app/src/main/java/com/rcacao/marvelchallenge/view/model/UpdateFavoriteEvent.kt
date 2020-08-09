package com.rcacao.marvelchallenge.view.model

sealed class UpdateFavoriteEvent {
    data class FavoriteRemove(val pos: Int) : UpdateFavoriteEvent()
    object UpdateList : UpdateFavoriteEvent()
}
