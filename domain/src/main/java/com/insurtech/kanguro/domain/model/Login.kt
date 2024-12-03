package com.insurtech.kanguro.domain.model

import com.auth0.android.jwt.JWT
import com.insurtech.kanguro.common.enums.AppLanguage
import java.util.Date

data class Login(
    val id: String?,
    val givenName: String?,
    val surname: String?,
    val accessToken: JWT?,
    val language: AppLanguage?,
    val referralCode: String?,
    val email: String?,
    val phone: String?,
    val refreshToken: String?,
    val idToken: JWT?,
    val isNeededDeleteData: Boolean?,
    val isPasswordUpdateNeeded: Boolean?,
    val donation: Donation?,
    val expiresOn: Date?
)
