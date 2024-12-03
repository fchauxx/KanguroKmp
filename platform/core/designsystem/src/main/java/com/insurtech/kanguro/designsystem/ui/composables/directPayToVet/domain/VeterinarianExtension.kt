package com.insurtech.kanguro.designsystem.ui.composables.directPayToVet.domain

import com.insurtech.kanguro.domain.model.Veterinarian

fun List<Veterinarian>.toVetsInformation() = this.map {
    VetInformationUi(
        id = it.id,
        name = it.veterinarianName,
        clinicName = it.clinicName,
        email = it.email
    )
}
