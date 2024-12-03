package com.insurtech.kanguro.ui.scenes.rentersEditAdditionalCoverage

import android.os.Parcelable
import com.insurtech.kanguro.domain.model.AdditionalCoverageType
import com.insurtech.kanguro.domain.model.DwellingType
import kotlinx.parcelize.Parcelize

@Parcelize
data class PolicyInfoSharedFlow(
    val policyId: String,
    val dwellingType: DwellingType,
    val deductibleId: Int,
    val liabilityId: Int,
    val personalProperty: Double,
    val state: String,
    val zipCode: String,
    val previouslySelectedAdditionalCoverages: List<AdditionalCoverageType> = emptyList(),
    val isInsuranceRequired: Boolean
) : Parcelable
