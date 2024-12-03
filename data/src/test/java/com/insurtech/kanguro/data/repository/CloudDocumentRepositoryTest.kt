package com.insurtech.kanguro.data.repository

import app.cash.turbine.test
import com.insurtech.kanguro.data.Result
import com.insurtech.kanguro.data.mapper.CloudDocumentMapper
import com.insurtech.kanguro.data.remote.fakes.FakeCloudDocumentRemoteDataSource
import com.insurtech.kanguro.data.repository.impl.CloudDocumentRepository
import com.insurtech.kanguro.networking.dto.CloudClaimDocumentDto
import com.insurtech.kanguro.networking.dto.CloudDocumentDto
import com.insurtech.kanguro.networking.dto.CloudDocumentPolicyDto
import com.insurtech.kanguro.testing.extensions.mapJsonToModel
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.fail

class CloudDocumentRepositoryTest {

    private lateinit var fakeCloudDocumentRemoteDataSource: FakeCloudDocumentRemoteDataSource

    private lateinit var cloudDocumentRepository: ICloudDocumentRepository

    @Before fun setUp() {
        fakeCloudDocumentRemoteDataSource = FakeCloudDocumentRemoteDataSource()

        cloudDocumentRepository = CloudDocumentRepository(
            cloudDocumentDataSource = fakeCloudDocumentRemoteDataSource
        )
    }

    @Test fun `Get cloud document policy, when it is passed a policy id and there was an unexpected error, then return result error`() = runTest {
        // ARRANGE

        val expectedException = Exception("This is a custom exception", Throwable())

        fakeCloudDocumentRemoteDataSource.setException(expectedException)

        // ACT / ASSERT

        cloudDocumentRepository.getCloudDocumentPolicy(
            policyId = "6f5300d0-d456-45ec-daa6-08daf4df4b01"
        ).test {
            assertEquals(
                expected = Result.Error(expectedException),
                actual = awaitItem()
            )
            awaitComplete()
        }
    }

    @Test fun `Get cloud document policy, when it is passed a policy id, then return result success with a cloud document policy`() = runTest {
        // ARRANGE

        val cloudDocumentPolicyDto = "200_get_get_cloud_document_policy_successfully.json"
            .mapJsonToModel<CloudDocumentPolicyDto>()

        if (cloudDocumentPolicyDto != null) {
            val expectedCloudDocumentPolicy = CloudDocumentMapper
                .mapCloudDocumentPolicyDtoToCloudDocumentPolicy(cloudDocumentPolicyDto)

            fakeCloudDocumentRemoteDataSource.setCloudDocumentPolicy(expectedCloudDocumentPolicy)

            // ACT / ASSERT

            cloudDocumentRepository.getCloudDocumentPolicy(
                policyId = "6f5300d0-d456-45ec-daa6-08daf4df4b01"
            ).test {
                assertEquals(
                    expected = Result.Success(expectedCloudDocumentPolicy),
                    actual = awaitItem()
                )
                awaitComplete()
            }
        } else {
            fail("CloudDocumentPolicyDto must be not null.")
        }
    }

    @Test fun `Get cloud claim document, when it is passed a policy id and a claim id and there was an unexpected error, then return result error`() = runTest {
        // ARRANGE

        val expectedException = Exception("This is a custom exception", Throwable())

        fakeCloudDocumentRemoteDataSource.setException(expectedException)

        // ACT / ASSERT

        cloudDocumentRepository.getCloudClaimDocument(
            policyId = "6f5300d0-d456-45ec-daa6-08daf4df4b01",
            claimId = "Claim_1 - 2239"
        ).test {
            assertEquals(
                expected = Result.Error(expectedException),
                actual = awaitItem()
            )
            awaitComplete()
        }
    }

    @Test fun `Get cloud claim document, when it is passed a policy id and a claim id, then return result success with a cloud claim document`() = runTest {
        // ARRANGE

        val cloudClaimDocumentDto = "200_get_get_cloud_claim_document_successfully.json"
            .mapJsonToModel<CloudClaimDocumentDto>()

        if (cloudClaimDocumentDto != null) {
            val expectedCloudClaimDocument = CloudDocumentMapper
                .mapCloudClaimDocumentDtoToCloudClaimDocument(cloudClaimDocumentDto)

            fakeCloudDocumentRemoteDataSource.setCloudClaimDocument(expectedCloudClaimDocument)

            // ACT / ASSERT

            cloudDocumentRepository.getCloudClaimDocument(
                policyId = "6f5300d0-d456-45ec-daa6-08daf4df4b01",
                claimId = "Claim_1 - 2239"
            ).test {
                assertEquals(
                    expected = Result.Success(expectedCloudClaimDocument),
                    actual = awaitItem()
                )
                awaitComplete()
            }
        } else {
            fail("CloudClaimDocumentDto must be not null.")
        }
    }

    @Test fun `Get cloud documents, when it is requested to get cloud documents and there was an unexpected error, then return result error`() = runTest {
        // ARRANGE

        val expectedException = Exception("This is a custom exception", Throwable())

        fakeCloudDocumentRemoteDataSource.setException(expectedException)

        // ACT / ASSERT

        cloudDocumentRepository.getCloudDocuments().test {
            assertEquals(
                expected = Result.Error(expectedException),
                actual = awaitItem()
            )
            awaitComplete()
        }
    }

    @Test fun `Get cloud documents, when it is requested to get cloud documents, then return result success with cloud documents`() = runTest {
        // ARRANGE

        val cloudDocumentDto = "200_get_get_cloud_documents_successfully.json"
            .mapJsonToModel<CloudDocumentDto>()

        if (cloudDocumentDto != null) {
            val expectedCloudDocument = CloudDocumentMapper
                .mapCloudDocumentDtoToCloudDocument(cloudDocumentDto)

            fakeCloudDocumentRemoteDataSource.setCloudDocument(expectedCloudDocument)

            // ACT / ASSERT

            cloudDocumentRepository.getCloudDocuments().test {
                assertEquals(
                    expected = Result.Success(expectedCloudDocument),
                    actual = awaitItem()
                )
                awaitComplete()
            }
        } else {
            fail("CloudDocumentDto must be not null.")
        }
    }
}
