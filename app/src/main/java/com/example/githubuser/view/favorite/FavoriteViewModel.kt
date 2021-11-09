package com.example.githubuser.view.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubuser.data.Repository
import com.example.githubuser.data.database.User
import com.example.githubuser.data.response.DetailResponse
import kotlinx.coroutines.launch

class FavoriteViewModel(private val repository: Repository) : ViewModel() {

    fun insert(userDetail: DetailResponse?) = viewModelScope.launch {
        repository.insert(
            User(
                id = userDetail?.id.toString(),
                login = userDetail?.login.toString(),
                avatarUrl = userDetail?.avatar_url.toString(),
                nodeId = userDetail?.node_id.toString(),
                name = userDetail?.name.toString()
            )
        )
    }

    fun delete(user: User) = viewModelScope.launch {
        repository.delete(user)
    }

    fun getAllNotes(): LiveData<List<User>> = repository.getAllUsers()

    fun getUserById(id: String) = repository.getUserById(id)
}