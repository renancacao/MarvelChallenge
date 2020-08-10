package com.rcacao.marvelchallenge.domain.usecases

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.rcacao.marvelchallenge.MockUtils
import com.rcacao.marvelchallenge.data.repository.characters.CharactersRepository
import com.rcacao.marvelchallenge.domain.model.DataResult
import com.rcacao.marvelchallenge.domain.model.character.CharacterModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class SaveFavoriteUseCaseTest {

    private var repository: CharactersRepository = mock()
    private lateinit var useCase: SaveFavoriteUseCase

    @Before
    fun setup() {
        useCase = SaveFavoriteUseCase(repository)
    }

    @Test
    fun testSuccess() {
        runBlockingTest {
            whenever(repository.saveFavorite(any())).thenReturn(DataResult.Success(Unit))

            val characterModel: CharacterModel = MockUtils.getCharacterModel(1)
            val result: DataResult<Unit> = useCase.invoke(characterModel)

            assertTrue(result is DataResult.Success)
        }
    }

    @Test
    fun testError() {
        runBlockingTest {
            whenever(repository.saveFavorite(any())).thenReturn(DataResult.Error(Exception()))

            val characterModel: CharacterModel = MockUtils.getCharacterModel(1)
            val result: DataResult<Unit> = useCase.invoke(characterModel)

            assertTrue(result is DataResult.Error)
        }
    }
}