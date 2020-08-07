package com.rcacao.marvelchallenge.data.repository.characters

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.rcacao.marvelchallenge.data.api.MarvelWebService
import com.rcacao.marvelchallenge.data.database.AppDatabase
import com.rcacao.marvelchallenge.data.database.Character
import com.rcacao.marvelchallenge.data.mapper.Mapper
import com.rcacao.marvelchallenge.data.mapper.Merger
import com.rcacao.marvelchallenge.data.model.character.CharacterResponse
import com.rcacao.marvelchallenge.domain.model.DataResult
import com.rcacao.marvelchallenge.domain.model.character.CharacterModel
import com.rcacao.marvelchallenge.utils.ApiHelper
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@Singleton
class CharactersRepository @Inject constructor(
    private val webService: MarvelWebService,
    private val apiHelper: ApiHelper,
    private val pagingConfig: PagingConfig,
    private val database: AppDatabase,
    private val characterModelMapper: Mapper<Character, CharacterModel>,
    private val characterMapper: Mapper<CharacterModel, Character>,
    private val pagingCharacterModelMapper:
    Merger<Flow<PagingData<CharacterResponse>>, String, Flow<PagingData<CharacterModel>>>
) {

    private var ids: List<String>? = null
    private var currentResult: Flow<PagingData<CharacterResponse>>? = null

    suspend fun getCharacters(query: String, forceLast: Boolean): Flow<PagingData<CharacterModel>> {
        val lastResult: Flow<PagingData<CharacterResponse>>? = currentResult
        return if (forceLast && lastResult != null) {
            mergeWithFavorites(lastResult, reloadIds = false)
        } else {
            val newResult: Flow<PagingData<CharacterResponse>> = getRemoteCharacters(query)
            currentResult = newResult
            mergeWithFavorites(newResult, reloadIds = ids == null)
        }
    }

    private suspend fun mergeWithFavorites(
        remoteResult: Flow<PagingData<CharacterResponse>>,
        reloadIds: Boolean
    ): Flow<PagingData<CharacterModel>> {

        if (reloadIds) {
            ids = getLocalIds()
        }

        return if (ids.isNullOrEmpty()) {
            pagingCharacterModelMapper.map(remoteResult)
        } else {
            pagingCharacterModelMapper.mapAndMerge(remoteResult, ids!!)
        }
    }

    private fun getRemoteCharacters(query: String): Flow<PagingData<CharacterResponse>> {
        return Pager(
            pagingConfig,
            pagingSourceFactory = {
                CharacterPagingSource(
                    query,
                    webService,
                    apiHelper
                )
            }
        ).flow
    }

    suspend fun saveFavorite(characterModel: CharacterModel): DataResult<Unit> {
        return try {
            val uid: Unit = database.characterDao().insert(characterMapper.map(characterModel))
            ids = getLocalIds()
            DataResult.Success(uid)
        } catch (ex: Exception) {
            DataResult.Error(ex)
        }
    }

    suspend fun deleteFavorite(id: String): DataResult<Unit> {
        return try {
            val uid: Unit = database.characterDao().deleteById(id)
            ids = getLocalIds()
            DataResult.Success(uid)
        } catch (ex: Exception) {
            DataResult.Error(ex)
        }
    }

    private suspend fun getLocalIds(): List<String> = database.characterDao().getIds()

}
