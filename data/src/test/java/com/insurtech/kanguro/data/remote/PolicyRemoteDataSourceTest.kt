package com.insurtech.kanguro.data.remote

import com.insurtech.kanguro.data.mapper.PetMapper
import com.insurtech.kanguro.data.mapper.PolicyMapper
import com.insurtech.kanguro.data.mapper.PreventiveCoverageInfoMapper
import com.insurtech.kanguro.data.source.PolicyDataSource
import com.insurtech.kanguro.domain.model.PetPolicy
import com.insurtech.kanguro.networking.api.RefactoredKanguroPetApiService
import com.insurtech.kanguro.networking.api.RefactoredKanguroPolicyApiService
import com.insurtech.kanguro.networking.dto.PetDto
import com.insurtech.kanguro.networking.dto.PetPolicyViewModelDto
import com.insurtech.kanguro.networking.dto.PreventiveCoverageInfoDto
import com.insurtech.kanguro.networking.error.RemoteServiceIntegrationError
import com.insurtech.kanguro.testing.BaseUnitTest
import com.insurtech.kanguro.testing.extensions.mapJsonToListOfModels
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

class PolicyRemoteDataSourceTest : BaseUnitTest() {

    @get:Rule
    val restInfrastructureRule = RestInfrastructureRule()

    @get:Rule
    val petRestInfrastructureRule = RestInfrastructureRule()

    private lateinit var policyDataSource: PolicyDataSource

    @Before
    fun setUp() {
        val refactoredKanguroPolicyApiService = restInfrastructureRule.server
            .wireRestApi<RefactoredKanguroPolicyApiService>()

        val refactoredKanguroPetApiService = petRestInfrastructureRule.server
            .wireRestApi<RefactoredKanguroPetApiService>()

        policyDataSource = PolicyRemoteDataSource(
            refactoredKanguroPolicyApiService,
            refactoredKanguroPetApiService
        )
    }

    @Test
    fun `Get preventive coverages, when it is requested to get preventive coverages and there is some issue incoming from client, then return client system error`() =
        runTest {
            // ARRANGE
            restInfrastructureRule.restScenario(400)

            // ACT
            val result = kotlin.runCatching {
                policyDataSource.getPolicyCoverage(
                    policyId = "6f5300d0-d456-45ec-daa6-08daf4df4b01",
                    reimbursement = 0.8,
                    offerId = null
                )
            }

            // ASSERT
            val unwrappedError = unwrapCaughtError(result)

            assertEquals(
                expected = RemoteServiceIntegrationError.ClientOrigin,
                actual = unwrappedError
            )
        }

    @Test
    fun `get preventive coverages, when it is requested to get preventive coverages and there is some issue incoming from server, then return remote system error`() =
        runTest {
            // ARRANGE
            restInfrastructureRule.restScenario(500)

            // ACT
            val result = kotlin.runCatching {
                policyDataSource.getPolicyCoverage(
                    policyId = "6f5300d0-d456-45ec-daa6-08daf4df4b01",
                    reimbursement = 0.8,
                    offerId = null
                )
            }

            // ASSERT
            val unwrappedError = unwrapCaughtError(result)

            assertEquals(
                expected = RemoteServiceIntegrationError.RemoteSystem,
                actual = unwrappedError
            )
        }

    @Test
    fun `get preventive coverages, when it is requested to get preventive coverages and there is no issue, then return preventive coverages infos`() =
        runTest {
            // ARRANGE
            val assetFileName = "200_get_get_policy_coverages_successfully.json"

            restInfrastructureRule.restScenario(
                statusCode = 200,
                response = FileSystemSupport.loadFile(assetFileName)
            )

            val expectedResponse = assetFileName.mapJsonToListOfModels<PreventiveCoverageInfoDto>()

            if (expectedResponse != null) {
                // ACT
                val response = policyDataSource.getPolicyCoverage(
                    policyId = "6f5300d0-d456-45ec-daa6-08daf4df4b01",
                    reimbursement = 0.8,
                    offerId = null
                )

                // ASSERT
                assertEquals(
                    expected = PreventiveCoverageInfoMapper.mapPreventiveCoverageInfoDtosToPreventiveCoverageInfos(
                        expectedResponse
                    ),
                    actual = response
                )
            } else {
                fail("ExpectedResponse must be not null.")
            }
        }

