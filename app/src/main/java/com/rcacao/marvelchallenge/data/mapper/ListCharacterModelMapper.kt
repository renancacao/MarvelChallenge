package com.rcacao.marvelchallenge.data.mapper

import com.rcacao.marvelchallenge.data.database.Character
import com.rcacao.marvelchallenge.domain.model.character.CharacterModel
import javax.inject.Inject

class ListCharacterModelMapper @Inject constructor() :
    Mapper<List<Character>, ArrayList<CharacterModel>> {

    override fun map(input: List<Character>): ArrayList<CharacterModel> =
        input.map {
            CharacterModel(
                it.id,
                it.name,
                it.imageListUrl,
                it.imageDetailsUrl,
                it.description,
                isFavorite = true
            )
        } as ArrayList<CharacterModel>
}

