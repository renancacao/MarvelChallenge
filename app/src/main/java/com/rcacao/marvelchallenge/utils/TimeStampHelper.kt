package com.rcacao.marvelchallenge.utils

class TimeStampHelper {
    fun timeStamp(): String {
        val tsLong: Long = System.currentTimeMillis() / 1000
        return tsLong.toString()
    }
}