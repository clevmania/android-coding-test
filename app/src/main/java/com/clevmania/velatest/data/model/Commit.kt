package com.clevmania.velatest.data.model


import com.google.gson.annotations.SerializedName

data class Commit(
    val author: AuthorX,
    @SerializedName("comment_count")
    val commentCount: Int,
    val committer: CommitterX,
    val message: String,
    val tree: Tree,
    val url: String,
    val verification: Verification
)