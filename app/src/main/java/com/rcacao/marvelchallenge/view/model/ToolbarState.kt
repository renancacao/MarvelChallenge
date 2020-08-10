package com.rcacao.marvelchallenge.view.model

sealed class ToolbarState {
    data class DetailsToolbar(val name: String, val isFavorite: Boolean) : ToolbarState()
}
