package com.logistiq.app.data.auth

import com.logistiq.app.network.model.AuthApi
import com.logistiq.app.network.model.LoginRequest
import retrofit2.Response

class AuthRepository(
    private val api: AuthApi
) {

    suspend fun login(
        email: String,
        password: String
    ): Response<*> {
        val request = LoginRequest(
            email = email,
            password = password
        )

        return api.login(request)
    }
}