package com.rcacao.marvelchallenge.utils

interface ApiHelper {
    fun getTimeStamp(): String
    fun getHash(ts: String): String
    fun getPublicKey(): String
    fun getOrderBy(): String
}