package com.example.githubuser.data

import com.example.githubuser.data.api.ApiInterface

class Repository(
    private val api: ApiInterface
) {
    suspend fun getSearch(user: String) = api.searchUser(user)
    suspend fun getDetail(user: String) = api.detailUser(user)
    suspend fun getFollower(user: String) = api.follower(user)
    suspend fun getFollowing(user: String) = api.following(user)
}
