package com.insurtech.kanguro.data.mapper

import com.insurtech.kanguro.domain.model.ClaimDocument
import com.insurtech.kanguro.domain.model.ClaimFeedbackBody
import com.insurtech.kanguro.domain.model.CommunicationBody
import com.insurtech.kanguro.networking.dto.ClaimDocumentDto
import com.insurtech.kanguro.networking.dto.ClaimFeedbackBodyDto
import com.insurtech.kanguro.networking.dto.CommunicationBodyDto
import com.insurtech.kanguro.testing.extensions.mapJsonToListOfModels
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.fail

class ClaimMapperTest {

    @Test fun `Map claim document DTOs to claim documents successfully`() {
        // ARRANGE

        val claimDocumentDtos = "200_get_get_claim_documents_successfully.json"
            .mapJsonToListOfModels<ClaimDocumentDto>()

        if (claimDocumentDtos != null) {
            // ACT

            val claimDocuments = ClaimMapper
                .mapClaimDocumentDtosToClaimDocuments(claimDocumentDtos)

            // ASSERT

            assertEquals(
                expected = listOf(
                    ClaimDocument(
                        id = claimDocumentDtos[0].id,
                        fileName = claimDocumentDtos[0].fileName,
                        fileSize = claimDocumentDtos[0].fileSize
                    ),
                    ClaimDocument(
                        id = claimDocumentDtos[1].id,
                        fileName = claimDocumentDtos[1].fileName,
                        fileSize = claimDocumentDtos[1].fileSize
                    )
                ),
                actual = claimDocuments
            )
        } else {
            fail("ClaimDocumentDtos must be not null.")
        }
    }

    @Test fun `Map communication body to communication body DTO successfully`() {
        // ARRANGE

        val files = listOf(
            "file1.jpg",
            "file2.jpg",
            "file3.jpg"
        )

        val communicationBody = CommunicationBody(
            message = "",
            files = files
        )

        val expectedCommunicationBodyDto = CommunicationBodyDto(
            message = "",
            files = files
        )

        // ACT

        val communicationBodyDto = ClaimMapper
            .mapCommunicationBodyToCommunicationBodyDto(communicationBody)

        // ASSERT

        assertEquals(
            expected = expectedCommunicationBodyDto,
            actual = communicationBodyDto
        )
    }

    @Test fun `Map claim feedback body to claim feedback body DTO successfully`() {
        // ARRANGE

        val feedbackRate = 5
        val feedbackDescription = "This is a claim feedback description body sample"

        val claimFeedbackBody = ClaimFeedbackBody(
            feedbackRate = feedbackRate,
            feedbackDescription = feedbackDescription
        )

        val expectedClaimFeedbackDto = ClaimFeedbackBodyDto(
            feedbackRate = feedbackRate,
            feedbackDescription = feedbackDescription
        )

        // ACT

        val claimFeedbackBodyDto = ClaimMapper
            .mapClaimFeedbackBodyToClaimFeedbackBodyDto(claimFeedbackBody)

        // ASSERT

        assertEquals(
            expected = expectedClaimFeedbackDto,
            actual = claimFeedbackBodyDto
        )
    }
}
