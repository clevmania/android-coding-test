package com.clevmania.velatest.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.clevmania.velatest.data.model.CommitResponse
import com.clevmania.velatest.data.network.VelaDataSource

class VelaRepositoryImpl(private val velaDataSource: VelaDataSource) : VelaRepository {
    private var allCommits = MutableLiveData<List<CommitResponse>>()

    override suspend fun retrieveCommits(): LiveData<List<CommitResponse>> {
        return allCommits
    }

    override suspend fun fetchDevelopersCommit(page: String, commitsPerPage: String) {
        velaDataSource.fetchDevelopersCommits(page,commitsPerPage)
    }

    init {
        velaDataSource.retrieveDevsCommits.observeForever {
            allCommits.value = it
        }
    }
}