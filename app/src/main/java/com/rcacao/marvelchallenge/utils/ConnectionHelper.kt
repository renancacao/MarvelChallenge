package com.rcacao.marvelchallenge.utils

interface ConnectionHelper {
    var isNetworkConnected: Boolean
    fun isConnected(): Boolean
    fun registerNetworkCallback()
}