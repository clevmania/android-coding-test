package com.clevmania.velatest.data.network

import com.clevmania.velatest.data.model.CommitResponse
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import java.util.concurrent.TimeUnit

const val baseUrl = "https://api.github.com/"

interface VelaApiService {
    @GET("repos/rails/rails/commits?page=1&per_page=25")
    fun fetchCommits(): Deferred<CommitResponse>

    companion object {
        operator fun invoke(connectivityInterceptor: ConnectivityInterceptor): VelaApiService {
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(connectivityInterceptor)
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(baseUrl)
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(VelaApiService::class.java)
        }
    }
}