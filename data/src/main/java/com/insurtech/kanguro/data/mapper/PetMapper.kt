package com.insurtech.kanguro.data.mapper

import com.insurtech.kanguro.domain.model.Pet
import com.insurtech.kanguro.networking.dto.PetDto

object PetMapper {

    fun mapPetDtoToPet(petDto: PetDto): Pet =
        Pet(
            id = petDto.id,
            name = petDto.name,
            birthDate = petDto.birthDate,
            isBirthDateApproximated = petDto.isBirthDateApproximated,
            gender = petDto.gender,
            type = petDto.type,
            size = petDto.size,
            isNeutered = petDto.isNeutered,
            preExistingConditions = petDto.preExistingConditions,
            petBreedId = petDto.petBreedId,
            breed = petDto.breed,
            additionalInfoSessionId = petDto.additionalInfoSessionId,
            hasAdditionalInfo = petDto.hasAdditionalInfo,
            petPictureUrl = petDto.petPictureUrl,
            pictureId = petDto.pictureId
        )

    fun mapPetsDtoToPets(petsDto: List<PetDto>): List<Pet> = petsDto.map { mapPetDtoToPet(it) }
}
