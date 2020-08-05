package com.rcacao.marvelchallenge.view.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rcacao.marvelchallenge.domain.model.DataResult
import com.rcacao.marvelchallenge.domain.model.comics.ComicsModel
import com.rcacao.marvelchallenge.domain.usecases.GetComicsUseCase
import com.rcacao.marvelchallenge.view.model.details.comics.ComicsStateUi
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
        mutableComicsStateUi.value = ComicsStateUi.Loading
        viewModelScope.launch {
            when (val result: DataResult<List<ComicsModel>> = getComicsUseCase(charId)) {
                is DataResult.Success -> handleComicsListData(result.data)
                is DataResult.Error -> handleComicListError(result.exception.localizedMessage)
            }
        }
    }

    private fun handleComicListError(error: String?) {
        mutableComicsList.value = emptyList()
        mutableComicsStateUi.value = ComicsStateUi.Error(error ?: "Error loading list")
    }

    private fun handleComicsListData(data: List<ComicsModel>) {
        mutableComicsStateUi.value = ComicsStateUi.Loaded
        mutableComicsList.value = data
        if (data.isEmpty()) {
            mutableComicsStateUi.value = ComicsStateUi.Empty
        }
    }

}