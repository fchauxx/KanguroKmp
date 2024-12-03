package com.insurtech.kanguro.networking.dto

import com.insurtech.kanguro.common.enums.CommunicationSender
import com.insurtech.kanguro.common.enums.CommunicationType
import java.util.Date

data class CommunicationDto(
    val id: Int,
    val type: CommunicationType,
    val sender: CommunicationSender,
    val message: String?,
    val fileURL: String?,
    val createdAt: Date
)
