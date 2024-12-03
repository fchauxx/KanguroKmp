package com.insurtech.kanguro.data.remote

import com.insurtech.kanguro.data.mapper.PetMapper
import com.insurtech.kanguro.data.source.PetDataSource
import com.insurtech.kanguro.networking.api.RefactoredKanguroPetApiService
import com.insurtech.kanguro.networking.dto.PetDto
import com.insurtech.kanguro.networking.error.RemoteServiceIntegrationError
import com.insurtech.kanguro.testing.BaseUnitTest
import com.insurtech.kanguro.testing.extensions.mapJsonToModel
import com.insurtech.kanguro.testing.files.FileSystemSupport
import com.insurtech.kanguro.testing.rest.RestInfrastructureRule
import com.insurtech.kanguro.testing.rest.wireRestApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.fail

class PetRemoteDataSourceTest : BaseUnitTest() {

    @get:Rule
    val restInfrastructureRule = RestInfrastructureRule()

    private lateinit var petDataSource: PetDataSource

    @Before
    fun setUp() {
        val refactoredKanguroPolicyApiService = restInfrastructureRule.server
            .wireRestApi<RefactoredKanguroPetApiService>()

        petDataSource = PetRemoteDataSource(refactoredKanguroPolicyApiService)
    }

    @Test
    fun `Get pet details, when it is requested to get pet and there is no issue, then return pet`() =
        runTest {
            // ARRANGE
            val assetPetFileName = "200_get_get_pet_detail_successfully.json"

            restInfrastructureRule.restScenario(
                statusCode = 200,
                response = FileSystemSupport.loadFile(assetPetFileName)
            )

            val expectedPetDto = assetPetFileName.mapJsonToModel<PetDto>()

            if (expectedPetDto != null) {
                // ACT
                val pet = petDataSource.getPetDetails(petId = 1)

                // ASSERT
                assertEquals(
                    expected = PetMapper.mapPetDtoToPet(expectedPetDto),
                    actual = pet
                )
            } else {
                fail("Expected pet dto must be not null.")
            }
        }

    @Test
    fun `Get pet details, when it is requested to get pet there is some issue incoming from client, then return client system error`() =
        runTest {
            // ARRANGE
            restInfrastructureRule.restScenario(400)

            // ACT
            val result = kotlin.runCatching {
                petDataSource.getPetDetails(petId = 1)
            }

            // ASSERT
            val unwrappedError = unwrapCaughtError(result)

            assertEquals(
                expected = RemoteServiceIntegrationError.ClientOrigin,
                actual = unwrappedError
            )
        }

    @Test
    fun `Get pet details, when it is requested to get pet there is some issue incoming from server, then return remote system error`() =
        runTest {
            // ARRANGE
            restInfrastructureRule.restScenario(500)

            // ACT
            val result = kotlin.runCatching {
                petDataSource.getPetDetails(petId = 1)
            }

            // ASSERT
            val unwrappedError = unwrapCaughtError(result)

            assertEquals(
                expected = RemoteServiceIntegrationError.RemoteSystem,
                actual = unwrappedError
            )
        }
}
