package com.rcacao.marvelchallenge.data

interface ApiHelper {
    fun getTimeStamp(): String
    fun getHash(ts: String): String
    fun getPublicKey(): String
    fun getOrderBy(): String
}