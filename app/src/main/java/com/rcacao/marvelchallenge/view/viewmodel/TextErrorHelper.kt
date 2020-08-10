package com.rcacao.marvelchallenge.view.viewmodel

interface TextErrorHelper {
    operator fun invoke(errorMessage: String?): String
}
