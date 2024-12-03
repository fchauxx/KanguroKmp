package com.insurtech.kanguro.data.remote

import com.insurtech.kanguro.data.mapper.CloudDocumentMapper
import com.insurtech.kanguro.data.source.CloudDocumentDataSource
import com.insurtech.kanguro.networking.api.RefactoredKanguroCloudDocumentService
import com.insurtech.kanguro.networking.dto.CloudClaimDocumentDto
import com.insurtech.kanguro.networking.dto.CloudDocumentDto
import com.insurtech.kanguro.networking.dto.CloudDocumentPolicyDto
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

class CloudDocumentRemoteDataSourceTest : BaseUnitTest() {

    @get:Rule val restInfrastructureRule = RestInfrastructureRule()

    private lateinit var cloudDocumentDataSource: CloudDocumentDataSource

    @Before fun setUp() {
        val refactoredKanguroCloudDocumentService = restInfrastructureRule.server
            .wireRestApi<RefactoredKanguroCloudDocumentService>()

        cloudDocumentDataSource = CloudDocumentRemoteDataSource(
            refactoredKanguroCloudDocumentService
        )
    }

    @Test fun `Get cloud document policy, when it is passed a policy id and there is some issue incoming from client, then return client system error`() = runTest {
        // ARRANGE

        restInfrastructureRule.restScenario(400)

        // ACT

        val result = kotlin.runCatching {
            cloudDocumentDataSource.getCloudDocumentPolicy(
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

    @Test fun `Get cloud document policy, when it is passed a policy id and there is some issue incoming from server, then return server system error`() = runTest {
        // ARRANGE

        restInfrastructureRule.restScenario(500)

        // ACT

        val result = kotlin.runCatching {
            cloudDocumentDataSource.getCloudDocumentPolicy(
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

    @Test fun `Get cloud document policy, when it is passed a policy id, then returns a cloud document policy successfully`() = runTest {
        // ARRANGE

        val assetFileName = "200_get_get_cloud_document_policy_successfully.json"

        restInfrastructureRule.restScenario(
            statusCode = 200,
            response = FileSystemSupport.loadFile(assetFileName)
        )

        val expectedResponse = assetFileName.mapJsonToModel<CloudDocumentPolicyDto>()

        if (expectedResponse != null) {
            // ACT

            val response = cloudDocumentDataSource.getCloudDocumentPolicy(
                policyId = "6f5300d0-d456-45ec-daa6-08daf4df4b01"
            )

            // ASSERT

            assertEquals(
                expected = CloudDocumentMapper
                    .mapCloudDocumentPolicyDtoToCloudDocumentPolicy(expectedResponse),
                actual = response
            )
        } else {
            fail("CloudDocumentPolicy must be not null.")
        }
    }

    @Test fun `Get cloud claim document, when it is passed a policy id and a claim id and there is some issue incoming from client, then return client system error`() = runTest {
        // ARRANGE

        restInfrastructureRule.restScenario(400)

        // ACT

        val result = kotlin.runCatching {
            cloudDocumentDataSource.getCloudClaimDocument(
                policyId = "6f5300d0-d456-45ec-daa6-08daf4df4b01",
                claimId = "Claim_1 - 2239"
            )
        }

        // ASSERT

        val unwrappedError = unwrapCaughtError(result)

        assertEquals(
            expected = RemoteServiceIntegrationError.ClientOrigin,
            actual = unwrappedError
        )
    }

    @Test fun `Get cloud claim document, when it is passed a policy id and a claim id and there is some issue incoming from server, then return server system error`() = runTest {
        // ARRANGE

        restInfrastructureRule.restScenario(500)

        // ACT

        val result = kotlin.runCatching {
            cloudDocumentDataSource.getCloudClaimDocument(
                policyId = "6f5300d0-d456-45ec-daa6-08daf4df4b01",
                claimId = "Claim_1 - 2239"
            )
        }

        // ASSERT

        val unwrappedError = unwrapCaughtError(result)

        assertEquals(
            expected = RemoteServiceIntegrationError.RemoteSystem,
            actual = unwrappedError
        )
    }

    @Test fun `Get cloud claim document, when it is passed a policy id and claim id, then returns a cloud claim document successfully`() = runTest {
        // ARRANGE

        val assetFileName = "200_get_get_cloud_claim_document_successfully.json"

        restInfrastructureRule.restScenario(
            statusCode = 200,
            response = FileSystemSupport.loadFile(assetFileName)
        )

        val expectedResponse = assetFileName.mapJsonToModel<CloudClaimDocumentDto>()

        if (expectedResponse != null) {
            // ACT

            val response = cloudDocumentDataSource.getCloudClaimDocument(
                policyId = "6f5300d0-d456-45ec-daa6-08daf4df4b01",
                claimId = "Claim_1 - 2239"
            )

            // ASSERT

            assertEquals(
                expected = CloudDocumentMapper
                    .mapCloudClaimDocumentDtoToCloudClaimDocument(expectedResponse),
                actual = response
            )
        } else {
            fail("CloudClaimDocumentDto must be not null.")
        }
    }

    @Test fun `Get cloud documents, when it is requested to get cloud documents, then returns cloud documents successfully`() = runTest {
        // ARRANGE

        val assetFileName = "200_get_get_cloud_documents_successfully.json"

        restInfrastructureRule.restScenario(
            statusCode = 200,
            response = FileSystemSupport.loadFile(assetFileName)
        )

        val expectedResponse = assetFileName.mapJsonToModel<CloudDocumentDto>()

        if (expectedResponse != null) {
            // ACT

            val response = cloudDocumentDataSource.getCloudDocuments()

            // ASSERT

            assertEquals(
                expected = CloudDocumentMapper
                    .mapCloudDocumentDtoToCloudDocument(expectedResponse),
                actual = response
            )
        } else {
            fail("CloudDocumentDto must be not null.")
        }
    }
}
