package com.insurtech.kanguro.networking.dto

data class UserUpdatePasswordBodyDto(
    val email: String,
    val currentPassword: String,
    val newPassword: String
)
