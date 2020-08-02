package com.rcacao.marvelchallenge.view.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.rcacao.marvelchallenge.data.CharacterResponse
import com.rcacao.marvelchallenge.data.datasource.CharactersDataSourceFactory
import javax.inject.Inject

class CharactersViewModel @ViewModelInject @Inject constructor(dataSourceFactory: CharactersDataSourceFactory) :
    ViewModel() {

    var charactersLiveData: LiveData<PagedList<CharacterResponse>>

    init {
        val config: PagedList.Config = PagedList.Config.Builder()
            .setPageSize(20)
            .setEnablePlaceholders(true)
            .build()
        charactersLiveData = LivePagedListBuilder(dataSourceFactory, config).build()
    }

}