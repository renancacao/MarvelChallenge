package com.rcacao.marvelchallenge.data.mapper

import com.rcacao.marvelchallenge.data.database.Character
import com.rcacao.marvelchallenge.domain.model.character.CharacterModel
import javax.inject.Inject

class CharacterModelMapper @Inject constructor() :
    Mapper<Character, CharacterModel> {
    override fun map(input: Character): CharacterModel = CharacterModel(
        input.id,
        input.name,
        input.imageListUrl,
        input.imageDetailsUrl,
        input.description,
        isFavorite = true
    )


}
