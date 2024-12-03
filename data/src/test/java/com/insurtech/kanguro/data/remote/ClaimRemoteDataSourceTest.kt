package com.insurtech.kanguro.data.remote

import com.insurtech.kanguro.data.mapper.ClaimMapper
import com.insurtech.kanguro.data.source.ClaimDataSource
import com.insurtech.kanguro.networking.api.RefactoredKanguroClaimsApiService
import com.insurtech.kanguro.networking.dto.ClaimDocumentDto
import com.insurtech.kanguro.networking.dto.ClaimFeedbackBodyDto
import com.insurtech.kanguro.networking.dto.CommunicationBodyDto
import com.insurtech.kanguro.networking.error.RemoteServiceIntegrationError
import com.insurtech.kanguro.testing.BaseUnitTest
import com.insurtech.kanguro.testing.extensions.mapJsonToListOfModels
import com.insurtech.kanguro.testing.files.FileSystemSupport
import com.insurtech.kanguro.testing.rest.RestInfrastructureRule
import com.insurtech.kanguro.testing.rest.wireRestApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue
import kotlin.test.fail

class ClaimRemoteDataSourceTest : BaseUnitTest() {

    @get:Rule val restInfrastructureRule = RestInfrastructureRule()

    private lateinit var claimDataSource: ClaimDataSource

    @Before fun setUp() {
        val refactoredKanguroClaimsApiService = restInfrastructureRule.server
            .wireRestApi<RefactoredKanguroClaimsApiService>()

        claimDataSource = ClaimRemoteDataSource(refactoredKanguroClaimsApiService)
    }

    @Test fun `Get claim documents, when it is requested to get claim documents and there is some issue incoming from client, then return client system error`() = runTest {
        // ARRANGE

        restInfrastructureRule.restScenario(400)

        val claimId = "eb6f2422-ed8a-4d2a-3737-08dafaf2ccea"

        // ACT

        val result = kotlin.runCatching {
            claimDataSource.getClaimDocuments(claimId)
        }

        // ASSERT

        val unwrappedError = unwrapCaughtError(result)

        assertEquals(
            expected = RemoteServiceIntegrationError.ClientOrigin,
            actual = unwrappedError
        )
    }

    @Test fun `Get claim documents, when it is requested to get claim documents and it was not found any claim documents, then return client system error`() = runTest {
        // ARRANGE

        restInfrastructureRule.restScenario(
            statusCode = 404,
            response = FileSystemSupport.loadFile("404_get_get_claim_documents_not_found_claim_documents.json")
        )

        val claimId = "eb6f2422-ed8a-4d2a-3737-08dafaf2ccea"

        // ACT

        val result = kotlin.runCatching {
            claimDataSource.getClaimDocuments(claimId)
        }

        // ASSERT

        val unwrappedError = unwrapCaughtError(result)

        assertEquals(
            expected = RemoteServiceIntegrationError.NotFoundClientOrigin,
            actual = unwrappedError
        )
    }

    @Test fun `Get claim documents, when it is requested to get claim documents and there is some issue incoming from server, then return remote system error`() = runTest {
        // ARRANGE

        restInfrastructureRule.restScenario(500)

        val claimId = "eb6f2422-ed8a-4d2a-3737-08dafaf2ccea"

        // ACT

        val result = kotlin.runCatching {
            claimDataSource.getClaimDocuments(claimId)
        }

        // ASSERT

        val unwrappedError = unwrapCaughtError(result)

        assertEquals(
            expected = RemoteServiceIntegrationError.RemoteSystem,
            actual = unwrappedError
        )
    }

    @Test fun `Get claim documents, when it is requested to get claim documents, then returns claim documents successfully`() = runTest {
        // ARRANGE

        val assetFileName = "200_get_get_claim_documents_successfully.json"

        restInfrastructureRule.restScenario(
            statusCode = 200,
            response = FileSystemSupport.loadFile(assetFileName)
        )

        val claimId = "eb6f2422-ed8a-4d2a-3737-08dafaf2ccea"

        val expectedClaimDocumentDtos = assetFileName
            .mapJsonToListOfModels<ClaimDocumentDto>()

        if (expectedClaimDocumentDtos != null) {
            // ACT

            val response = claimDataSource.getClaimDocuments(claimId)

            // ASSERT

            assertEquals(
                expected = ClaimMapper.mapClaimDocumentDtosToClaimDocuments(expectedClaimDocumentDtos),
                actual = response
            )
        } else {
            fail("ClaimDocumentDtos must be not null.")
        }
    }

