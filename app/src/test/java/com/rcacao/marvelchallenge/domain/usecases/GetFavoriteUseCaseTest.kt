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
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class GetFavoriteUseCaseTest {

    private var repository: CharactersRepository = mock()
    private lateinit var useCase: GetFavoritesUseCase

    @Before
    fun setup() {
        useCase = GetFavoritesUseCase(repository)
    }

    @Test
    fun testSuccess() {
        runBlockingTest {

            val list: ArrayList<CharacterModel> = MockUtils.getCharacterModelList()
            whenever(repository.getFavorites(any())).thenReturn(DataResult.Success(list))

            val result: DataResult<ArrayList<CharacterModel>> = useCase.invoke("")

            assertTrue(result is DataResult.Success)
            assertEquals(list, (result as DataResult.Success).data)
        }
    }

    @Test
    fun testError() {
        runBlockingTest {
            whenever(repository.getFavorites(any())).thenReturn(DataResult.Error(Exception()))

            val result: DataResult<ArrayList<CharacterModel>> = useCase.invoke("")

            assertTrue(result is DataResult.Error)
        }
    }
}