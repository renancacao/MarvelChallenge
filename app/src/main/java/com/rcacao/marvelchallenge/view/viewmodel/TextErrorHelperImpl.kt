package com.rcacao.marvelchallenge.view.viewmodel

import javax.inject.Inject

class TextErrorHelperImpl @Inject constructor() : TextErrorHelper {
    override operator fun invoke(errorMessage: String?): String =
        "\uD83D\uDE28 Wooops... " + (errorMessage ?: "Strange error!")
}
