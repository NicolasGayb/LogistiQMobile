package com.logistiq.app.network.model

data class LoginResponse(
    val token: String,
    val userId: Int
)