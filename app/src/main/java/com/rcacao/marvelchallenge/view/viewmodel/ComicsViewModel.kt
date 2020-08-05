package com.rcacao.marvelchallenge.view.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rcacao.marvelchallenge.domain.model.DataResult
import com.rcacao.marvelchallenge.domain.model.comics.ComicsModel
import com.rcacao.marvelchallenge.domain.usecases.GetComicsUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class ComicsViewModel @ViewModelInject @Inject constructor(private val getComicsUseCase: GetComicsUseCase) :
    ViewModel() {


    fun getComics(charId: String) {
        viewModelScope.launch {
            val result: DataResult<List<ComicsModel>> = getComicsUseCase(charId)
        }
    }

}