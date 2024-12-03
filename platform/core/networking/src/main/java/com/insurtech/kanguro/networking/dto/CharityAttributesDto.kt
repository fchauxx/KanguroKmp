package com.insurtech.kanguro.networking.dto

import com.insurtech.kanguro.common.enums.CharityCause
import com.squareup.moshi.Json

data class CharityAttributesDto(
    val title: String?,
    @Json(name = "abreviatedTitle")
    val abbreviatedTitle: String?,
    val description: String?,
    val locale: String?,
    val canBeChosenByUser: Boolean?,
    val charityKey: Int?,
    val cause: CharityCause?
)
