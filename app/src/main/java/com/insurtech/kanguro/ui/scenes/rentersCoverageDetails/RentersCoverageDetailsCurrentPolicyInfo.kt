package com.insurtech.kanguro.ui.scenes.rentersCoverageDetails

import com.insurtech.kanguro.domain.model.AdditionalCoverageType
import com.insurtech.kanguro.domain.model.DwellingType

data class RentersCoverageDetailsCurrentPolicyInfo(
    var residenceState: String = "",
    var residenceZipCode: String = "",
    var liability: Int? = null,
    var deductible: Int? = null,
    var personalProperty: Double? = null,
    var dwellingType: DwellingType? = null,
    var additionalCoveragesTypes: List<AdditionalCoverageType>? = null,
    var isInsuranceRequired: Boolean = false
)
