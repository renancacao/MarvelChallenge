package com.rcacao.marvelchallenge.data.mapper

import com.rcacao.marvelchallenge.data.MockUtils
import com.rcacao.marvelchallenge.data.model.series.SeriesDataResponse
import com.rcacao.marvelchallenge.data.model.series.SeriesListResponse
import com.rcacao.marvelchallenge.domain.model.series.SeriesModel
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class SeriesMapperTest {

    private lateinit var seriesDataResponse: SeriesDataResponse
    private lateinit var mapper: SeriesMapper

    @Before
    fun setup() {
        mapper = SeriesMapper()
        seriesDataResponse = SeriesDataResponse()
    }

    @Test
    fun testMapWithData() {

        val seriesListResponse = SeriesListResponse()
        seriesListResponse.series = MockUtils.getSeriesResponseList()
        seriesDataResponse.data = seriesListResponse
        val seriesList: List<SeriesModel> = mapper.map(seriesDataResponse)

        Assert.assertEquals(seriesListResponse.series.size, seriesList.size)

        for (id in seriesList.indices) {
            Assert.assertEquals(seriesListResponse.series[id].title, seriesList[id].title)
            Assert.assertEquals(
                MockUtils.getImageUrl(seriesListResponse.series[id].thumbnail, "portrait_xlarge"),
                seriesList[id].imageUrl
            )
        }

    }


    @Test
    fun testMapWithoutData() {
        seriesDataResponse.data = null

        val seriesList: List<SeriesModel> = mapper.map(seriesDataResponse)

        Assert.assertEquals(0, seriesList.size)
    }
}