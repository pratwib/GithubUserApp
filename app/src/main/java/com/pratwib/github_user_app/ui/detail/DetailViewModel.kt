package com.pratwib.github_user_app.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pratwib.github_user_app.datasource.UserResponse
import com.pratwib.github_user_app.networking.ApiConfig
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class DetailViewModel(username: String) : ViewModel() {
    private val _detailUser = MutableLiveData<UserResponse?>()
    val detailUser: LiveData<UserResponse?> = _detailUser
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    init {
        viewModelScope.launch { getDetailUser(username) }
        Log.i(TAG, "DetailViewModel is Created")
    }

    private suspend fun getDetailUser(username: String) {
        coroutineScope.launch {
            _isLoading.value = true
            val result = ApiConfig.getApiService().getDetailUserAsync(username)
            try {
                _isLoading.value = false
                _detailUser.postValue(result)
            } catch (e: Exception) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${e.message.toString()}")
            }
        }
    }

    companion object {
        private const val TAG = "DetailViewModel"
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}