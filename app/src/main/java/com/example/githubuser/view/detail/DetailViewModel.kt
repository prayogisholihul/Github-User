package com.example.githubuser.view.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubuser.data.Repository
import com.example.githubuser.data.database.User
import com.example.githubuser.data.response.DetailResponse
import com.example.githubuser.utils.Resource
import kotlinx.coroutines.launch

class DetailViewModel(private val repository: Repository) : ViewModel() {
    var detailResponse = MutableLiveData<Resource<DetailResponse?>>()

    fun fetchDetailUser(user: String) = viewModelScope.launch {
        detailResponse.value = Resource.Loading()
        try {
            val response = repository.getDetail(user)
            detailResponse.value = Resource.Success(response.body())
        } catch (e: Exception) {
            detailResponse.value = Resource.Error(e.message.toString())
        }
    }

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
}
