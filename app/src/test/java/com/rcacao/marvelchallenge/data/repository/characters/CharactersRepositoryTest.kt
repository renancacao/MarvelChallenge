package com.rcacao.marvelchallenge.data.repository.characters

import androidx.paging.PagingConfig
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.rcacao.marvelchallenge.MockUtils
import com.rcacao.marvelchallenge.data.ApiHelper
import com.rcacao.marvelchallenge.data.api.MarvelWebService
import com.rcacao.marvelchallenge.data.database.AppDatabase
import com.rcacao.marvelchallenge.data.database.Character
import com.rcacao.marvelchallenge.data.database.CharacterDao
import com.rcacao.marvelchallenge.data.mapper.CharacterMapper
import com.rcacao.marvelchallenge.data.mapper.ListCharacterModelMapper
import com.rcacao.marvelchallenge.data.mapper.Mapper
import com.rcacao.marvelchallenge.data.mapper.PagingCharacterModelMerger
import com.rcacao.marvelchallenge.domain.model.DataResult
import com.rcacao.marvelchallenge.domain.model.character.CharacterModel
import com.rcacao.marvelchallenge.utils.ConnectionHelper
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.exceptions.base.MockitoException


@ExperimentalCoroutinesApi
class CharactersRepositoryTest {

    private var connectionHelper: ConnectionHelper = mock()

    private var pagingCharacterModelMapper = PagingCharacterModelMerger()

    private var database: AppDatabase = mock()

    private var dao: CharacterDao = mock()

    private var pagingConfig: PagingConfig = PagingConfig(
        pageSize = 20,
        enablePlaceholders = false,
        initialLoadSize = 20
    )

    private var apiHelper: ApiHelper = mock()

    private var api: MarvelWebService = mock()

    private var characterMapper: Mapper<CharacterModel, Character> = CharacterMapper()
    private var listCharacterModelMapper: Mapper<List<Character>, ArrayList<CharacterModel>> =
        ListCharacterModelMapper()

    private lateinit var repository: CharactersRepositoryImpl


    @Before
    fun setup() {

        whenever(database.characterDao()).thenReturn(dao)

        repository = CharactersRepositoryImpl(
            api,
            apiHelper,
            pagingConfig,
            database,
            listCharacterModelMapper,
            characterMapper,
            pagingCharacterModelMapper,
            connectionHelper
        )
    }

    @Test
    fun testSaveFavoriteSuccess() {
        runBlockingTest {
            whenever(dao.insert(any())).thenReturn(Unit)
            val result: DataResult<Unit> = repository.saveFavorite(MockUtils.getCharacterModel(1))
            Assert.assertTrue(result is DataResult.Success)
        }
    }

    @Test
    fun testSaveFavoriteError() {
        runBlockingTest {
            whenever(dao.insert(any())).thenThrow(MockitoException(""))
            val result: DataResult<Unit> = repository.saveFavorite(MockUtils.getCharacterModel(1))
            Assert.assertTrue(result is DataResult.Error)
        }
    }


    @Test
    fun testDeleteFavoriteSuccess() {
        runBlockingTest {
            whenever(dao.deleteById(any())).thenReturn(Unit)
            val result: DataResult<Unit> = repository.deleteFavorite("id")
            Assert.assertTrue(result is DataResult.Success)
        }
    }

    @Test
    fun testDeleteFavoriteError() {
        runBlockingTest {
            whenever(dao.deleteById(any())).thenThrow(MockitoException(""))
            val result: DataResult<Unit> = repository.deleteFavorite("id")
            Assert.assertTrue(result is DataResult.Error)
        }
    }

    @Test
    fun testGetFavoritesSuccess() {
        runBlockingTest {
            whenever(dao.getCharacters(any())).thenReturn(emptyList())
            val result: DataResult<java.util.ArrayList<CharacterModel>> =
                repository.getFavorites("")
            Assert.assertTrue(result is DataResult.Success)
        }
    }

    @Test
    fun testGetFavoritesError() {
        runBlockingTest {
            whenever(dao.getCharacters(any())).thenThrow(MockitoException(""))
            val result: DataResult<java.util.ArrayList<CharacterModel>> =
                repository.getFavorites("")
            Assert.assertTrue(result is DataResult.Error)
        }
    }


}
