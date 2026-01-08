package com.logistiq.app.data.auth

import com.logistiq.app.network.model.AuthApi
import com.logistiq.app.network.model.SignupRequest
import com.logistiq.app.network.model.SignupResponse
import com.logistiq.app.network.model.User
import com.logistiq.app.network.model.Company
import javax.inject.Inject
import javax.inject.Singleton
import com.logistiq.app.network.model.LoginRequest
import com.logistiq.app.network.model.LoginResponse
import retrofit2.Response

@Singleton
class AuthRepository @Inject constructor(
    private val api: AuthApi
) {

    suspend fun login(email: String, password: String): Response<LoginResponse> {
        return api.login(email = email, password = password)
    }

    suspend fun signup(
        name: String,
        email: String,
        password: String,
        companyName: String,
        companyCNPJ: String,
    ): SignupResponse {
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

        val response = api.signup(request)

        if (response.isSuccessful) {
            return response.body() ?: throw Exception("Resposta vazia do servidor")
        } else {
            throw Exception("Erro no cadastro: ${response.code()} ${response.message()}")
        }
    }
}
