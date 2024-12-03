package com.insurtech.kanguro.data.mapper

import com.insurtech.kanguro.common.enums.PetSize
import com.insurtech.kanguro.common.enums.PetType
import com.insurtech.kanguro.domain.model.Pet
import com.insurtech.kanguro.networking.dto.PetDto
import org.junit.Test
import kotlin.test.assertEquals

class PetMapperTest {

    @Test
    fun `Map pet dto to pet successfully`() {
        // ARRANGE
        val petDto = PetDto(
            id = 891,
            name = "Helga",
            type = PetType.Dog,
            size = PetSize.Small
        )

        val expectedPet = Pet(
            id = 891,
            name = "Helga",
            type = PetType.Dog,
            size = PetSize.Small
        )

        // ACT
        val pet = PetMapper.mapPetDtoToPet(petDto)

        // ASSERT
        assertEquals(
            expected = expectedPet,
            actual = pet
        )
    }
}
