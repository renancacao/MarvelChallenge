package com.rcacao.marvelchallenge.data.mapper

import com.rcacao.marvelchallenge.data.database.Character
import com.rcacao.marvelchallenge.domain.model.character.CharacterModel
import javax.inject.Inject

class CharacterMapper @Inject constructor() :
    Mapper<CharacterModel, Character> {
    override fun map(input: CharacterModel): Character = Character(
        input.id,
        input.name,
        input.imageListUrl,
        input.imageDetailsUrl,
        input.description
    )


}