    @Test fun `Send claim communications, when it is passed a claim id and a claim communication body DTO and there is some issue incoming from client, then return false`() = runTest {
        // ARRANGE

        val badRequestStatusCode = 400

        restInfrastructureRule.restScenario(
            statusCode = badRequestStatusCode
        )

        // ACT

        val isSuccessfulSendClaimCommunicationsOperation = claimDataSource.sendClaimCommunications(
            claimId = "1",
            claimCommunicationBodyDto = CommunicationBodyDto(
                message = "",
                files = listOf(
                    "file1.jpg",
                    "file2.jpg",
                    "file3.jpg"
                )
            )
        )

        // ASSERT

        assertFalse(isSuccessfulSendClaimCommunicationsOperation)
    }

    @Test fun `Send claim communications, when it is passed a claim id and a claim communication body DTO and there is some issue incoming from server, then return false`() = runTest {
        // ARRANGE

        val internalServerErrorStatusCode = 500

        restInfrastructureRule.restScenario(
            statusCode = internalServerErrorStatusCode
        )

        // ACT

        val isSuccessfulSendClaimCommunicationsOperation = claimDataSource.sendClaimCommunications(
            claimId = "1",
            claimCommunicationBodyDto = CommunicationBodyDto(
                message = "",
                files = listOf(
                    "file1.jpg",
                    "file2.jpg",
                    "file3.jpg"
                )
            )
        )

        // ASSERT

        assertFalse(isSuccessfulSendClaimCommunicationsOperation)
    }

    @Test fun `Send claim communications, when it is passed a claim id and a claim communication body DTO, then return true`() = runTest {
        // ARRANGE

        restInfrastructureRule.restScenario(
            statusCode = 200,
            response = FileSystemSupport.loadFile("200_post_send_claim_communications_successfully.json")
        )

        // ACT

        val isSuccessfulSendClaimCommunicationsOperation = claimDataSource.sendClaimCommunications(
            claimId = "1",
            claimCommunicationBodyDto = CommunicationBodyDto(
                message = "",
                files = listOf(
                    "file1.jpg",
                    "file2.jpg",
                    "file3.jpg"
                )
            )
        )

        // ASSERT

        assertTrue(isSuccessfulSendClaimCommunicationsOperation)
    }

    @Test fun `Put claim feedback, when it is passed a claim id and a claim feedback body DTO and there is some issue incoming from client, then return false`() = runTest {
        // ARRANGE

        val badRequestStatusCode = 400

        restInfrastructureRule.restScenario(
            statusCode = badRequestStatusCode
        )

        // ACT

        val isSuccessfulPutClaimFeedbackOperation = claimDataSource.putClaimFeedback(
            claimId = "1",
            claimFeedbackBodyDto = ClaimFeedbackBodyDto(
                feedbackRate = 5,
                feedbackDescription = "This is a feedback sample."
            )
        )

        // ASSERT

        assertFalse(isSuccessfulPutClaimFeedbackOperation)
    }

    @Test fun `Put claim feedback, when it is passed a claim id and a claim feedback body DTO and there is some issue incoming from server, then return false`() = runTest {
        // ARRANGE

        val internalServerErrorStatusCode = 500

        restInfrastructureRule.restScenario(
            statusCode = internalServerErrorStatusCode
        )

        // ACT

        val isSuccessfulPutClaimFeedbackOperation = claimDataSource.putClaimFeedback(
            claimId = "1",
            claimFeedbackBodyDto = ClaimFeedbackBodyDto(
                feedbackRate = 5,
                feedbackDescription = "This is a feedback sample."
            )
        )

        // ASSERT

        assertFalse(isSuccessfulPutClaimFeedbackOperation)
    }

    @Test fun `Put claim feedback, when it is passed a claim id and a claim feedback body DTO, then return true`() = runTest {
        // ARRANGE

        restInfrastructureRule.restScenario(
            statusCode = 200,
            response = FileSystemSupport.loadFile("204_put_put_claim_feedback_successfully.json")
        )

        // ACT

        val isSuccessfulPutClaimFeedbackOperation = claimDataSource.putClaimFeedback(
            claimId = "1",
            claimFeedbackBodyDto = ClaimFeedbackBodyDto(
                feedbackRate = 5,
                feedbackDescription = "This is a feedback sample."
            )
        )

        // ASSERT

        assertTrue(isSuccessfulPutClaimFeedbackOperation)
    }
}
