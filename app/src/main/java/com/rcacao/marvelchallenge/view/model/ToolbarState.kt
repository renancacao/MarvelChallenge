package com.rcacao.marvelchallenge.view.model

sealed class ToolbarState {
    object DefaultToolbar: ToolbarState()
    data class DetailsToolbar(val name: String): ToolbarState()
}
