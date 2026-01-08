package com.logistiq.app.ui.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import com.logistiq.app.data.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import androidx.compose.runtime.*

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: AuthRepository
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

                val response = repository.login(email, password)

                Log.d("LOGIN_FLOW", "Response: ${response.code()}")

                if (response.isSuccessful) {
                    val result = response.body()

                    if (result == null) {
                        Log.e("LOGIN_FLOW", "Response body veio null")
                        errorMessage = "Erro ao processar resposta do servidor"
                        return@launch
                    }

                    val user = result.user
                    if (user == null) {
                        Log.e("LOGIN_FLOW", "Usuário veio null no response")
                        errorMessage = "Erro ao carregar dados do usuário"
                        return@launch
                    }

                    Log.d("LOGIN_FLOW", "Usuário logado: ${result.user.name}")
                    Log.d("LOGIN_FLOW", "Token recebido")

                    repository.saveToken(result.token)

                    Log.d("LOGIN_FLOW", "Token salvo com sucesso")

                } else {
                    errorMessage = "Erro ${response.code()}: ${response.message()}"
                    Log.e("LOGIN_FLOW", "Login falhou")
                }

            } catch (e: Exception) {
                Log.e("LOGIN", "Erro na requisição", e)
                errorMessage = "Erro ao se conectar com o servidor"
            } finally {
                isLoading = false
            }
        }
    }
}
