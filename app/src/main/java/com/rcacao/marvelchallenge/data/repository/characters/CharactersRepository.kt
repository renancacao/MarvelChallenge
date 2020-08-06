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
import com.rcacao.marvelchallenge.domain.model.character.CharacterModel
import com.rcacao.marvelchallenge.utils.ApiHelper
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

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

    suspend fun getCharacters(query: String): Flow<PagingData<CharacterModel>> {
        if (ids == null) {
            ids = getLocalIds()
        }
        return pagingCharacterModelMapper.mapAndMerge(getRemoteCharacters(query), ids)
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

    suspend fun saveFavorite(characterModel: CharacterModel) {
        database.characterDao().insert(characterMapper.map(characterModel))
        ids = getLocalIds()
    }

    private suspend fun getLocalIds(): List<String> = database.characterDao().getIds()
}
