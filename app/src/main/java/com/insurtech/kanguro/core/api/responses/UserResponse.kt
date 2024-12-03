package com.insurtech.kanguro.core.api.responses

import android.os.Parcelable
import com.insurtech.kanguro.common.enums.AppLanguage
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class UserResponse(
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
    val hasAccessBlocked: Boolean? = null
) : Parcelable
