package com.dicoding.picodiploma.lastsubmission.api

import com.dicoding.picodiploma.lastsubmission.data.response.DetailUserResponse
import com.dicoding.picodiploma.lastsubmission.data.response.User
import com.dicoding.picodiploma.lastsubmission.data.response.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {
    @GET("search/users")
    @Headers("Authorization: token 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")
    fun getSearchUsers(
        @Query("q") query: String,
    ): Call<UserResponse>

    @GET("users/{username}")
    @Headers("Authorization: token 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")
    fun getDetailUsers(
        @Path("username") username: String,
    ): Call<DetailUserResponse>

    @GET("users/{username}/following")
    @Headers("Authorization: token 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")
    fun getFollowingUsers(
        @Path("username") username: String,
    ): Call<ArrayList<User>>


    @GET("users/{username}/followers")
    @Headers("Authorization: token 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")
    fun getFollowersUsers(
        @Path("username") username: String,
    ): Call<ArrayList<User>>

}