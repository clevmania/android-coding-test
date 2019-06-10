package com.clevmania.velatest.data.network.interceptor

import android.content.Context
import android.net.ConnectivityManager
import com.clevmania.velatest.data.network.exception.NoConnectivityException
import okhttp3.Interceptor
import okhttp3.Response

class ConnectivityInterceptorImpl(context: Context) : ConnectivityInterceptor {
    private val appContext = context.applicationContext

    override fun intercept(chain: Interceptor.Chain): Response {
        if(!isNetworkAvailable())
            throw NoConnectivityException()
        return chain.proceed(chain.request())
    }

    private fun isNetworkAvailable(): Boolean{
        val connectivityManager = appContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }
}