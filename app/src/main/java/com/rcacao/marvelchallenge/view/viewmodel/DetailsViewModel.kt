package com.rcacao.marvelchallenge.view.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rcacao.marvelchallenge.domain.model.DataResult
import com.rcacao.marvelchallenge.domain.model.comics.ComicsModel
import com.rcacao.marvelchallenge.domain.model.series.SeriesModel
import com.rcacao.marvelchallenge.domain.usecases.UseCase
import com.rcacao.marvelchallenge.view.model.details.comics.ComicsStateUi
import com.rcacao.marvelchallenge.view.model.details.series.SeriesStateUi
import kotlinx.coroutines.launch
import javax.inject.Inject

class DetailsViewModel @ViewModelInject @Inject constructor(
    private val getComicsUseCase: UseCase<String, DataResult<List<ComicsModel>>>,
    private val getSeriesUseCase: UseCase<String, DataResult<List<SeriesModel>>>,
    private val textErrorHelper: TextErrorHelper
) :
    ViewModel() {

    private val mutableComicsStateUi = MutableLiveData<ComicsStateUi>()
    val comicsStateUi: LiveData<ComicsStateUi>
        get() = mutableComicsStateUi

    private val mutableComicsList = MutableLiveData<List<ComicsModel>>()
    val comicsList: LiveData<List<ComicsModel>>
        get() = mutableComicsList

    private val mutableSeriesStateUi = MutableLiveData<SeriesStateUi>()
    val seriesStateUi: LiveData<SeriesStateUi>
        get() = mutableSeriesStateUi

    private val mutableSeriesList = MutableLiveData<List<SeriesModel>>()
    val seriesList: LiveData<List<SeriesModel>>
        get() = mutableSeriesList

    fun getComics(charId: String) {
        mutableComicsStateUi.value = ComicsStateUi.Loading
        viewModelScope.launch {
            when (val result: DataResult<List<ComicsModel>> = getComicsUseCase(charId)) {
                is DataResult.Success -> handleComicsListData(result.data)
                is DataResult.Error -> handleComicListError(result.exception.message)
            }
        }
    }

    private fun handleComicListError(error: String?) {
        mutableComicsList.value = emptyList()
        mutableComicsStateUi.value = ComicsStateUi.Error(textErrorHelper(error))
    }

    private fun handleComicsListData(data: List<ComicsModel>) {
        mutableComicsStateUi.value = ComicsStateUi.Loaded
        mutableComicsList.value = data
        if (data.isEmpty()) {
            mutableComicsStateUi.value = ComicsStateUi.Empty
        }
    }


    fun getSeries(charId: String) {
        mutableSeriesStateUi.value = SeriesStateUi.Loading
        viewModelScope.launch {
            when (val result: DataResult<List<SeriesModel>> = getSeriesUseCase(charId)) {
                is DataResult.Success -> handleSeriesListData(result.data)
                is DataResult.Error -> handleSeriesListError(result.exception.message)
            }
        }
    }

    private fun handleSeriesListError(error: String?) {
        mutableSeriesList.value = emptyList()
        mutableSeriesStateUi.value = SeriesStateUi.Error(textErrorHelper(error))
    }

    private fun handleSeriesListData(data: List<SeriesModel>) {
        mutableSeriesStateUi.value = SeriesStateUi.Loaded
        mutableSeriesList.value = data
        if (data.isEmpty()) {
            mutableSeriesStateUi.value = SeriesStateUi.Empty
        }
    }

}