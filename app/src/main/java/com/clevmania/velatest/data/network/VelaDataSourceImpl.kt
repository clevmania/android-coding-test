package com.clevmania.velatest.data.network

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.clevmania.velatest.data.model.CommitResponse
import com.clevmania.velatest.data.model.ParamsModel
import com.clevmania.velatest.data.network.api.VelaApiService
import com.clevmania.velatest.data.network.exception.NoConnectivityException

class VelaDataSourceImpl(private val velaApiService: VelaApiService,
                         private val context : Context
) : VelaDataSource {
    private val _retrieveDevsCommits = MutableLiveData<List<CommitResponse>>()

    override val retrieveDevsCommits: LiveData<List<CommitResponse>>
        get() = _retrieveDevsCommits

    override suspend fun fetchDevelopersCommits(page: String, commitPerPage: String) {
//        val params = ParamsModel(page,commitPerPage)
        try {
            val commitRequest = velaApiService.fetchCommits(page,commitPerPage).await()
            if(commitRequest.isSuccessful)
                _retrieveDevsCommits.postValue(commitRequest.body())
        }catch (e: NoConnectivityException){
            Toast.makeText(context, "Connectivity is turned off, please turn it on",Toast.LENGTH_LONG).show()
        }catch (ex: Exception){
            Log.e("Fetching Commits Failed", ex.toString())
        }
    }
}