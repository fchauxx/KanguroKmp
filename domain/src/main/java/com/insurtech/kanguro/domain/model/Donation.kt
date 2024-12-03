package com.insurtech.kanguro.domain.model

import com.insurtech.kanguro.common.enums.CharityCause

data class Donation(
    val userId: String?,
    val charityId: Int?,
    val title: String?,
    val cause: CharityCause?
)
