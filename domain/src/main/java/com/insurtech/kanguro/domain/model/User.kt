package com.insurtech.kanguro.domain.model

import com.insurtech.kanguro.common.enums.AppLanguage
import java.util.Date

data class User(
    val id: String,
    val givenName: String,
    val surname: String,
    val language: AppLanguage,
    val referralCode: String? = null,
    val phone: String? = null,
    val address: String? = null,
    val complement: String? = null,
    val zipCode: String? = null,
    val email: String? = null,
    val birthDate: Date,
    val isNeededDeleteData: Boolean,
    val hasAccessBlocked: Boolean,
    val insuranceId: Int? = null
)
