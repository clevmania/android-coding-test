package com.clevmania.velatest.data.model

data class CommitModel(val authorsName:String, val commitId: String, val message: String)

data class ParamsModel(val page: String, val perPage: String)