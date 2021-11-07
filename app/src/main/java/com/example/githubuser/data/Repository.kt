package com.example.githubuser.data

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.githubuser.data.api.ApiInterface
import com.example.githubuser.data.database.User
import com.example.githubuser.data.database.UserDao
import com.example.githubuser.data.database.UserRoomDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class Repository(
    private val api: ApiInterface,
    application: Application
) {
    suspend fun getSearch(user: String) = api.searchUser(user)
    suspend fun getDetail(user: String) = api.detailUser(user)
    suspend fun getFollower(user: String) = api.follower(user)
    suspend fun getFollowing(user: String) = api.following(user)

    // RoomDatabase
    private val mUserDao: UserDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = UserRoomDatabase.getDatabase(application)
        mUserDao = db.userDao()
    }

    fun insert(user: User) {
        executorService.execute { mUserDao.insert(user) }
    }

    fun getAllUsers(): LiveData<List<User>> = mUserDao.getAllUsers()

    fun update(user: User) {
        executorService.execute { mUserDao.update(user) }
    }
}
