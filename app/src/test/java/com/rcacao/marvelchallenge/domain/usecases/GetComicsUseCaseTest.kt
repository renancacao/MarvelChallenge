package com.rcacao.marvelchallenge.domain.usecases

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.rcacao.marvelchallenge.MockUtils
import com.rcacao.marvelchallenge.data.repository.comics.ComicsRepository
import com.rcacao.marvelchallenge.domain.model.DataResult
import com.rcacao.marvelchallenge.domain.model.comics.ComicsModel
import com.rcacao.marvelchallenge.utils.ConnectionHelper
import com.rcacao.marvelchallenge.utils.NoNetworkingException
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class GetComicsUseCaseTest {

    private val connectionHelper: ConnectionHelper = mock()
    private var repository: ComicsRepository = mock()
    private lateinit var useCase: GetComicsUseCase

    @Before
    fun setup() {
        useCase = GetComicsUseCase(repository, connectionHelper)
    }

    @Test
    fun testSuccess() {
        runBlockingTest {

            whenever(connectionHelper.isConnected()).thenReturn(true)

            val list = MockUtils.getComicsModelList()
            whenever(repository.getComics(any())).thenReturn(DataResult.Success(list))

            val result: DataResult<List<ComicsModel>> = useCase.invoke("id")

            assertTrue(result is DataResult.Success)
            assertEquals(list, (result as DataResult.Success).data)
        }
    }

    @Test
    fun testError() {
        runBlockingTest {

            whenever(connectionHelper.isConnected()).thenReturn(true)

            whenever(repository.getComics(any())).thenReturn(DataResult.Error(Exception()))

            val result: DataResult<List<ComicsModel>> = useCase.invoke("id")

            assertTrue(result is DataResult.Error)
        }
    }

    @Test
    fun testWithoutNetwork() {
        runBlockingTest {

            whenever(connectionHelper.isConnected()).thenReturn(false)

            val list = MockUtils.getComicsModelList()
            whenever(repository.getComics(any())).thenReturn(DataResult.Error(NoNetworkingException()))

            val result: DataResult<List<ComicsModel>> = useCase.invoke("id")

            assertTrue(result is DataResult.Error)
        }
    }


}