package com.rcacao.marvelchallenge.view.model.details.series

sealed class SeriesStateUi {
    object Loading : SeriesStateUi()
    object Loaded : SeriesStateUi()
    object Empty : SeriesStateUi()
    data class Error(val errorMsg: String) : SeriesStateUi()
}
