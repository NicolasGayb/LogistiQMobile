package com.logistiq.app.network.model

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("/api/login")
    suspend fun login(
        @Body request: LoginRequest
    ): Response<LoginResponse>

    @POST("/api/signup")
    suspend fun signup(
        @Body request: SignupRequest
    ): Response<SignupResponse>
}