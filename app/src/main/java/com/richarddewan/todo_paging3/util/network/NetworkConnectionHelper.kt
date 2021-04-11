package com.richarddewan.todo_paging3.util.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.os.Build
import javax.inject.Singleton

@Singleton
class NetworkConnectionHelper(private val context: Context) {

    fun isNetworkConnected(): Boolean {
        var result = false
        (context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).apply {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                result = isCapableNetwork(this,this.activeNetwork)
            }
            else {
                val networkInfo = this.allNetworks
                networkInfo.forEach { network ->
                    return isCapableNetwork(this, network)
                }
            }
        }

        return result
    }

    private fun isCapableNetwork(cm: ConnectivityManager, network: Network?) : Boolean {
        cm.getNetworkCapabilities(network)?.also {
            when  {
                it.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> return true

                it.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> return true
            }
        }

        return false
    }
}