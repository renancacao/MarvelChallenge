package com.rcacao.marvelchallenge.domain.model.character

data class CharacterModel(
    val id: String,
    val name: String,
    val listImageUrl: String,
    val detailImageUrl: String,
    val description: String
)