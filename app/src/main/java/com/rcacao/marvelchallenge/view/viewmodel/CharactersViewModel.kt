package com.rcacao.marvelchallenge.view.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.rcacao.marvelchallenge.data.CharacterResponse
import com.rcacao.marvelchallenge.data.repository.CharactersRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CharactersViewModel @ViewModelInject @Inject constructor(private val repository: CharactersRepository) :
    ViewModel() {

    private var currentQueryValue: String? = null
    private var currentSearchResult: Flow<PagingData<CharacterResponse>>? = null

    fun searchRepo(queryString: String): Flow<PagingData<CharacterResponse>> {
        val lastResult: Flow<PagingData<CharacterResponse>>? = currentSearchResult
        if (queryString == currentQueryValue && lastResult != null) {
            return lastResult
        }
        currentQueryValue = queryString
        val newResult: Flow<PagingData<CharacterResponse>> =
            repository.getCharacters().cachedIn(viewModelScope)
        currentSearchResult = newResult
        return newResult
    }

}