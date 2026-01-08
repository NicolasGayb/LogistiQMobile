package com.logistiq.app.network.model

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded

interface AuthApi {

    @FormUrlEncoded
    @POST("/api/login")
    suspend fun login(
        @Field("username") email: String,
        @Field("password") password: String
    ): Response<LoginResponse>

    @POST("/api/signup")
    suspend fun signup(
        @Body request: SignupRequest
    ): Response<SignupResponse>
}