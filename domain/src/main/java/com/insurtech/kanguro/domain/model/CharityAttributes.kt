package com.insurtech.kanguro.domain.model

import com.insurtech.kanguro.common.enums.CharityCause

data class CharityAttributes(
    val title: String?,
    val abbreviatedTitle: String?,
    val description: String?,
    val locale: String?,
    val canBeChosenByUser: Boolean?,
    val charityKey: Int?,
    val cause: CharityCause?
)
