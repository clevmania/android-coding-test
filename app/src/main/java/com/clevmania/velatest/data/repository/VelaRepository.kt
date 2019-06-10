package com.clevmania.velatest.data.repository

import androidx.lifecycle.LiveData
import com.clevmania.velatest.data.model.CommitResponse

interface VelaRepository {
    suspend fun retrieveCommits(): LiveData<List<CommitResponse>>

    suspend fun fetchDevelopersCommit(page: String, commitsPerPage: String)
}