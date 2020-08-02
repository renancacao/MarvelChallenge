package com.rcacao.marvelchallenge.view.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.rcacao.marvelchallenge.data.CharacterResponse
import com.rcacao.marvelchallenge.data.repository.CharactersRepository
import javax.inject.Inject

class CharactersViewModel @ViewModelInject constructor(private val repository: CharactersRepository) :
    ViewModel() {

    @Inject
    lateinit var charactersLiveData: LiveData<PagedList<CharacterResponse>>

}