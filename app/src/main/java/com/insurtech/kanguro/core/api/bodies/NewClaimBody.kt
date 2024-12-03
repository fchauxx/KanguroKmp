package com.insurtech.kanguro.core.api.bodies

import android.os.Parcelable
import com.insurtech.kanguro.domain.model.ClaimType
import com.insurtech.kanguro.domain.model.ReimbursementProcess
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
data class NewClaimBody(
    var description: String? = null,
    var invoiceDate: Date? = null,
    var amount: Double? = null,
    var petId: Long? = null,
    var type: ClaimType? = null,
    var pledgeOfHonorId: Int? = null,
    var reimbursementProcess: ReimbursementProcess? = null,
    var documentIds: List<Int> = listOf()
) : Parcelable
