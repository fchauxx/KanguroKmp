package com.insurtech.kanguro.domain.model

import android.os.Parcelable
import com.insurtech.kanguro.common.enums.AccountType
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserAccount(
    val accountNumber: String?,
    val routingNumber: String?,
    val bankName: String?,
    val accountType: AccountType?
) : Parcelable
