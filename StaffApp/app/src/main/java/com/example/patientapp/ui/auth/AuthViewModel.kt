package com.example.patientapp.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.patientapp.data.model.User
import com.example.patientapp.data.network.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject

sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    data class Success(val user: User) : AuthState()
    data class Error(val message: String) : AuthState()
}

class AuthViewModel : ViewModel() {
    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState

    // The backend uses typical JSON `{ "username": "...", "password": "..." }`
    fun login(username: String, password: String, roleType: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            try {
                val jsonObject = JSONObject().apply {
                    put("username", username)
                    put("password", password)
                }
                val requestBody = jsonObject.toString().toRequestBody("application/json".toMediaTypeOrNull())

                var response = when (roleType) {
                    "DOCTOR" -> RetrofitClient.doctorAuthApi.login(requestBody)
                    else -> RetrofitClient.adminAuthApi.login(requestBody)
                }

                if (response.isSuccessful && response.body() != null) {
                    _authState.value = AuthState.Success(response.body()!!)
                } else {
                    _authState.value = AuthState.Error("Login failed: ${response.code()}")
                }
            } catch (e: Exception) {
                _authState.value = AuthState.Error("Error: ${e.message}")
            }
        }
    }


    fun resetState() {
        _authState.value = AuthState.Idle
    }
}
