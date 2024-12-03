package com.insurtech.kanguro.data.mapper

import com.insurtech.kanguro.domain.model.PreventiveCoverageInfo
import com.insurtech.kanguro.networking.dto.PreventiveCoverageInfoDto
import com.insurtech.kanguro.testing.extensions.mapJsonToListOfModels
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.fail

class PreventiveCoverageInfoMapperTest {

    @Test
    fun `Map PreventiveCoverageInfo Dtos to PreventiveCoverageInfos successfully`() {
        // ARRANGE
        val preventiveCoverageInfoDtos = "200_get_get_policy_coverages_successfully.json"
            .mapJsonToListOfModels<PreventiveCoverageInfoDto>()

        if (preventiveCoverageInfoDtos != null) {
            // ACT
            val preventiveCoverageInfos = PreventiveCoverageInfoMapper
                .mapPreventiveCoverageInfoDtosToPreventiveCoverageInfos(preventiveCoverageInfoDtos)

            // ASSERT
            assertEquals(
                expected = listOf(
                    PreventiveCoverageInfo(
                        name = preventiveCoverageInfoDtos[0].name,
                        value = preventiveCoverageInfoDtos[0].value,
                        usedValue = preventiveCoverageInfoDtos[0].usedValue,
                        remainingValue = preventiveCoverageInfoDtos[0].remainingValue,
                        annualLimit = preventiveCoverageInfoDtos[0].annualLimit,
                        remainingLimit = preventiveCoverageInfoDtos[0].remainingLimit,
                        uses = preventiveCoverageInfoDtos[0].uses,
                        remainingUses = preventiveCoverageInfoDtos[0].remainingUses,
                        varName = preventiveCoverageInfoDtos[0].varName,
                        usesLimit = preventiveCoverageInfoDtos[0].usesLimit
                    ),
                    PreventiveCoverageInfo(
                        name = preventiveCoverageInfoDtos[1].name,
                        value = preventiveCoverageInfoDtos[1].value,
                        usedValue = preventiveCoverageInfoDtos[1].usedValue,
                        remainingValue = preventiveCoverageInfoDtos[1].remainingValue,
                        annualLimit = preventiveCoverageInfoDtos[1].annualLimit,
                        remainingLimit = preventiveCoverageInfoDtos[1].remainingLimit,
                        uses = preventiveCoverageInfoDtos[1].uses,
                        remainingUses = preventiveCoverageInfoDtos[1].remainingUses,
                        varName = preventiveCoverageInfoDtos[1].varName,
                        usesLimit = preventiveCoverageInfoDtos[1].usesLimit
                    )
                ),
                actual = preventiveCoverageInfos
            )
        } else {
            fail("preventiveCoverageInfoDtos must be not null.")
        }
    }
}
