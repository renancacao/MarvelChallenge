package com.rcacao.marvelchallenge.view.viewmodel

import androidx.paging.CombinedLoadStates

interface LoadStateStatusHelper {
    fun isSuccess(loadState: CombinedLoadStates): Boolean
    fun isError(loadState: CombinedLoadStates): Boolean
    fun isLoading(loadState: CombinedLoadStates): Boolean
    fun errorText(loadState: CombinedLoadStates): String
}
