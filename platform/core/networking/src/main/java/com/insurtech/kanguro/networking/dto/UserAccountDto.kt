package com.insurtech.kanguro.networking.dto

import com.insurtech.kanguro.common.enums.AccountType

data class UserAccountDto(
    val accountNumber: String?,
    val routingNumber: String?,
    val bankName: String?,
    val accountType: AccountType?
)
