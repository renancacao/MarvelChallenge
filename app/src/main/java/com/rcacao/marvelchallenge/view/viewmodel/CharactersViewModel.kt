package com.rcacao.marvelchallenge.view.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.CombinedLoadStates
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.rcacao.marvelchallenge.domain.model.Event
import com.rcacao.marvelchallenge.domain.model.character.CharacterModel
import com.rcacao.marvelchallenge.domain.usecases.GetCharactersPagingUseCase.CharacterPagingRequest
import com.rcacao.marvelchallenge.domain.usecases.UseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ExperimentalCoroutinesApi
class CharactersViewModel @ViewModelInject @Inject constructor(
    private val getCharactersPagingUseCase: UseCase<CharacterPagingRequest, Flow<PagingData<CharacterModel>>>,
    private val loadStateStatusHelper: LoadStateStatusHelper
) :
    ViewModel() {

    var currentQuery: String = ""

    private val mutableErrorMessage = MutableLiveData<Event<String>>()
    val errorMessage: LiveData<Event<String>>
        get() = mutableErrorMessage

    private val mutableListVisibility = MutableLiveData<Boolean>()
    val listVisibility: LiveData<Boolean>
        get() = mutableListVisibility

    private val mutableLoadingVisibility = MutableLiveData<Boolean>()
    val loadingVisibility: LiveData<Boolean>
        get() = mutableLoadingVisibility

    private val mutableErrorVisibility = MutableLiveData<Boolean>()
    val errorVisibility: LiveData<Boolean>
        get() = mutableErrorVisibility

    suspend fun searchCharacter(queryString: String): Flow<PagingData<CharacterModel>> {
        val sameQuery: Boolean = queryString == currentQuery
        currentQuery = queryString
        return getCharactersPagingUseCase(CharacterPagingRequest(queryString, sameQuery)).cachedIn(
            viewModelScope
        )
    }

    fun stateChange(loadState: CombinedLoadStates) {
        setListVisibility(loadState)
        setLoadingVisibility(loadState)
        setRetryVisibility(loadState)
        handleError(loadState)
    }

    private fun handleError(loadState: CombinedLoadStates) {
        val error = loadStateStatusHelper.errorText(loadState)
        if (error.isNotEmpty()) {
            mutableErrorMessage.value = Event(error)
        }
    }

    private fun setListVisibility(loadState: CombinedLoadStates) {
        mutableListVisibility.value =
            loadStateStatusHelper.isSuccess(loadState) && !loadStateStatusHelper.isError(loadState)
    }

    private fun setLoadingVisibility(loadState: CombinedLoadStates) {
        mutableLoadingVisibility.value = loadStateStatusHelper.isLoading(loadState)
    }

    private fun setRetryVisibility(loadState: CombinedLoadStates) {
        mutableErrorVisibility.value = loadStateStatusHelper.isError(loadState)
    }


}