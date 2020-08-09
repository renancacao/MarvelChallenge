package com.rcacao.marvelchallenge.view.viewmodel

import javax.inject.Inject

class TextErrorHelper @Inject constructor() {
    operator fun invoke(errorMessage: String?): String =
        "\uD83D\uDE28 Wooops... " + (errorMessage ?: "Strange error!")
}
