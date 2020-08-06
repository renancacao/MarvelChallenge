package com.rcacao.marvelchallenge.data.repository.characters

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.rcacao.marvelchallenge.data.api.MarvelWebService
import com.rcacao.marvelchallenge.data.database.AppDatabase
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
    private val characterMapper:
    Merger<Flow<PagingData<CharacterResponse>>, String, Flow<PagingData<CharacterModel>>>
) {

    private var ids: List<String> = getLocalIds()

    fun getCharacters(query: String): Flow<PagingData<CharacterModel>> {
        return characterMapper.mapAndMerge(getRemoteCharacters(query), ids)
    }

    private fun getRemoteCharacters(query: String): Flow<PagingData<CharacterResponse>> {
        return Pager(pagingConfig,
            pagingSourceFactory = {
                CharacterPagingSource(
                    query,
                    webService,
                    apiHelper
                )
            }
        ).flow
    }

    private fun getLocalIds(): List<String> = database.characterDao().getIds()


}
