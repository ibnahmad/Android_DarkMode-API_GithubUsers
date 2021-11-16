package com.dicoding.picodiploma.lastsubmission.data.response

data class DetailUserResponse(
    val avatar_url: String,
    val login: String,
    val name: String,
    val following_url: String,
    val followers_url: String,
    val public_repos: String,
    val location: String,
    val following: Int,
    val followers: Int
)
