package com.insurtech.kanguro.domain.model

data class UserUpdatePasswordBody(
    val email: String,
    val currentPassword: String,
    val newPassword: String
)
