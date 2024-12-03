package com.insurtech.kanguro.data.repository

import app.cash.turbine.test
import com.insurtech.kanguro.data.Result
import com.insurtech.kanguro.data.remote.fakes.FakeClaimRemoteDataSource
import com.insurtech.kanguro.data.repository.impl.RefactoredClaimRepository
import com.insurtech.kanguro.domain.model.ClaimDocument
import com.insurtech.kanguro.domain.model.CommunicationBody
import com.insurtech.kanguro.networking.error.RemoteServiceIntegrationError
import com.insurtech.kanguro.testing.extensions.mapJsonToListOfModels
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.fail

class ClaimRepositoryTest {

    private lateinit var fakeClaimRemoteDataSource: FakeClaimRemoteDataSource

    private lateinit var refactoredClaimRepository: IRefactoredClaimRepository

    @Before fun setUp() {
        fakeClaimRemoteDataSource = FakeClaimRemoteDataSource()

        refactoredClaimRepository = RefactoredClaimRepository(fakeClaimRemoteDataSource)
    }

    @Test fun `Get claim documents, when it is requested to get claim documents and there was an unexpected error, then return result error`() = runTest {
        // ARRANGE

        val expectedException = Exception(RemoteServiceIntegrationError.ClientOrigin)

        fakeClaimRemoteDataSource.setException(expectedException)

        val claimId = "eb6f2422-ed8a-4d2a-3737-08dafaf2ccea"

        // ACT / ASSERT

        refactoredClaimRepository.getClaimDocuments(claimId).test {
            assertEquals(
                expected = Result.Error(expectedException),
                actual = awaitItem()
            )
            awaitComplete()
        }
    }

    @Test fun `Get claim documents, when it is requested to get claim documents and it was not found claim documents, then return error`() = runTest {
        // ARRANGE

        val expectedException = Exception(RemoteServiceIntegrationError.NotFoundClientOrigin)

        fakeClaimRemoteDataSource.setException(expectedException)

        val claimId = "eb6f2422-ed8a-4d2a-3737-08dafaf2ccea"

        // ACT / ASSERT

        refactoredClaimRepository.getClaimDocuments(claimId).test {
            assertEquals(
                expected = Result.Error(expectedException),
                actual = awaitItem()
            )
            awaitComplete()
        }
    }

    @Test fun `Get claim documents, when it is requested to get claim documents, then return result success with claim documents`() = runTest {
        // ARRANGE

        val expectedClaimDocuments = "200_get_get_claim_documents_successfully.json"
            .mapJsonToListOfModels<ClaimDocument>()

        if (expectedClaimDocuments != null) {
            fakeClaimRemoteDataSource.setClaimDocuments(expectedClaimDocuments)

            val claimId = "eb6f2422-ed8a-4d2a-3737-08dafaf2ccea"

            // ACT / ASSERT

            refactoredClaimRepository.getClaimDocuments(claimId).test {
                assertEquals(
                    expected = Result.Success(expectedClaimDocuments),
                    actual = awaitItem()
                )
                awaitComplete()
            }
        } else {
            fail("ClaimDocuments must be not null.")
        }
    }

    @Test fun `Send claim communications, when it is passed a claim id and a claim communication body, then return success with true`() = runTest {
        // ACT

        val result = refactoredClaimRepository.sendClaimCommunications(
            claimId = "1",
            claimCommunicationBody = CommunicationBody(
                message = "",
                files = listOf(
                    "file1.jpg",
                    "file2.jpg",
                    "file3.jpg"
                )
            )
        )

        // ASSERT

        assertEquals(
            expected = Result.Success(true),
            actual = result
        )
    }

    @Test fun `Put claim feedback, when it is passed a claim id and a rating and a description, then return success with true`() = runTest {
        val result = refactoredClaimRepository.putClaimFeedback(
            claimId = "1",
            rating = 5,
            description = "This is a description sample"
        )

        // ASSERT

        assertEquals(
            expected = Result.Success(true),
            actual = result
        )
    }
}
