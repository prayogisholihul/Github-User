package com.example.githubuser.view.detail.following

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubuser.data.Repository
import com.example.githubuser.data.response.FollowingResponse
import com.example.githubuser.utils.Resource
import kotlinx.coroutines.launch

class FollowingViewModel(private val repository: Repository) : ViewModel() {
    var followingResponse = MutableLiveData<Resource<List<FollowingResponse>>>()

    fun fetchFollowing(user: String) = viewModelScope.launch {
        followingResponse.value = Resource.Loading()
        try {
            val response = repository.getFollowing(user)
            followingResponse.value = Resource.Success(response.body()!!)
        } catch (e: Exception) {
            followingResponse.value = Resource.Error(e.message.toString())
        }
    }
}
