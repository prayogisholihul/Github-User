package com.example.githubuser.data.api

import com.example.githubuser.data.response.DetailResponse
import com.example.githubuser.data.response.FollowerResponse
import com.example.githubuser.data.response.FollowingResponse
import com.example.githubuser.data.response.SearchUserResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {

    @GET("search/users")
    suspend fun searchUser(
        @Query("q") user: String
    ): Response<SearchUserResponse>

    @GET("users/{user}")
    suspend fun detailUser(
        @Path("user") user: String
    ): Response<DetailResponse>

    @GET("users/{user}/followers")
    suspend fun follower(
        @Path("user") user: String
    ): Response<List<FollowerResponse>>

    @GET("users/{user}/following")
    suspend fun following(
        @Path("user") user: String
    ): Response<List<FollowingResponse>>
}
