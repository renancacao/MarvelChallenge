package com.rcacao.marvelchallenge.data.utils

class MarvelApiHelper {
    fun timeStamp(): String {
        val tsLong: Long = System.currentTimeMillis() / 1000
        return tsLong.toString()
    }
}