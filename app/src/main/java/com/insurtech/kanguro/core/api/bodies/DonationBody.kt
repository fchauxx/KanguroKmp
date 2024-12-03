package com.insurtech.kanguro.core.api.bodies

import android.os.Parcelable
import com.insurtech.kanguro.common.enums.CharityCause
import kotlinx.parcelize.Parcelize

@Parcelize
data class DonationBody(
    val userId: String?,
    val charityId: Int?,
    val title: String?,
    val cause: CharityCause?
) : Parcelable
