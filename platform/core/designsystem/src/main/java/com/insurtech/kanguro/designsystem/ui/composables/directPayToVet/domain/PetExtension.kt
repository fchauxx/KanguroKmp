package com.insurtech.kanguro.designsystem.ui.composables.directPayToVet.domain

import com.insurtech.kanguro.domain.model.Pet
import com.insurtech.kanguro.common.enums.PetType as EnumPetType

fun Pet.toPetInformation(): PetInformation? {
    return PetInformation(
        id = this.id ?: return null,
        name = this.name ?: return null,
        type = when (this.type) {
            EnumPetType.Dog -> PetType.dog
            EnumPetType.Cat -> PetType.cat
            else -> return null
        }
    )
}

fun List<Pet>.toPetsInformation() = this.mapNotNull { it.toPetInformation() }
