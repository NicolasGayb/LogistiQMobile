package com.logistiq.app.ui.signup

import com.logistiq.app.network.model.SignupResponse

sealed class SignupState {
    object Idle : SignupState()
    object Loading : SignupState()
    data class Success(val data: SignupResponse) : SignupState()
    data class Error(val message: String) : SignupState()
}
