package com.insurtech.kanguro.data.mapper

import com.insurtech.kanguro.domain.model.BackendVersion
import com.insurtech.kanguro.networking.dto.BackendVersionDto
import com.insurtech.kanguro.testing.extensions.mapJsonToModel
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.fail

class BackendVersionMapperTest {

    @Test fun `Map backend version DTO to backend version`() {
        // ARRANGE

        val backendVersionDto = "200_get_get_backend_version_successfully.json"
            .mapJsonToModel<BackendVersionDto>()

        // ACT

        if (backendVersionDto != null) {
            val backendVersion = BackendVersionMapper
                .mapBackendVersionDtoToBackendVersion(backendVersionDto)

            // ASSERT

            assertEquals(
                expected = BackendVersion(
                    version = backendVersionDto.version
                ),
                actual = backendVersion
            )
        } else {
            fail("BackendVersionDto must be not null.")
        }
    }
}
