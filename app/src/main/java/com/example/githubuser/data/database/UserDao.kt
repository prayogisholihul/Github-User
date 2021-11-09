package com.example.githubuser.data.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: User)

    @Delete
    suspend fun delete(user: User)

    @Query("SELECT * from user")
    fun getAllUsers(): LiveData<List<User>>

    @Query("SELECT * from user WHERE id=:id")
    fun getUserByID(id: String): LiveData<User>
}
