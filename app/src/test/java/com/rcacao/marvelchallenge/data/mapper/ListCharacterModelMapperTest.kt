package com.rcacao.marvelchallenge.data.mapper

import com.rcacao.marvelchallenge.MockUtils
import com.rcacao.marvelchallenge.data.database.Character
import com.rcacao.marvelchallenge.domain.model.character.CharacterModel
import org.junit.Assert
import org.junit.Test

class ListCharacterModelMapperTest {

    @Test
    fun testMapWithData() {

        val mapper = ListCharacterModelMapper()
        val listCharacter: List<Character> = MockUtils.getCharacterList()
        val listCharacterModel: List<CharacterModel> = mapper.map(listCharacter)

        Assert.assertEquals(listCharacter.size, listCharacterModel.size)
        for (id in listCharacter.indices) {
            Assert.assertEquals(listCharacter[id].name, listCharacterModel[id].name)
            Assert.assertEquals(listCharacter[id].description, listCharacterModel[id].description)
            Assert.assertEquals(
                listCharacter[id].imageDetailsUrl,
                listCharacterModel[id].imageDetailsUrl
            )
            Assert.assertEquals(listCharacter[id].imageListUrl, listCharacterModel[id].imageListUrl)
            Assert.assertEquals(true, listCharacterModel[id].isFavorite)
        }

    }

}