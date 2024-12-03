package com.insurtech.kanguro.domain.model

data class UserUpdateProfileBody(
    val givenName: String,
    val surname: String,
    val phone: String
)
