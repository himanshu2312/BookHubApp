@file:Suppress("DEPRECATION")

package com.himanshu.bookhub.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

@Suppress("DEPRECATION")
class ConnectionManager {
    fun checkConnectivity(context: Context): Boolean{
         val connectivityManager=context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
         val activeNetworkInfo: NetworkInfo = connectivityManager.activeNetworkInfo ?: return false
         return activeNetworkInfo.isConnected
    }
}