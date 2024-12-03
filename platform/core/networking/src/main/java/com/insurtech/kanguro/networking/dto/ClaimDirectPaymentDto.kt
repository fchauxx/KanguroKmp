package com.insurtech.kanguro.networking.dto

import java.util.Date

data class ClaimDirectPaymentDto(
    val petId: Int? = null,
    val type: ClaimTypeDto? = null,
    val invoiceDate: Date? = null,
    val description: String? = null,
    val amount: Double? = null,
    val veterinarianId: Int? = null,
    val pledgeOfHonor: String? = null,
    val pledgeOfHonorExtension: String? = null,
    val veterinarianName: String? = null,
    val veterinarianEmail: String? = null,
    val veterinarianClinic: String? = null
)
