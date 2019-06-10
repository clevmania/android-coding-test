package com.clevmania.velatest.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.clevmania.velatest.R
import com.clevmania.velatest.data.model.CommitModel
import com.clevmania.velatest.data.network.VelaDataSourceImpl
import com.clevmania.velatest.data.network.api.VelaApiService
import com.clevmania.velatest.data.network.interceptor.ConnectivityInterceptorImpl
import com.clevmania.velatest.data.repository.VelaRepositoryImpl
import com.clevmania.velatest.ui.adapter.CommitsAdapter
import com.clevmania.velatest.viewmodel.VelaViewModel
import com.clevmania.velatest.viewmodel.VelaViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity(), CoroutineScope {
    private lateinit var viewModel : VelaViewModel
    private lateinit var job: Job

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        loadCommits()
        initFactory()
        bindUiToViewModel()
    }

    private fun initFactory(){
        job = Job()
        val apiService = VelaApiService(ConnectivityInterceptorImpl(this))

        val repository = VelaRepositoryImpl(VelaDataSourceImpl(apiService, applicationContext))
        val viewModelFactory = VelaViewModelFactory(repository)
        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(VelaViewModel::class.java)
    }
/*
    private fun loadCommits(){
        val commitsList = arrayListOf<CommitModel>()
        pb_commit.visibility = View.VISIBLE
        val apiService = VelaApiService(ConnectivityInterceptorImpl(this))
        val velaDataSource = VelaDataSourceImpl(apiService,this.applicationContext)

        velaDataSource.retrieveDevsCommits.observe(this, Observer {
            if(it == null){
                pb_commit.visibility = View.GONE
                tv_status.text = getString(R.string.cannot_fetch)
                return@Observer
            }

            if(it.isNullOrEmpty()){
                tv_status.text = getString(R.string.no_commits_found)
                pb_commit.visibility = View.GONE
                return@Observer
            }

            pb_commit.visibility = View.GONE
            tv_status.visibility = View.GONE

            it.forEach {devsCommit->
                commitsList.add(CommitModel(devsCommit.commit.author.name, devsCommit.sha, devsCommit.commit.message))
            }

            rv_commits.setHasFixedSize(true)
            rv_commits.layoutManager = LinearLayoutManager(this)
            rv_commits.adapter = CommitsAdapter(commitsList)

        })

        GlobalScope.launch {
            velaDataSource.fetchDevelopersCommits("1","25")
        }
    }*/

    private fun bindUiToViewModel() = launch {
        val commitsList = arrayListOf<CommitModel>()
        pb_commit.visibility = View.VISIBLE
        viewModel.retrieveAllCommits("1","25")

        val devsCommit = viewModel.allDevelopersCommit.await()
        devsCommit.observe(this@MainActivity, Observer {
            if(it == null){
                pb_commit.visibility = View.GONE
                tv_status.text = getString(R.string.cannot_fetch)
                return@Observer
            }

            if(it.isNullOrEmpty()){
                tv_status.text = getString(R.string.no_commits_found)
                pb_commit.visibility = View.GONE
                return@Observer
            }

            pb_commit.visibility = View.GONE
            tv_status.visibility = View.GONE

            it.forEach {devsCommit->
                commitsList.add(CommitModel(devsCommit.commit.author.name, devsCommit.sha, devsCommit.commit.message))
            }

            rv_commits.setHasFixedSize(true)
            rv_commits.layoutManager = LinearLayoutManager(this@MainActivity)
            rv_commits.adapter = CommitsAdapter(commitsList)
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

}
