package com.insurtech.kanguro.domain.login

import android.os.Parcelable
import com.auth0.android.jwt.JWT
import com.insurtech.kanguro.common.enums.AppLanguage
import com.insurtech.kanguro.core.api.bodies.DonationBody
import kotlinx.parcelize.Parcelize

@Deprecated("Use Login or LoginDto instead")
@Parcelize
data class LoginResponse(
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
    val donation: DonationBody?
) : Parcelable
