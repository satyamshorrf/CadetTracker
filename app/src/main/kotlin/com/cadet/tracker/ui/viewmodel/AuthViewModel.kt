package com.cadet.tracker.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cadet.tracker.data.models.User
import com.cadet.tracker.data.repository.AuthRepository
import com.cadet.tracker.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    private val _loginSuccess = MutableStateFlow(false)
    val loginSuccess: StateFlow<Boolean> = _loginSuccess.asStateFlow()

    init {
        _currentUser.value = authRepository.getCurrentUser()
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            when (val result = authRepository.login(email, password)) {
                is Result.Success -> {
                    _currentUser.value = result.data
                    _loginSuccess.value = true
                    Timber.d("Login successful")
                }
                is Result.Error -> {
                    _errorMessage.value = result.message
                    Timber.e("Login error: ${result.message}")
                }
                is Result.Loading -> {}
            }
            _isLoading.value = false
        }
    }

    fun register(
        name: String,
        email: String,
        phone: String,
        password: String,
        role: String,
        unit: String
    ) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            when (val result = authRepository.register(name, email, phone, password, role, unit)) {
                is Result.Success -> {
                    _currentUser.value = result.data
                    _loginSuccess.value = true
                    Timber.d("Registration successful")
                }
                is Result.Error -> {
                    _errorMessage.value = result.message
                    Timber.e("Registration error: ${result.message}")
                }
                is Result.Loading -> {}
            }
            _isLoading.value = false
        }
    }

    fun logout() {
        viewModelScope.launch {
            when (authRepository.logout()) {
                is Result.Success -> {
                    _currentUser.value = null
                    _loginSuccess.value = false
                    Timber.d("Logout successful")
                }
                is Result.Error -> {
                    _errorMessage.value = "Logout failed"
                }
                is Result.Loading -> {}
            }
        }
    }

    fun clearError() {
        _errorMessage.value = null
    }
}
