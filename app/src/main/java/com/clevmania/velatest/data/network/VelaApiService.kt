package com.clevmania.velatest.data.network

import com.clevmania.velatest.data.model.CommitResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.GET

const val baseUrl = "https://api.github.com/"

interface VelaApiService {
    @GET("repos/rails/rails/commits?page=1&per_page=25")
    fun fetchCommits(): Deferred<CommitResponse>
}