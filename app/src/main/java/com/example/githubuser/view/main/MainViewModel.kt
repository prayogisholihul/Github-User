package com.example.githubuser.view.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubuser.data.Repository
import com.example.githubuser.data.response.SearchUserResponse
import com.example.githubuser.utils.Resource
import kotlinx.coroutines.launch

class MainViewModel(private val repository: Repository) : ViewModel() {
    var userSearchResponse: MutableLiveData<Resource<SearchUserResponse>> = MutableLiveData()

    init {
        fetchSearchUser("")
    }

    fun fetchSearchUser(user: String) = viewModelScope.launch {
        userSearchResponse.postValue(Resource.Loading())
        try {
            val response = repository.getSearch(user)
            userSearchResponse.postValue(Resource.Success(response.body()!!))
        } catch (e: Exception) {
            userSearchResponse.postValue(Resource.Error(e.message.toString()))
        }
    }
}
