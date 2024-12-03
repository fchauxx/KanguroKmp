package com.insurtech.kanguro.ui.scenes.home

import com.insurtech.kanguro.common.enums.PetType
import com.insurtech.kanguro.common.enums.PolicyStatus
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.domain.CoverageStatusUi
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.domain.PetsCoverageSummaryCardModel
import com.insurtech.kanguro.domain.model.PetPolicy
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.domain.PetType as PetTypeUi

fun PetPolicy.toPetsCoverageSummaryCardModel(): PetsCoverageSummaryCardModel {
    return PetsCoverageSummaryCardModel(
        id = id ?: "",
        breed = pet?.breed ?: "",
        birthDate = pet?.birthDate,
        status = when (status) {
            PolicyStatus.ACTIVE -> CoverageStatusUi.Active
            PolicyStatus.PENDING -> CoverageStatusUi.Pending
            PolicyStatus.CANCELED -> CoverageStatusUi.Cancelled
            PolicyStatus.TERMINATED -> CoverageStatusUi.Terminated
            null -> CoverageStatusUi.Terminated
        },
        pictureUrl = pet?.petPictureUrl ?: "",
        name = pet?.name ?: "",
        petType = when (pet?.type) {
            PetType.Dog -> PetTypeUi.Dog
            PetType.Cat -> PetTypeUi.Cat
            null -> PetTypeUi.Dog
        }
    )
}
