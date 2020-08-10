package com.rcacao.marvelchallenge.data.repository.comics

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.rcacao.marvelchallenge.MockUtils
import com.rcacao.marvelchallenge.data.ApiHelper
import com.rcacao.marvelchallenge.data.api.MarvelWebService
import com.rcacao.marvelchallenge.data.mapper.ComicsMapper
import com.rcacao.marvelchallenge.data.mapper.Mapper
import com.rcacao.marvelchallenge.data.model.comics.ComicsDataResponse
import com.rcacao.marvelchallenge.domain.model.DataResult
import com.rcacao.marvelchallenge.domain.model.comics.ComicsModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.exceptions.base.MockitoException

@ExperimentalCoroutinesApi
class ComicsRepositoryTest {

    private var mapper: Mapper<ComicsDataResponse, List<ComicsModel>> = ComicsMapper()
    private var apiHelper: ApiHelper = mock()
    private var api: MarvelWebService = mock()

    private lateinit var repository: ComicsRepositoryImpl


    @Before
    fun setup() {
        repository = ComicsRepositoryImpl(
            api,
            apiHelper,
            mapper
        )
        whenever(apiHelper.getHash(any())).thenReturn("hash")
        whenever(apiHelper.getTimeStamp()).thenReturn("timeStamp")
        whenever(apiHelper.getPublicKey()).thenReturn("key")

    }

    @Test
    fun testGetComicsSuccess() {
        runBlockingTest {
            whenever(
                api.loadComics(
                    any(),
                    any(),
                    any(),
                    any(),
                    any()
                )
            ).thenReturn(MockUtils.getComicsDataResponse())
            val result: DataResult<List<ComicsModel>> = repository.getComics("1")
            assertTrue(result is DataResult.Success)
            Assert.assertEquals("title1", (result as DataResult.Success).data[0].title)
        }
    }

    @Test
    fun testGetComicsError() {
        runBlockingTest {
            whenever(
                api.loadComics(
                    any(),
                    any(),
                    any(),
                    any(),
                    any()
                )
            ).thenThrow(MockitoException(""))
            val result: DataResult<List<ComicsModel>> = repository.getComics("1")
            assertTrue(result is DataResult.Error)
        }
    }


}