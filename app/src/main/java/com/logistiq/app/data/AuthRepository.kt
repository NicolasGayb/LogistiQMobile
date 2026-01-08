package com.logistiq.app.data

import com.logistiq.app.network.model.AuthApi
import com.logistiq.app.network.model.Company
import com.logistiq.app.network.model.LoginRequest
import com.logistiq.app.network.model.LoginResponse
import com.logistiq.app.network.model.SignupRequest
import com.logistiq.app.network.model.SignupResponse
import com.logistiq.app.network.model.User
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(
    private val api: AuthApi,
    private val tokenStorage: TokenStorage
) {

    suspend fun login(email: String, password: String): Response<LoginResponse> {
        return api.login(email = email, password = password)
    }

    suspend fun saveToken(token: String) {
        tokenStorage.saveToken(token)
    }

    suspend fun logout(){
        tokenStorage.clear()
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