package com.rcacao.marvelchallenge.data.repository.characters

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.rcacao.marvelchallenge.data.api.MarvelWebService
import com.rcacao.marvelchallenge.data.mapper.Mapper
import com.rcacao.marvelchallenge.data.model.character.CharacterResponse
import com.rcacao.marvelchallenge.domain.model.character.CharacterModel
import com.rcacao.marvelchallenge.utils.ApiHelper
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CharactersRepository @Inject constructor(
    private val webService: MarvelWebService,
    private val apiHelper: ApiHelper,
    private val pagingConfig: PagingConfig,
    private val characterMapper:
    Mapper<Flow<PagingData<CharacterResponse>>, Flow<PagingData<CharacterModel>>>
) {

    fun getCharacters(query: String): Flow<PagingData<CharacterModel>> {
        return characterMapper.map(Pager(pagingConfig,
            pagingSourceFactory = {
                CharacterPagingSource(
                    query,
                    webService,
                    apiHelper
                )
            }
        ).flow)
    }

}
