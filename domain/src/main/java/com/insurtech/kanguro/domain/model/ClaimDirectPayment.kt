package com.insurtech.kanguro.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
data class ClaimDirectPayment(
    val petId: Int? = null,
    val type: ClaimType? = null,
    val invoiceDate: Date? = null,
    val description: String? = null,
    val amount: Double? = null,
    val veterinarianId: Int? = null,
    val pledgeOfHonor: String? = null,
    val pledgeOfHonorExtension: String? = null,
    val veterinarianName: String? = null,
    val veterinarianEmail: String? = null,
    val veterinarianClinic: String? = null
) : Parcelable
