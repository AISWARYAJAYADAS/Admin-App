package com.example.adminapp.screens.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.adminapp.api.ApiService
import com.example.adminapp.model.user.GetUsersResponseItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(private val apiService: ApiService) : ViewModel() {
    private val _users = MutableStateFlow<List<GetUsersResponseItem>>(emptyList())
    val users: StateFlow<List<GetUsersResponseItem>> = _users

    private val _userState = MutableStateFlow<NetworkState<GetUsersResponseItem>?>(null)
    val userState: StateFlow<NetworkState<GetUsersResponseItem>?> = _userState

    fun fetchUsers() {
        viewModelScope.launch {
            _users.value = apiService.getUsers()
        }
    }

    fun getUserDetails(userId: String) {
        viewModelScope.launch {
            _userState.value = NetworkState.Loading
            try {
                val user = apiService.getUserDetails(userId)
                _userState.value = NetworkState.Success(user)
            } catch (e: Exception) {
                _userState.value = NetworkState.Error(e.message)
            }
        }
    }
}

sealed class NetworkState<out T> {
    object Idle : NetworkState<Nothing>()
    object Loading : NetworkState<Nothing>()
    data class Success<T>(val data: T) : NetworkState<T>()
    data class Error(val message: String?) : NetworkState<Nothing>()
}