    @Test
    fun `Get policy detail, when it is requested to get policy and there is no issue, then return the policy`() =
        runTest {
            // ARRANGE
            val assetPolicyFileName = "200_get_get_policy_detail_successfully.json"

            restInfrastructureRule.restScenario(
                statusCode = 200,
                response = FileSystemSupport.loadFile(assetPolicyFileName)
            )

            val assetPetFileName = "200_get_get_pet_detail_successfully.json"

            petRestInfrastructureRule.restScenario(
                statusCode = 200,
                response = FileSystemSupport.loadFile(assetPetFileName)
            )

            val expectedPolicyDto = assetPolicyFileName.mapJsonToModel<PetPolicyViewModelDto>()

            if (expectedPolicyDto != null) {
                // ACT
                val response = policyDataSource.getPolicyDetail(
                    policyId = "6f5300d0-d456-45ec-daa6-08daf4df4b01"
                )

                // ASSERT
                assertEquals(
                    expected = getExpectedPolicy(expectedPolicyDto),
                    actual = response
                )
            } else {
                fail("ExpectedResponse must be not null.")
            }
        }

    @Test
    fun `Get policy detail, when it is requested to get policy and there is some issue incoming from client, then return client system error`() =
        runTest {
            // ARRANGE
            restInfrastructureRule.restScenario(400)

            // ACT
            val result = kotlin.runCatching {
                policyDataSource.getPolicyDetail(
                    policyId = "6f5300d0-d456-45ec-daa6-08daf4df4b01"
                )
            }

            // ASSERT
            val unwrappedError = unwrapCaughtError(result)

            assertEquals(
                expected = RemoteServiceIntegrationError.ClientOrigin,
                actual = unwrappedError
            )
        }

    @Test
    fun `Get policy detail, when it is requested to get policy and there is some issue incoming from server, then return remote system error`() =
        runTest {
            // ARRANGE
            restInfrastructureRule.restScenario(500)

            // ACT
            val result = kotlin.runCatching {
                policyDataSource.getPolicyDetail(
                    policyId = "6f5300d0-d456-45ec-daa6-08daf4df4b01"
                )
            }

            // ASSERT
            val unwrappedError = unwrapCaughtError(result)

            assertEquals(
                expected = RemoteServiceIntegrationError.RemoteSystem,
                actual = unwrappedError
            )
        }

    @Test
    fun `Get policy detail, when it is requested to get policy and there is some issue incoming from pet sercvice, then return remote system error`() =
        runTest {
            // ARRANGE
            val assetPolicyFileName = "200_get_get_policy_detail_successfully.json"

            restInfrastructureRule.restScenario(
                statusCode = 200,
                response = FileSystemSupport.loadFile(assetPolicyFileName)
            )

            petRestInfrastructureRule.restScenario(500)

            // ACT
            val result = kotlin.runCatching {
                policyDataSource.getPolicyDetail(
                    policyId = "6f5300d0-d456-45ec-daa6-08daf4df4b01"
                )
            }

            // ASSERT
            val unwrappedError = unwrapCaughtError(result)

            assertEquals(
                expected = RemoteServiceIntegrationError.RemoteSystem,
                actual = unwrappedError
            )
        }

    private fun getExpectedPolicy(policyDto: PetPolicyViewModelDto): PetPolicy {
        val expectedPet = PetMapper.mapPetDtoToPet(
            "200_get_get_pet_detail_successfully.json"
                .mapJsonToModel<PetDto>()
                ?: fail("Pet must be not null.")
        )

        return PolicyMapper.mapPolicyDtoToPolicy(policyDto, expectedPet)
    }
}
