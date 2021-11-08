package com.example.githubuser.data

import com.example.githubuser.data.api.ApiInterface
import com.example.githubuser.data.database.User
import com.example.githubuser.data.database.UserRoomDatabase

class Repository(
    private val api: ApiInterface,
    private val db: UserRoomDatabase
) {
    suspend fun getSearch(user: String) = api.searchUser(user)
    suspend fun getDetail(user: String) = api.detailUser(user)
    suspend fun getFollower(user: String) = api.follower(user)
    suspend fun getFollowing(user: String) = api.following(user)

    // RoomDatabase

    fun insert(user: User) {
        db.userDao().insert(user)
    }

    fun delete(user: User) {
        db.userDao().delete(user)
    }

    fun update(user: User) {
        db.userDao().update(user)
    }

    fun getAllUsers() = db.userDao().getAllUsers()
}
