package com.rcacao.marvelchallenge.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.net.NetworkInfo
import android.os.Build
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ConnectionHelper @Inject constructor(@ApplicationContext private val context: Context) {

    private var isNetworkConnected = false

    fun isConnected(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            isNetworkConnected
        } else {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
            activeNetwork?.isConnectedOrConnecting == true
        }
    }

    fun registerNetworkCallback() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            try {
                val connectivityManager =
                    context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                connectivityManager.registerDefaultNetworkCallback(object : NetworkCallback() {
                    override fun onAvailable(network: Network) {
                        isNetworkConnected = true
                    }

                    override fun onLost(network: Network) {
                        isNetworkConnected = false
                    }
                })
                isNetworkConnected = false
            } catch (e: Exception) {
                isNetworkConnected = false
            }
        }
    }
}