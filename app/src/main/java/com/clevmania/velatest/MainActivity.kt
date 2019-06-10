package com.clevmania.velatest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.clevmania.velatest.data.model.CommitModel
import com.clevmania.velatest.data.network.VelaDataSourceImpl
import com.clevmania.velatest.data.network.api.VelaApiService
import com.clevmania.velatest.data.network.interceptor.ConnectivityInterceptorImpl
import com.clevmania.velatest.ui.CommitsAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadCommits()
    }

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
    }


}
