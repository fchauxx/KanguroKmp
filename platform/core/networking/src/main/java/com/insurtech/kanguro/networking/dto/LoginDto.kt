package com.insurtech.kanguro.networking.dto

import com.auth0.android.jwt.JWT
import java.util.Date

data class LoginDto(
    val id: String?,
    val givenName: String?,
    val surname: String?,
    val accessToken: JWT?,
    val language: AppLanguageDto?,
    val referralCode: String?,
    val email: String?,
    val phone: String?,
    val refreshToken: String?,
    val idToken: JWT?,
    val isNeededDeleteData: Boolean?,
    val isPasswordUpdateNeeded: Boolean?,
    val donation: DonationDto?,
    val expiresOn: Date?
)
