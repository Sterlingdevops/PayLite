package com.sterlingng.paylite.utils

import android.content.Context
import android.net.ConnectivityManager

/**
 * Created by rtukpe on 13/03/2018.
 */

object NetworkUtils {

    fun isNetworkConnected(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting
    }
}// This utility class is not publicly instantiable
