package com.rcacao.marvelchallenge.domain.model.character

data class CharacterModel(
    val id: String,
    val name: String,
    val imageListUrl: String,
    val imageDetailsUrl: String,
    val description: String,
    var isFavorite: Boolean = false
)
