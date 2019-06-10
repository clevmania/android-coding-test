package com.clevmania.velatest.viewmodel

import androidx.lifecycle.ViewModel
import com.clevmania.velatest.data.repository.VelaRepository
import kotlinx.coroutines.*

class VelaViewModel(private val velaRepository: VelaRepository):ViewModel() {
    val allDevelopersCommit by lazyDeferred{velaRepository.retrieveCommits()}

    suspend fun retrieveAllCommits(noOfPages: String, noOfCommits : String)
            = velaRepository.fetchDevelopersCommit(noOfPages,noOfCommits)

    private fun<T> lazyDeferred(block: suspend CoroutineScope.() -> T): Lazy<Deferred<T>>{
        return lazy {
            GlobalScope.async(start = CoroutineStart.LAZY){
                block.invoke(this)
            }
        }
    }
}