package com.clevmania.velatest.data.repository

import androidx.lifecycle.LiveData
import com.clevmania.velatest.data.model.CommitResponse

interface VelaRepository {
    suspend fun retrieveFarmers(): LiveData<List<CommitResponse>>

    suspend fun fetchSampleFarmers(page: String, commitsPerPage: String)
}