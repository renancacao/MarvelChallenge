package com.rcacao.marvelchallenge.domain.usecases

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.rcacao.marvelchallenge.MockUtils
import com.rcacao.marvelchallenge.data.repository.series.SeriesRepository
import com.rcacao.marvelchallenge.domain.model.DataResult
import com.rcacao.marvelchallenge.domain.model.series.SeriesModel
import com.rcacao.marvelchallenge.utils.ConnectionHelper
import com.rcacao.marvelchallenge.utils.NoNetworkingException
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class GetSeriesUseCaseTest {

    private val connectionHelper: ConnectionHelper = mock()
    private var repository: SeriesRepository = mock()
    private lateinit var useCase: GetSeriesUseCase

    @Before
    fun setup() {
        useCase = GetSeriesUseCase(repository, connectionHelper)
    }

    @Test
    fun testSuccess() {
        runBlockingTest {

            whenever(connectionHelper.isConnected()).thenReturn(true)

            val list = MockUtils.getSeriesModelList()
            whenever(repository.getSeries(any())).thenReturn(DataResult.Success(list))

            val result: DataResult<List<SeriesModel>> = useCase.invoke("id")

            assertTrue(result is DataResult.Success)
            assertEquals(list, (result as DataResult.Success).data)
        }
    }

    @Test
    fun testError() {
        runBlockingTest {

            whenever(connectionHelper.isConnected()).thenReturn(true)

            whenever(repository.getSeries(any())).thenReturn(DataResult.Error(Exception()))

            val result: DataResult<List<SeriesModel>> = useCase.invoke("id")

            assertTrue(result is DataResult.Error)
        }
    }

    @Test
    fun testWithoutNetwork() {
        runBlockingTest {

            whenever(connectionHelper.isConnected()).thenReturn(false)

            val list = MockUtils.getSeriesModelList()
            whenever(repository.getSeries(any())).thenReturn(DataResult.Error(NoNetworkingException()))

            val result: DataResult<List<SeriesModel>> = useCase.invoke("id")

            assertTrue(result is DataResult.Error)
        }
    }


}