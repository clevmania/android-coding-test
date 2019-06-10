package com.clevmania.velatest.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.clevmania.velatest.R
import kotlinx.android.synthetic.main.item_commits.view.*

class CommitsAdapter(private val commitList : List<CommitModel>): RecyclerView.Adapter<CommitsAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_commits,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount() = commitList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(commitList[position])

    }

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        fun bindView(commits: CommitModel) {
            itemView.tv_author_name .text = commits.name
            itemView.tv_commit_id.text = commits.id
            itemView.tv_commit_message.text = commits.message
        }
    }
}