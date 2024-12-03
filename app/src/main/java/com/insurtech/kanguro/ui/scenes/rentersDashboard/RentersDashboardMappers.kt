package com.insurtech.kanguro.ui.scenes.rentersDashboard

import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.domain.CoverageStatusUi
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.domain.RentersCoverageSummaryCardModel
import com.insurtech.kanguro.domain.model.PolicyStatus
import com.insurtech.kanguro.domain.model.RentersPolicy
import com.insurtech.kanguro.designsystem.ui.composables.rentersCoverageDetails.model.DwellingType as DwellingTypeUi
import com.insurtech.kanguro.domain.model.DwellingType as DwellingTypeModel

fun RentersPolicy.toUi(): RentersCoverageSummaryCardModel {
    return RentersCoverageSummaryCardModel(
        id = id,
        address = concatAddress(address.city, address.state),
        type = dwellingType.toUi(),
        status = status.toUi()
    )
}

fun PolicyStatus.toUi(): CoverageStatusUi {
    return when (this) {
        PolicyStatus.PENDING -> CoverageStatusUi.Pending
        PolicyStatus.ACTIVE -> CoverageStatusUi.Active
        PolicyStatus.CANCELED -> CoverageStatusUi.Cancelled
        PolicyStatus.TERMINATED -> CoverageStatusUi.Terminated
    }
}

fun DwellingTypeModel.toUi(): DwellingTypeUi {
    return when (this) {
        DwellingTypeModel.SingleFamily -> DwellingTypeUi.SingleFamily
        DwellingTypeModel.MultiFamily -> DwellingTypeUi.MultiFamily
        DwellingTypeModel.Townhouse -> DwellingTypeUi.Townhouse
        DwellingTypeModel.Apartment -> DwellingTypeUi.Apartment
        DwellingTypeModel.StudentHousing -> DwellingTypeUi.StudentHousing
    }
}

fun List<RentersPolicy>.toUi(): List<RentersCoverageSummaryCardModel> {
    return this.map {
        it.toUi()
    }
}

fun concatAddress(city: String?, state: String?): String {
    if (!city.isNullOrBlank() && !state.isNullOrBlank()) {
        return "$city, $state"
    }

    if (!city.isNullOrBlank()) {
        return city
    } else if (!state.isNullOrBlank()) {
        return state
    }

    return ""
}
