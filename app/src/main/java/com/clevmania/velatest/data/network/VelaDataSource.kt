package com.clevmania.velatest.data.network

import androidx.lifecycle.LiveData
import com.clevmania.velatest.data.model.CommitResponse

interface VelaDataSource {
    val retrieveDevsCommits : LiveData<CommitResponse>

    suspend fun fetchDevelopersCommits(page: String, commitPerPage: String)
}