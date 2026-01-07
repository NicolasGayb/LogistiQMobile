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
import com.logistiq.app.data.auth.AuthRepository
import com.logistiq.app.util.formatCNPJ
import com.logistiq.app.network.model.SignupRequest
import com.logistiq.app.ui.signup.SignupState

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val authApi: AuthApi,
    private val repository: AuthRepository
) : ViewModel() {

    var name by mutableStateOf("")
        private set
    var email by mutableStateOf("")
        private set
    var password by mutableStateOf("")
        private set
    var companyName by mutableStateOf("")
        private set
    var companyCNPJ by mutableStateOf("")
        private set

    private val _signupState = MutableStateFlow<SignupState>(SignupState.Idle)
    val signupState: StateFlow<SignupState> = _signupState

    val isFormValid: Boolean
        get() = name.isNotBlank() &&
                email.isNotBlank() &&
                password.length >= 6 &&
                companyName.isNotBlank() &&
                companyCNPJ.length == 18 // CNPJ formatado: 18 caracteres

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
        companyCNPJ = formatCNPJ(value)
    }

    fun signup() {
        viewModelScope.launch {
            _signupState.value = SignupState.Loading
            try {
                val response = authApi.signup(
                    SignupRequest(
                        name = name,
                        email = email,
                        password = password,
                        companyName = companyName,
                        companyCNPJ = companyCNPJ
                    )
                )

                if (response.isSuccessful) {
                    val body = response.body() ?: throw Exception("Resposta vazia do servidor")
                    _signupState.value = SignupState.Success(body)
                } else {
                    throw Exception("Erro no cadastro: ${response.code()} ${response.message()}")
                }
            } catch (e: Exception) {
                _signupState.value = SignupState.Error(e.message ?: "Erro desconhecido")
            }
        }
    }
}
