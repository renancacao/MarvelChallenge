package com.rcacao.marvelchallenge.utils

import java.security.MessageDigest

fun String.toMD5(): String {
    val bytes = MessageDigest.getInstance("MD5").digest(this.toByteArray())
    return bytes.joinToString("") { "%02x".format(it) }
}
