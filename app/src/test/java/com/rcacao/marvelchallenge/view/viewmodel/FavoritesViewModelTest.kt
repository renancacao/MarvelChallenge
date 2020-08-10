package com.rcacao.marvelchallenge.view.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.rcacao.marvelchallenge.MockUtils
import com.rcacao.marvelchallenge.domain.model.DataResult
import com.rcacao.marvelchallenge.domain.model.character.CharacterModel
import com.rcacao.marvelchallenge.domain.usecases.UseCase
import com.rcacao.marvelchallenge.view.model.details.favorites.FavoritesStateUi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@ExperimentalCoroutinesApi
class FavoritesViewModelTest {
    @Rule
    @JvmField
    var instantExecutorRule = InstantTaskExecutorRule()

    private val useCase: UseCase<String, DataResult<List<CharacterModel>>> = mock()
    private val textErrorHelper: TextErrorHelper = mock()
    private val observerStatus: Observer<FavoritesStateUi> = mock()
    private val observerList: Observer<ArrayList<CharacterModel>> = mock()

    private lateinit var viewModel: FavoritesViewModel

    @Before
    fun setup() {
        runBlockingTest {
            Dispatchers.setMain(Dispatchers.Unconfined)
            whenever(textErrorHelper.invoke(any())).thenReturn("error")
            viewModel = FavoritesViewModel(useCase, textErrorHelper)
        }
    }

    @Test
    fun getCurrentQuery() {
        viewModel.currentQuery = "query"
        assertEquals("query", viewModel.currentQuery)
    }

    @Test
    fun testSearchSuccess() {
        runBlockingTest {
            viewModel.favoritesStateUi.observeForever(observerStatus)
            viewModel.favoritesList.observeForever(observerList)

            val list = MockUtils.getCharacterModelList()
            whenever(useCase.invoke(any())).thenReturn(DataResult.Success(list))

            viewModel.searchCharacter("teste")

            assertEquals("teste", viewModel.currentQuery)
            verify(observerStatus).onChanged(FavoritesStateUi.Loading)
            verify(observerStatus).onChanged(FavoritesStateUi.Loaded)
            verify(observerList).onChanged(list)
        }
    }

    @Test
    fun testSearchEmpty() {
        runBlockingTest {
            viewModel.favoritesStateUi.observeForever(observerStatus)
            viewModel.favoritesList.observeForever(observerList)

            val list = ArrayList<CharacterModel>()
            whenever(useCase.invoke(any())).thenReturn(DataResult.Success(list))

            viewModel.searchCharacter("teste")

            assertEquals("teste", viewModel.currentQuery)
            verify(observerStatus).onChanged(FavoritesStateUi.Loading)
            verify(observerStatus).onChanged(FavoritesStateUi.Error("error"))
        }
    }

    @Test
    fun testSearchError() {
        runBlockingTest {
            viewModel.favoritesStateUi.observeForever(observerStatus)
            viewModel.favoritesList.observeForever(observerList)
            whenever(useCase.invoke(any())).thenReturn(DataResult.Error(Exception("error")))

            viewModel.searchCharacter("teste")

            assertEquals("teste", viewModel.currentQuery)
            verify(observerStatus).onChanged(FavoritesStateUi.Loading)
            verify(observerStatus).onChanged(FavoritesStateUi.Error("error"))
        }
    }

}