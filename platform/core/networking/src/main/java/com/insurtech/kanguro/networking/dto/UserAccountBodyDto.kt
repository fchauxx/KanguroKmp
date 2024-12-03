package com.insurtech.kanguro.networking.dto

import com.insurtech.kanguro.common.enums.AccountType

data class UserAccountBodyDto(
    val accountNumber: String?,
    val routingNumber: String?,
    val bankName: String,
    val accountType: AccountType
)
