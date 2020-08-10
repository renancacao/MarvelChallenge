package com.rcacao.marvelchallenge.data.mapper

import com.rcacao.marvelchallenge.MockUtils
import com.rcacao.marvelchallenge.data.model.comics.ComicsDataResponse
import com.rcacao.marvelchallenge.data.model.comics.ComicsListResponse
import com.rcacao.marvelchallenge.domain.model.comics.ComicsModel
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class ComicsMapperTest {

    private lateinit var comicsDataResponse: ComicsDataResponse
    private lateinit var mapper: ComicsMapper

    @Before
    fun setup() {
        mapper = ComicsMapper()
        comicsDataResponse = ComicsDataResponse()
    }

    @Test
    fun testMapWithData() {
        val comicsListResponse = ComicsListResponse()
        comicsListResponse.comics = MockUtils.getComicsResponseList()
        comicsDataResponse.data = comicsListResponse

        val comicsList: List<ComicsModel> = mapper.map(comicsDataResponse)

        Assert.assertEquals(comicsListResponse.comics.size, comicsList.size)
        for (id in comicsList.indices) {
            Assert.assertEquals(comicsListResponse.comics[id].title, comicsList[id].title)
            Assert.assertEquals(
                MockUtils.getImageUrl(comicsListResponse.comics[id].thumbnail, "portrait_xlarge"),
                comicsList[id].imageUrl
            )
        }

    }

    @Test
    fun testMapWithoutData() {
        comicsDataResponse.data = null

        val comicsList: List<ComicsModel> = mapper.map(comicsDataResponse)

        Assert.assertEquals(0, comicsList.size)
    }


}