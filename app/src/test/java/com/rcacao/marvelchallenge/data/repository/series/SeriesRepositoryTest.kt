package com.rcacao.marvelchallenge.data.repository.series

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.rcacao.marvelchallenge.MockUtils
import com.rcacao.marvelchallenge.data.ApiHelper
import com.rcacao.marvelchallenge.data.api.MarvelWebService
import com.rcacao.marvelchallenge.data.mapper.Mapper
import com.rcacao.marvelchallenge.data.mapper.SeriesMapper
import com.rcacao.marvelchallenge.data.model.series.SeriesDataResponse
import com.rcacao.marvelchallenge.domain.model.DataResult
import com.rcacao.marvelchallenge.domain.model.series.SeriesModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.exceptions.base.MockitoException

@ExperimentalCoroutinesApi
class SeriesRepositoryTest {

    private var mapper: Mapper<SeriesDataResponse, List<SeriesModel>> = SeriesMapper()
    private var apiHelper: ApiHelper = mock()
    private var api: MarvelWebService = mock()

    private lateinit var repository: SeriesRepositoryImpl


    @Before
    fun setup() {
        repository =
            SeriesRepositoryImpl(
                api,
                apiHelper,
                mapper
            )
        whenever(apiHelper.getHash(any())).thenReturn("hash")
        whenever(apiHelper.getTimeStamp()).thenReturn("timeStamp")
        whenever(apiHelper.getPublicKey()).thenReturn("key")

    }

    @Test
    fun testGetSeriesSuccess() {
        runBlockingTest {
            whenever(
                api.loadSeries(
                    any(),
                    any(),
                    any(),
                    any(),
                    any()
                )
            ).thenReturn(MockUtils.getSeriesDataResponse())
            val result: DataResult<List<SeriesModel>> = repository.getSeries("1")
            assertTrue(result is DataResult.Success)
            Assert.assertEquals("title1", (result as DataResult.Success).data[0].title)
        }
    }

    @Test
    fun testGetSeriesError() {
        runBlockingTest {
            whenever(
                api.loadSeries(
                    any(),
                    any(),
                    any(),
                    any(),
                    any()
                )
            ).thenThrow(MockitoException(""))
            val result: DataResult<List<SeriesModel>> = repository.getSeries("1")
            assertTrue(result is DataResult.Error)
        }
    }


}