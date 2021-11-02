package com.example.githubuser.view.detailUser.follower

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubuser.data.Repository
import com.example.githubuser.data.response.FollowerResponse
import com.example.githubuser.utils.Resource
import kotlinx.coroutines.launch

class FollowerViewModel(private val repository: Repository) : ViewModel() {
    var followerResponse = MutableLiveData<Resource<List<FollowerResponse>>>()

    fun fetchFollower(user: String) = viewModelScope.launch {
        followerResponse.value = Resource.Loading()
        try {
            val response = repository.getFollower(user)
            followerResponse.value = Resource.Success(response.body()!!)
        } catch (e: Exception) {
            followerResponse.value = Resource.Error(e.message.toString())
        }
    }
}
