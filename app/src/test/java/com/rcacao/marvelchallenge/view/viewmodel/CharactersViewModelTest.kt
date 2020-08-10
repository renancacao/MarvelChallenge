package com.rcacao.marvelchallenge.view.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.rcacao.marvelchallenge.domain.model.character.CharacterModel
import com.rcacao.marvelchallenge.domain.usecases.GetCharactersPagingUseCase
import com.rcacao.marvelchallenge.domain.usecases.UseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@ExperimentalCoroutinesApi
class CharactersViewModelTest {

    private lateinit var combinedLoadState: CombinedLoadStates

    @Rule
    @JvmField
    var instantExecutorRule = InstantTaskExecutorRule()

    private var loadStateStatusHelper: LoadStateStatusHelper = mock()
    private var useCase:
            UseCase<GetCharactersPagingUseCase.CharacterPagingRequest, Flow<PagingData<CharacterModel>>> =
        mock()

    private var observerVisibility: Observer<Boolean> = mock()

    private lateinit var viewModel: CharactersViewModel

    @Before
    fun setup() {
        viewModel = CharactersViewModel(useCase, loadStateStatusHelper)
        val refresh = LoadState.NotLoading(false)
        val prepend = LoadState.Error(Exception("message"))
        val append = LoadState.NotLoading(false)

        val loadStates = LoadStates(refresh, prepend, append)
        combinedLoadState = CombinedLoadStates(loadStates)
    }

    @Test
    fun getCurrentQuery() {
        viewModel.currentQuery = "query"

        assertEquals("query", viewModel.currentQuery)
    }

    @Test
    fun testSearchCharacter() {
        runBlockingTest {
            val flow: Flow<PagingData<CharacterModel>> = flow {}
            whenever(useCase.invoke(any())).thenReturn(flow)

            val result: Flow<PagingData<CharacterModel>> = viewModel.searchCharacter("teste")

            assertNotNull(result)
            assertEquals("teste", viewModel.currentQuery)
        }
    }


    @Test
    fun testStateChangedListVisibilityTrue() {
        runBlockingTest {
            viewModel.listVisibility.observeForever(observerVisibility)
            whenever(loadStateStatusHelper.isSuccess(any())).thenReturn(true)
            whenever(loadStateStatusHelper.isError(any())).thenReturn(false)
            whenever(loadStateStatusHelper.errorText(any())).thenReturn("")

            viewModel.stateChange(combinedLoadState)

            verify(observerVisibility).onChanged(true)
        }
    }

    @Test
    fun testStateChangedListVisibilityFalse() {
        runBlockingTest {
            viewModel.listVisibility.observeForever(observerVisibility)
            whenever(loadStateStatusHelper.isSuccess(any())).thenReturn(false)
            whenever(loadStateStatusHelper.isError(any())).thenReturn(true)
            whenever(loadStateStatusHelper.errorText(any())).thenReturn("")

            viewModel.stateChange(combinedLoadState)

            verify(observerVisibility).onChanged(false)
        }
    }

    @Test
    fun testStateChangedLoadingVisibilityTrue() {
        runBlockingTest {
            viewModel.loadingVisibility.observeForever(observerVisibility)
            whenever(loadStateStatusHelper.isLoading(any())).thenReturn(true)
            whenever(loadStateStatusHelper.errorText(any())).thenReturn("")

            viewModel.stateChange(combinedLoadState)

            verify(observerVisibility).onChanged(true)
        }
    }

    @Test
    fun testStateChangedLoadingVisibilityFalse() {
        runBlockingTest {
            viewModel.loadingVisibility.observeForever(observerVisibility)
            whenever(loadStateStatusHelper.isLoading(any())).thenReturn(false)
            whenever(loadStateStatusHelper.errorText(any())).thenReturn("")

            viewModel.stateChange(combinedLoadState)

            verify(observerVisibility).onChanged(false)
        }
    }

    @Test
    fun testStateChangedRetryingVisibilityTrue() {
        runBlockingTest {
            viewModel.errorVisibility.observeForever(observerVisibility)
            whenever(loadStateStatusHelper.isError(any())).thenReturn(true)
            whenever(loadStateStatusHelper.errorText(any())).thenReturn("message")

            viewModel.stateChange(combinedLoadState)

            verify(observerVisibility).onChanged(true)
        }
    }

    @Test
    fun testStateChangedRetryVisibilityFalse() {
        runBlockingTest {
            viewModel.errorVisibility.observeForever(observerVisibility)
            whenever(loadStateStatusHelper.isLoading(any())).thenReturn(false)
            whenever(loadStateStatusHelper.errorText(any())).thenReturn("message")

            viewModel.stateChange(combinedLoadState)

            verify(observerVisibility).onChanged(false)
        }
    }

}