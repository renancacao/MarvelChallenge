package com.rcacao.marvelchallenge.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.rcacao.marvelchallenge.data.CharacterResponse
import com.rcacao.marvelchallenge.data.paging.MarvelPagingSource
import com.rcacao.marvelchallenge.data.api.MarvelWebService
import com.rcacao.marvelchallenge.utils.ApiHelper
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CharactersRepository @Inject constructor(
    private val webService: MarvelWebService,
    private val apiHelper: ApiHelper
) {

    fun getCharacters(query: String): Flow<PagingData<CharacterResponse>> {
        return Pager(
            PagingConfig(
                pageSize = 20,
                enablePlaceholders = false,
                initialLoadSize = 20
            ),
            pagingSourceFactory = {
                MarvelPagingSource(
                    query,
                    webService,
                    apiHelper
                )
            }
        ).flow
    }

}
