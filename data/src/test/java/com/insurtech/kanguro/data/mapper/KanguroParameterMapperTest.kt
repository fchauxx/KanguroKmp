package com.insurtech.kanguro.data.mapper

import com.insurtech.kanguro.common.enums.KanguroParameterType
import com.insurtech.kanguro.domain.model.KanguroParameter
import com.insurtech.kanguro.networking.dto.KanguroParameterDto
import com.insurtech.kanguro.testing.extensions.mapJsonToListOfModels
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.fail

class KanguroParameterMapperTest {

    @Test
    fun `Map kanguro parameter DTOs to kanguro parameters successfully`() {
        // ARRANGE
        val kanguroParameterDtos = "200_get_get_vet_advices_successfully.json"
            .mapJsonToListOfModels<KanguroParameterDto>()

        if (kanguroParameterDtos != null) {
            // ACT
            val kanguroParameters = KanguroParameterMapper
                .mapKanguroParameterDtosToKanguroParameters(kanguroParameterDtos)

            // ASSERT
            assertEquals(
                expected = listOf(
                    KanguroParameter(
                        key = 1,
                        value = "1. A dog questions",
                        description = "Answer for dogs",
                        type = KanguroParameterType.VA_D,
                        language = "en",
                        isActive = true
                    ),
                    KanguroParameter(
                        key = 1,
                        value = "2. A cat question",
                        description = "Answer for cats",
                        type = KanguroParameterType.VA_C,
                        language = "en",
                        isActive = true
                    )
                ),
                actual = kanguroParameters
            )
        } else {
            fail("KanguroParameterDtos must be not null.")
        }
    }
}
