package com.logistiq.app.network.model

data class UserResponse(
    val id: Int,
    val name: String,
    val email: String,
    val role: String,
    val company_id: Int,
    val is_active: Boolean
)
