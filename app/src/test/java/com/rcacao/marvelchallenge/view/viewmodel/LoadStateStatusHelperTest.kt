package com.rcacao.marvelchallenge.view.viewmodel

import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.LoadStates
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test


class LoadStateStatusHelperTest {

    private lateinit var loadStateStatusHelperTest: LoadStateStatusHelperImpl

    @Before
    fun setup() {
        loadStateStatusHelperTest = LoadStateStatusHelperImpl(TextErrorHelperImpl())
    }

    @Test
    fun isSuccessTrue() {
        val refresh = LoadState.NotLoading(false)
        val prepend = LoadState.NotLoading(false)
        val append = LoadState.NotLoading(false)

        val loadStates = LoadStates(refresh, prepend, append)
        val combinedLoadState = CombinedLoadStates(loadStates)

        assertTrue(loadStateStatusHelperTest.isSuccess(combinedLoadState))
    }

    @Test
    fun isSuccessFalse() {
        val refresh = LoadState.Loading
        val prepend = LoadState.NotLoading(false)
        val append = LoadState.NotLoading(false)

        val loadStates = LoadStates(refresh, prepend, append)
        val combinedLoadState = CombinedLoadStates(loadStates)

        assertFalse(loadStateStatusHelperTest.isSuccess(combinedLoadState))
    }

    @Test
    fun isErrorTrueAppend() {
        val refresh = LoadState.NotLoading(false)
        val prepend = LoadState.NotLoading(false)
        val append = LoadState.Error(Exception())

        val loadStates = LoadStates(refresh, prepend, append)
        val combinedLoadState = CombinedLoadStates(loadStates)

        assertTrue(loadStateStatusHelperTest.isError(combinedLoadState))
    }

    @Test
    fun isErrorTrueRefresh() {
        val refresh = LoadState.Error(Exception())
        val prepend = LoadState.NotLoading(false)
        val append = LoadState.NotLoading(false)

        val loadStates = LoadStates(refresh, prepend, append)
        val combinedLoadState = CombinedLoadStates(loadStates)

        assertTrue(loadStateStatusHelperTest.isError(combinedLoadState))
    }

    @Test
    fun isErrorFalse() {
        val refresh = LoadState.NotLoading(false)
        val prepend = LoadState.NotLoading(false)
        val append = LoadState.NotLoading(false)

        val loadStates = LoadStates(refresh, prepend, append)
        val combinedLoadState = CombinedLoadStates(loadStates)

        assertFalse(loadStateStatusHelperTest.isError(combinedLoadState))
    }

    @Test
    fun isLoadingTrueRefresh() {
        val refresh = LoadState.Loading
        val prepend = LoadState.NotLoading(false)
        val append = LoadState.NotLoading(false)

        val loadStates = LoadStates(refresh, prepend, append)
        val combinedLoadState = CombinedLoadStates(loadStates)

        assertTrue(loadStateStatusHelperTest.isLoading(combinedLoadState))
    }

    @Test
    fun isLoadingTrueAppend() {
        val refresh = LoadState.NotLoading(false)
        val prepend = LoadState.NotLoading(false)
        val append = LoadState.Loading

        val loadStates = LoadStates(refresh, prepend, append)
        val combinedLoadState = CombinedLoadStates(loadStates)

        assertTrue(loadStateStatusHelperTest.isLoading(combinedLoadState))
    }

    @Test
    fun isLoadingFalse() {
        val refresh = LoadState.NotLoading(false)
        val prepend = LoadState.Loading
        val append = LoadState.NotLoading(false)

        val loadStates = LoadStates(refresh, prepend, append)
        val combinedLoadState = CombinedLoadStates(loadStates)

        assertFalse(loadStateStatusHelperTest.isLoading(combinedLoadState))
    }

    @Test
    fun errorTextSourceAppend() {
        val refresh = LoadState.NotLoading(false)
        val prepend = LoadState.NotLoading(false)
        val append = LoadState.Error(Exception("message"))

        val loadStates = LoadStates(refresh, prepend, append)
        val combinedLoadState = CombinedLoadStates(loadStates)

        assertTrue(loadStateStatusHelperTest.errorText(combinedLoadState).contains("message"))
    }

    @Test
    fun errorTextSourcePrepend() {
        val refresh = LoadState.NotLoading(false)
        val prepend = LoadState.Error(Exception("message"))
        val append = LoadState.NotLoading(false)

        val loadStates = LoadStates(refresh, prepend, append)
        val combinedLoadState = CombinedLoadStates(loadStates)

        assertTrue(loadStateStatusHelperTest.errorText(combinedLoadState).contains("message"))
    }


}