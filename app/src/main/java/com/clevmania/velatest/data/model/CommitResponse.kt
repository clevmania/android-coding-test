package com.clevmania.velatest.data.model


import com.google.gson.annotations.SerializedName

data class CommitResponse(
    val author: Author,
    @SerializedName("comments_url")
    val commentsUrl: String,
    val commit: Commit,
    val committer: Committer,
    @SerializedName("html_url")
    val htmlUrl: String,
    @SerializedName("node_id")
    val nodeId: String,
    val parents: List<Parent>,
    val sha: String,
    val url: String
)