package com.rcacao.marvelchallenge.data

import com.rcacao.marvelchallenge.BuildConfig
import java.security.MessageDigest
import javax.inject.Inject

class ApiHelperImpl @Inject constructor() :
    ApiHelper {

    private val privateKey: String = BuildConfig.PRIVATE_APIKEY

    override fun getTimeStamp(): String {
        val tsLong: Long = System.currentTimeMillis() / 1000
        return tsLong.toString()
    }

    override fun getHash(ts: String): String {
        return md5(ts + privateKey + getPublicKey())
    }

    override fun getPublicKey(): String = BuildConfig.APIKEY

    override fun getOrderBy(): String = "name"

    private fun md5(text: String): String {
        val bytes: ByteArray = MessageDigest.getInstance("MD5").digest(text.toByteArray())
        return bytes.joinToString("") { "%02x".format(it) }
    }
}
