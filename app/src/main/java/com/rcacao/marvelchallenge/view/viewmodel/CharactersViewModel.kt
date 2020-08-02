package com.rcacao.marvelchallenge.view.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.rcacao.marvelchallenge.data.CharacterResponse
import com.rcacao.marvelchallenge.data.repository.CharactersRepository
import javax.inject.Inject

class CharactersViewModel @ViewModelInject constructor(
    private val repository: CharactersRepository,
    val dataSourceFactory: DataSource.Factory<Int, CharacterResponse>
) :
    ViewModel() {

    @Inject
    lateinit var charactersLiveData: LiveData<PagedList<CharacterResponse>>

}