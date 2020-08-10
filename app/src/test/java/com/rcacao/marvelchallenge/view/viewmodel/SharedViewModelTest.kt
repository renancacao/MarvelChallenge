package com.rcacao.marvelchallenge.view.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.rcacao.marvelchallenge.MockUtils
import com.rcacao.marvelchallenge.domain.model.DataResult
import com.rcacao.marvelchallenge.domain.model.Event
import com.rcacao.marvelchallenge.domain.model.character.CharacterModel
import com.rcacao.marvelchallenge.domain.usecases.UseCase
import com.rcacao.marvelchallenge.view.model.NavigationEvent
import com.rcacao.marvelchallenge.view.model.ToolbarState
import com.rcacao.marvelchallenge.view.model.UpdateCharactersEvent
import com.rcacao.marvelchallenge.view.model.UpdateFavoriteEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@ExperimentalCoroutinesApi
class SharedViewModelTest {


    @Rule
    @JvmField
    var instantExecutorRule = InstantTaskExecutorRule()

    private val deleteUseCase: UseCase<CharacterModel, DataResult<Unit>> = mock()
    private val saveUseCase: UseCase<CharacterModel, DataResult<Unit>> = mock()
    private val observerNavigationEvent: Observer<Event<NavigationEvent>> = mock()
    private val observerCharacter: Observer<CharacterModel> = mock()
    private val observerToolbar: Observer<ToolbarState> = mock()
    private val favoritesObserver: Observer<Event<UpdateFavoriteEvent>> = mock()
    private val characterObserve: Observer<Event<UpdateCharactersEvent>> = mock()
    private lateinit var viewModel: SharedViewModel

    @Before
    fun setup() {
        runBlockingTest {
            Dispatchers.setMain(Dispatchers.Unconfined)
            viewModel = SharedViewModel(saveUseCase, deleteUseCase)
            whenever(deleteUseCase.invoke(any())).thenReturn(DataResult.Success(Unit))
            whenever(saveUseCase.invoke(any())).thenReturn(DataResult.Success(Unit))
        }
    }

    @Test
    fun getCurrentPosition() {
        viewModel.currentPosition = 10
        assertEquals(10, viewModel.currentPosition)
    }

    @Test
    fun testSelect() {
        runBlockingTest {
            viewModel.selectedCharacter.observeForever(observerCharacter)
            viewModel.navigationEvent.observeForever(observerNavigationEvent)

            val characterModel: CharacterModel = MockUtils.getCharacterModel(0)

            viewModel.selectCharacter(false, 1, characterModel)

            assertEquals(1, viewModel.currentPosition)
            verify(observerCharacter).onChanged(characterModel)
            verify(observerNavigationEvent).onChanged(any())
        }
    }

    @Test
    fun testConfigureToolbar() {
        runBlockingTest {
            viewModel.toolbarState.observeForever(observerToolbar)
            viewModel.navigationEvent.observeForever(observerNavigationEvent)

            val characterModel: CharacterModel = MockUtils.getCharacterModel(0)
            viewModel.selectCharacter(false, 1, characterModel)
            viewModel.configureDetailsToolbar()

            verify(observerToolbar).onChanged(
                ToolbarState.DetailsToolbar(
                    characterModel.name,
                    characterModel.isFavorite
                )
            )
            verify(observerNavigationEvent).onChanged(any())
        }
    }

    @Test
    fun testStarFromFavorites() {
        runBlockingTest {
            viewModel.characterEvent.observeForever(characterObserve)
            viewModel.favoriteEvent.observeForever(favoritesObserver)

            val characterModel: CharacterModel = MockUtils.getCharacterModel(0)
            viewModel.starFromFavorite(characterModel, 1)

            verify(characterObserve).onChanged(any())
            verify(favoritesObserver).onChanged(any())
        }
    }

    @Test
    fun testStarFromCharacter() {
        runBlockingTest {
            viewModel.characterEvent.observeForever(characterObserve)
            viewModel.favoriteEvent.observeForever(favoritesObserver)

            val characterModel: CharacterModel = MockUtils.getCharacterModel(0)
            viewModel.starFromCharacters(characterModel, 1)

            verify(characterObserve).onChanged(any())
            verify(favoritesObserver).onChanged(any())
        }
    }

    @Test
    fun testStarFromToolbarFalse() {
        runBlockingTest {
            viewModel.characterEvent.observeForever(characterObserve)
            viewModel.favoriteEvent.observeForever(favoritesObserver)

            val characterModel: CharacterModel = MockUtils.getCharacterModel(0)
            viewModel.starFromToolbar(characterModel, 1, false)

            verify(characterObserve).onChanged(any())
            verify(favoritesObserver).onChanged(any())
        }
    }

    @Test
    fun testStarFromToolbarTrue() {
        runBlockingTest {
            viewModel.characterEvent.observeForever(characterObserve)
            viewModel.favoriteEvent.observeForever(favoritesObserver)

            val characterModel: CharacterModel = MockUtils.getCharacterModel(0)
            viewModel.starFromToolbar(characterModel, 1, true)

            verify(characterObserve).onChanged(any())
            verify(favoritesObserver).onChanged(any())
        }
    }


}