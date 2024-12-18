package com.insurtech.kanguro.shared.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class User(
    @SerialName("id")
    val id: String,
    @SerialName("givenName")
    val givenName: String,
    @SerialName("surname")
    val surname: String,
    @SerialName("language")
    val language: AppLanguage,
    @SerialName("referralCode")
    val referralCode: String? = null,
    @SerialName("phone")
    val phone: String? = null,
    @SerialName("address")
    val address: String? = null,
    @SerialName("complement")
    val complement: String? = null,
    @SerialName("zipCode")
    val zipCode: String? = null,
    @SerialName("email")
    val email: String? = null,
    @SerialName("birthDate")
    val birthDate: String,
    @SerialName("isNeededDeleteData")
    val isNeededDeleteData: Boolean,
    @SerialName("hasAccessBlocked")
    val hasAccessBlocked: Boolean,
    @SerialName("insuranceId")
    val insuranceId: Int? = null
)
