package com.insurtech.kanguro.networking.dto

import com.insurtech.kanguro.common.enums.KanguroParameterType

data class KanguroParameterDto(
    val key: Int?,
    val value: String?,
    val description: String?,
    val type: KanguroParameterType?,
    val language: String?,
    val isActive: Boolean?
)
