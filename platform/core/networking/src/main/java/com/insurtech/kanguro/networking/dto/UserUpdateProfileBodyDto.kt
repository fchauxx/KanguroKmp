package com.insurtech.kanguro.networking.dto

data class UserUpdateProfileBodyDto(
    val givenName: String,
    val surname: String,
    val phone: String
)
