package com.example.githubuser.data.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class User(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    var id: String,

    @ColumnInfo(name = "login")
    var login: String,

    @ColumnInfo(name = "avatarUrl")
    var avatarUrl: String,

    @ColumnInfo(name = "nodeId")
    var nodeId: String,

    @ColumnInfo(name = "name")
    var name: String
) : Parcelable
