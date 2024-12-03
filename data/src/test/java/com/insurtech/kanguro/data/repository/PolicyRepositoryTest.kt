package com.insurtech.kanguro.data.repository

import app.cash.turbine.test
import com.insurtech.kanguro.data.Result
import com.insurtech.kanguro.data.mapper.PetMapper
import com.insurtech.kanguro.data.mapper.PolicyMapper
import com.insurtech.kanguro.data.mapper.PreventiveCoverageInfoMapper
import com.insurtech.kanguro.data.remote.fakes.FakePolicyRemoteDataSource
import com.insurtech.kanguro.data.repository.impl.RefactoredPolicyRepository
import com.insurtech.kanguro.networking.dto.PetDto
import com.insurtech.kanguro.networking.dto.PetPolicyViewModelDto
import com.insurtech.kanguro.networking.dto.PreventiveCoverageInfoDto
import com.insurtech.kanguro.testing.extensions.mapJsonToListOfModels
import com.insurtech.kanguro.testing.extensions.mapJsonToModel
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.fail

class PolicyRepositoryTest {

    private lateinit var fakePolicyRemoteDataSource: FakePolicyRemoteDataSource

    private lateinit var policyRepository: IRefactoredPolicyRepository

    @Before
    fun setUp() {
        fakePolicyRemoteDataSource = FakePolicyRemoteDataSource()
        policyRepository = RefactoredPolicyRepository(
            policyRemoteDataSource = fakePolicyRemoteDataSource
        )
    }

    @Test
    fun `Get policy coverages infos, successfully`() = runTest {
        // ARRANGE
        val preventiveCoverageInfoDtos = "200_get_get_policy_coverages_successfully.json"
            .mapJsonToListOfModels<PreventiveCoverageInfoDto>()

        if (preventiveCoverageInfoDtos != null) {
            val expectedPreventiveCoverageInfos =
                PreventiveCoverageInfoMapper.mapPreventiveCoverageInfoDtosToPreventiveCoverageInfos(
                    preventiveCoverageInfoDtos
                )

            fakePolicyRemoteDataSource.setPreventiveCoverageInfos(expectedPreventiveCoverageInfos)

            // ACT
            policyRepository.getPolicyCoverage(
                policyId = "6f5300d0-d456-45ec-daa6-08daf4df4b01",
                reimbursement = 0.8,
                offerId = null
            ).test {
                // ASSERT
                assertEquals(
                    expected = Result.Success(expectedPreventiveCoverageInfos),
                    actual = awaitItem()
                )
                awaitComplete()
            }
        } else {
            fail("preventiveCoverageInfoDtos must be not null.")
        }
    }

    @Test
    fun `Get policy coverages infos, when there was an unexpected error`() = runTest {
        // ARRANGE
        val exception = Exception("This is a custom exception", Throwable())
        fakePolicyRemoteDataSource.setException(exception)

        // ACT
        policyRepository.getPolicyCoverage(
            policyId = "6f5300d0-d456-45ec-daa6-08daf4df4b01",
            reimbursement = 0.8,
            offerId = null
        ).test {
            // ASSERT
            assertEquals(
                expected = Result.Error(exception),
                actual = awaitItem()
            )
            awaitComplete()
        }
    }

    @Test
    fun `Get policy detail, successfully`() = runTest {
        // ARRANGE
        val policyDto = "200_get_get_policy_detail_successfully.json"
            .mapJsonToModel<PetPolicyViewModelDto>()

        if (policyDto != null) {
            val expectedPet = PetMapper.mapPetDtoToPet(
                "200_get_get_pet_detail_successfully.json"
                    .mapJsonToModel<PetDto>()
                    ?: fail("Pet must be not null.")
            )

            val expectedPolicy = PolicyMapper.mapPolicyDtoToPolicy(policyDto, expectedPet)

            fakePolicyRemoteDataSource.setPolicy(expectedPolicy)

            // ACT
            policyRepository.getPolicyDetail(policyId = "6f5300d0-d456-45ec-daa6-08daf4df4b01")
                .test {
                    // ASSERT
                    assertEquals(
                        expected = Result.Success(expectedPolicy),
                        actual = awaitItem()
                    )
                    awaitComplete()
                }
        } else {
            fail("PolicyDto must be not null.")
        }
    }

    @Test
    fun `Get policy detail, when there was an unexpected error`() = runTest {
        // ARRANGE
        val exception = Exception("This is a custom exception", Throwable())
        fakePolicyRemoteDataSource.setException(exception)

        // ACT
        policyRepository.getPolicyDetail(policyId = "6f5300d0-d456-45ec-daa6-08daf4df4b01")
            .test {
                // ASSERT
                assertEquals(
                    expected = Result.Error(exception),
                    actual = awaitItem()
                )
                awaitComplete()
            }
    }
}
