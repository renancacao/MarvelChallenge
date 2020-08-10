package com.rcacao.marvelchallenge.view.model.details.comics

sealed class ComicsStateUi {
    object Loading: ComicsStateUi()
    object Loaded: ComicsStateUi()
    object Empty: ComicsStateUi()
    data class Error(val errorMsg: String) : ComicsStateUi()
}
