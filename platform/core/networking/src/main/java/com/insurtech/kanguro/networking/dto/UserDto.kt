package com.insurtech.kanguro.networking.dto

import com.insurtech.kanguro.common.enums.AppLanguage
import java.util.Date

data class UserDto(
    val id: String? = null,
    val givenName: String? = null,
    val surname: String? = null,
    val language: AppLanguage? = null,
    val referralCode: String? = null,
    val phone: String? = null,
    val address: String? = null,
    val complement: String? = null,
    val zipCode: String? = null,
    val email: String? = null,
    val birthDate: Date? = null,
    val isNeededDeleteData: Boolean? = null,
    val hasAccessBlocked: Boolean? = null,
    val insuranceId: Int? = null
)
