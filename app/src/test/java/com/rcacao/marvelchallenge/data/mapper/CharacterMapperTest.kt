package com.rcacao.marvelchallenge.data.mapper

import com.rcacao.marvelchallenge.MockUtils
import com.rcacao.marvelchallenge.data.database.Character
import com.rcacao.marvelchallenge.domain.model.character.CharacterModel
import org.junit.Assert
import org.junit.Test

class CharacterMapperTest {

    @Test
    fun testMapWithData() {

        val mapper = CharacterMapper()
        val characterModel: CharacterModel = MockUtils.getCharacterModel(1)
        val character: Character = mapper.map(characterModel)

        Assert.assertEquals(characterModel.id, character.id)
        Assert.assertEquals(characterModel.name, character.name)
        Assert.assertEquals(characterModel.imageListUrl, character.imageListUrl)
        Assert.assertEquals(characterModel.imageDetailsUrl, character.imageDetailsUrl)
        Assert.assertEquals(characterModel.description, character.description)

    }

}