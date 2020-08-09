package com.rcacao.marvelchallenge.utils

interface ConnectionHelper {
    fun isConnected(): Boolean
    fun registerNetworkCallback()
}