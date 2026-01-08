package com.logistiq.app.network.model

data class SignupResponse(
    val id: String,
    val name: String,
    val email: String,
    val access_token: String?,
    val token_type: String?
)

