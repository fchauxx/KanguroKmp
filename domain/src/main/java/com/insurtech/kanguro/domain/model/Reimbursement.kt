package com.insurtech.kanguro.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Reimbursement(
    val id: Int? = null,
    val amount: Double? = null
) : Parcelable
