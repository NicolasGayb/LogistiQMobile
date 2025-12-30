package com.logistiq.app.ui.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.logistiq.app.network.model.AuthApi
import kotlinx.coroutines.launch
import androidx.compose.runtime.mutableStateOf
import com.logistiq.app.data.remote.RetrofitInstance
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import com.logistiq.app.data.auth.AuthRepository
import com.logistiq.app.network.model.LoginRequest

class LoginViewModel(
    private val repository: AuthRepository =
        AuthRepository(RetrofitInstance.api)
) : ViewModel() {

    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var isLoading by mutableStateOf(false)
    var errorMessage by mutableStateOf<String?>(null)

    fun onEmailChange(value: String) {
        email = value
        errorMessage = null
    }

    fun onPasswordChange(value: String) {
        password = value
        errorMessage = null
    }

    fun login() {
        if (email.isBlank() || password.isBlank()) {
            errorMessage = "Por favor, preencha todos os campos."
            return
        }

        viewModelScope.launch {
            isLoading = true
            errorMessage = null

            try {
                Log.d("LOGIN_FLOW", "Chamando api.login()")

                val request = LoginRequest(
                    email = email,
                    password = password
                )

                val response = repository.login(email, password)

                Log.d("LOGIN_FLOW", "Response: ${response.code()}")

                if (response.isSuccessful) {
                    // Login bem-sucedido
                    Log.d("LOGIN_FLOW", "Login bem-sucedido")
                } else {
                    // Login falhou
                    errorMessage = "Erro ${response.code()}: ${response.message()}"
                    Log.d("LOGIN_FLOW", "Login falhou")
                }
            }
            catch (e: Exception) {
                Log.e("LOGIN", "Erro na requisição", e)
                errorMessage = "Erro de conexão"
            } finally {
                isLoading = false
            }
        }
    }
}
