package com.clevmania.velatest.data.model


import com.google.gson.annotations.SerializedName

data class Parent(
    @SerializedName("html_url")
    val htmlUrl: String,
    val sha: String,
    val url: String
)