package com.logistiq.app.ui.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.logistiq.app.network.model.AuthApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.logistiq.app.data.AuthRepository
import com.google.gson.Gson
import com.logistiq.app.network.model.SignupRequest
import android.util.Log
import com.logistiq.app.network.model.User
import com.logistiq.app.network.model.Company

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val authApi: AuthApi
) : ViewModel() {

    var name by mutableStateOf("")
        private set

    var email by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

    var companyName by mutableStateOf("")
        private set

    // 🔒 SEMPRE armazenar apenas números
    var companyCNPJ by mutableStateOf("")
        private set

    private val _signupState = MutableStateFlow<SignupState>(SignupState.Idle)
    val signupState: StateFlow<SignupState> = _signupState

    val isFormValid: Boolean
        get() = name.isNotBlank() &&
                email.isNotBlank() &&
                password.length >= 6 &&
                companyName.isNotBlank() &&
                companyCNPJ.length == 14 // 14 dígitos reais

    fun onNameChange(value: String) {
        name = value
    }

    fun onEmailChange(value: String) {
        email = value
    }

    fun onPasswordChange(value: String) {
        password = value
    }

    fun onCompanyNameChange(value: String) {
        companyName = value
    }

    fun onCompanyCNPJChange(value: String) {
        // 🔒 garante apenas números, sem máscara
        companyCNPJ = value.filter { it.isDigit() }
    }

    fun signup() {
        viewModelScope.launch {
            _signupState.value = SignupState.Loading

            try {
                val request = SignupRequest(
                    company = Company(
                        name = companyName,
                        document = companyCNPJ
                    ),
                    user = User(
                        name = name,
                        email = email,
                        password = password,
                    )
                )

                // 🔍 LOG CRÍTICO PARA DEBUG
                Log.d("SIGNUP_REQUEST", Gson().toJson(request))

                val response = authApi.signup(request)

                if (response.isSuccessful) {
                    val body = response.body()
                        ?: throw Exception("Resposta vazia do servidor")

                    _signupState.value = SignupState.Success(body)
                } else {
                    throw Exception(
                        "Erro no cadastro: ${response.code()} ${response.message()}"
                    )
                }

            } catch (e: Exception) {
                Log.e("SIGNUP_ERROR", "Erro ao cadastrar", e)
                _signupState.value =
                    SignupState.Error(e.message ?: "Erro desconhecido")
            }
        }
    }
}
