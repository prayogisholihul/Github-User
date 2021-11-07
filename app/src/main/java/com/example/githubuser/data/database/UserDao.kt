package com.example.githubuser.data.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(user: User)

    @Update
    fun update(user: User)

    @Query("SELECT * from user")
    fun getAllUsers(): LiveData<List<User>>
}
