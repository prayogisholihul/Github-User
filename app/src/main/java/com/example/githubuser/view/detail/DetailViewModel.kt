package com.example.githubuser.view.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubuser.data.Repository
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
}
