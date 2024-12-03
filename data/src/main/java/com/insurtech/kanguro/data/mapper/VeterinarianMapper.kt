package com.insurtech.kanguro.data.mapper

import com.insurtech.kanguro.domain.model.Veterinarian
import com.insurtech.kanguro.networking.dto.VeterinarianDto

object VeterinarianMapper {

    private fun mapVeterinarianDtoToVeterinarian(veterinarianDto: VeterinarianDto): Veterinarian? {
        return Veterinarian(
            id = veterinarianDto.id ?: return null,
            clinicName = veterinarianDto.clinicName ?: return null,
            veterinarianName = veterinarianDto.veterinarianName ?: return null,
            email = veterinarianDto.email ?: return null
        )
    }

    fun mapVeterinariansDtoToVeterinarians(veterinariansDto: List<VeterinarianDto>) =
        veterinariansDto.mapNotNull { mapVeterinarianDtoToVeterinarian(it) }
}
