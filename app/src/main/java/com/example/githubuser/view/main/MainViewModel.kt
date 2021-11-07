package com.example.githubuser.view.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.githubuser.data.DataStoreTheme
import com.example.githubuser.data.Repository
import com.example.githubuser.data.response.SearchUserResponse
import com.example.githubuser.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val repository: Repository, application: Application) :
    AndroidViewModel(application) {
    private val _userSearchResponse: MutableLiveData<Resource<SearchUserResponse?>> =
        MutableLiveData()
    var userSearch: LiveData<Resource<SearchUserResponse?>> = _userSearchResponse

    init {
        fetchSearchUser("")
    }

    fun fetchSearchUser(user: String) = viewModelScope.launch {
        _userSearchResponse.postValue(Resource.Loading())
        try {
            val response = repository.getSearch(user)
            _userSearchResponse.postValue(Resource.Success(response.body()))
        } catch (e: Exception) {
            _userSearchResponse.postValue(Resource.Error(e.message.toString()))
        }
    }

    private val uiDataStore = DataStoreTheme.getInstance(application)

    // get
    val getUIMode = uiDataStore.uiMode

    // set
    fun saveToDataStore(isNightMode: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            uiDataStore.saveToDataStore(isNightMode)
        }
    }
}
