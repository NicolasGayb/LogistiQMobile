package com.logistiq.app.network.model

data class SignupRequest(
    val name: String,
    val email: String,
    val password: String,
    val companyName: String,
    val companyCNPJ: String
)