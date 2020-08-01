package com.rcacao.marvelchallenge.data.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.rcacao.marvelchallenge.data.CharacterResponse
import javax.inject.Inject

class CharactersDataSourceFactory @Inject constructor() :
    DataSource.Factory<Int, CharacterResponse>() {

    private val sourceLiveData = MutableLiveData<CharactersDataSource>()

    @Inject
    lateinit var latestSource: CharactersDataSource

    override fun create(): DataSource<Int, CharacterResponse> {
        sourceLiveData.postValue(latestSource)
        return latestSource
    }
}