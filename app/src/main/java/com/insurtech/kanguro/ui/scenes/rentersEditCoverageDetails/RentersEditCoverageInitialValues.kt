package com.insurtech.kanguro.ui.scenes.rentersEditCoverageDetails

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RentersEditCoverageInitialValues(
    val currentLiabilityId: Int?,
    val currentDeductibleId: Int?,
    val currentPersonalPropertyValue: Double?
) : Parcelable
