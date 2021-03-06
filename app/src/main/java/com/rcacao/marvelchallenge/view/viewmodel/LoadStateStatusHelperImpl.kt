package com.rcacao.marvelchallenge.view.viewmodel

import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import javax.inject.Inject

class LoadStateStatusHelperImpl @Inject constructor(private val textErrorHelper: TextErrorHelper) :
    LoadStateStatusHelper {

    override fun isSuccess(loadState: CombinedLoadStates) =
        loadState.source.refresh is LoadState.NotLoading

    override fun isError(loadState: CombinedLoadStates) =
        loadState.append is LoadState.Error
                || loadState.source.refresh is LoadState.Error

    override fun isLoading(loadState: CombinedLoadStates): Boolean =
        loadState.append is LoadState.Loading
                || loadState.source.refresh is LoadState.Loading

    override fun errorText(loadState: CombinedLoadStates): String {
        val errorState: LoadState.Error? = loadState.source.append as? LoadState.Error
            ?: loadState.source.prepend as? LoadState.Error
            ?: loadState.append as? LoadState.Error
            ?: loadState.refresh as? LoadState.Error
            ?: loadState.prepend as? LoadState.Error
        return errorState?.let {
            return textErrorHelper(it.error.message)
        } ?: ""
    }
}
