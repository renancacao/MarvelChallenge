package com.rcacao.marvelchallenge.view.model

sealed class UpdateCharactersEvent {
    data class UpdateItem(val pos: Int, val value: Boolean) : UpdateCharactersEvent()
    object UpdateList : UpdateCharactersEvent()
}
