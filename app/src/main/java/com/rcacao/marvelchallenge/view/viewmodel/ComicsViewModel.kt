package com.rcacao.marvelchallenge.view.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rcacao.marvelchallenge.domain.model.DataResult
import com.rcacao.marvelchallenge.domain.model.comics.ComicsModel
import com.rcacao.marvelchallenge.domain.usecases.GetComicsUseCase
import com.rcacao.marvelchallenge.view.model.ComicsStateUi
import kotlinx.coroutines.launch
import javax.inject.Inject

class ComicsViewModel @ViewModelInject @Inject constructor(private val getComicsUseCase: GetComicsUseCase) :
    ViewModel() {

    private val mutableComicsStateUi = MutableLiveData<ComicsStateUi>()
    val comicsStateUi: LiveData<ComicsStateUi>
        get() = mutableComicsStateUi

    private val mutableComicsList = MutableLiveData<List<ComicsModel>>()
    val comicsList: LiveData<List<ComicsModel>>
        get() = mutableComicsList

    fun getComics(charId: String) {
        viewModelScope.launch {
            when(val result: DataResult<List<ComicsModel>> = getComicsUseCase(charId)){
                is DataResult.Success -> mutableComicsList.value = result.data
                is DataResult.Error -> mutableComicsList.value = emptyList()
            }
        }
    }

}