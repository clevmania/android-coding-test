package com.clevmania.velatest.data.repository

import androidx.lifecycle.LiveData
import com.clevmania.velatest.data.model.CommitResponse

class VelaRepositoryImpl : VelaRepository {
    override suspend fun retrieveCommits(): LiveData<List<CommitResponse>> {

    }

    override suspend fun fetchDevelopersCommit(page: String, commitsPerPage: String) {
    }
}