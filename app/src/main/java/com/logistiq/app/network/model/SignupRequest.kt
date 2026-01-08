package com.logistiq.app.network.model

data class SignupRequest(
    val company: Company,
    val user: User
)

data class Company(
    val name: String,
    val document: String
)

data class User(
    val name: String,
    val email: String,
    val password: String
)
