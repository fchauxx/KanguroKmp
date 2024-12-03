package com.insurtech.kanguro.data.mapper

import com.insurtech.kanguro.domain.model.ClaimDocument
import com.insurtech.kanguro.domain.model.CloudClaimDocument
import com.insurtech.kanguro.domain.model.CloudDocument
import com.insurtech.kanguro.domain.model.CloudDocumentPolicy
import com.insurtech.kanguro.domain.model.CloudPet
import com.insurtech.kanguro.domain.model.CloudRenters
import com.insurtech.kanguro.domain.model.PolicyDocument
import com.insurtech.kanguro.networking.dto.CloudClaimDocumentDto
import com.insurtech.kanguro.networking.dto.CloudDocumentDto
import com.insurtech.kanguro.networking.dto.CloudDocumentPolicyDto
import com.insurtech.kanguro.testing.extensions.mapJsonToModel
import org.junit.Test
import java.util.Date
import kotlin.test.assertEquals
import kotlin.test.fail

class CloudDocumentMapperTest {

    @Test fun `Map cloud document policy DTO to cloud document policy successfully`() {
        // ARRANGE

        val cloudDocumentPolicyDto = "200_get_get_cloud_document_policy_successfully.json"
            .mapJsonToModel<CloudDocumentPolicyDto>()

        // ACT

        if (cloudDocumentPolicyDto != null) {
            val cloudDocumentPolicy = CloudDocumentMapper
                .mapCloudDocumentPolicyDtoToCloudDocumentPolicy(cloudDocumentPolicyDto)

            // ASSERT

            assertEquals(
                expected = CloudDocumentPolicy(
                    id = "6f5300d0-d456-45ec-daa6-08daf4df4b01",
                    ciId = 20000488,
                    policyStartDate = Date(1673913600000L),
                    policyAttachments = listOf(),
                    policyDocuments = listOf(
                        PolicyDocument(
                            id = 2239L,
                            name = "Declarations and Schedule of Insurance Page",
                            filename = "Declarations and Schedule of Insurance Page (20000488).pdf"
                        ),
                        PolicyDocument(
                            id = 2238L,
                            name = "Policy Document",
                            filename = "Policy Document (20000488).pdf"
                        ),
                        PolicyDocument(
                            id = 2240L,
                            name = "Preventive Care Endorsement",
                            filename = "Preventive Care Endorsement (20000488).pdf"
                        )
                    ),
                    claimDocuments = listOf()
                ),
                actual = cloudDocumentPolicy
            )
        } else {
            fail("CloudDocumentPolicyDto must be not null.")
        }
    }

    @Test fun `Map cloud claim document DTO to cloud claim document successfully`() {
        // ARRANGE

        val cloudClaimDocumentDto = "200_get_get_cloud_claim_document_successfully.json"
            .mapJsonToModel<CloudClaimDocumentDto>()

        // ACT

        if (cloudClaimDocumentDto != null) {
            val cloudClaimDocument = CloudDocumentMapper
                .mapCloudClaimDocumentDtoToCloudClaimDocument(cloudClaimDocumentDto)

            // ASSERT

            assertEquals(
                expected = CloudClaimDocument(
                    claimId = "Claim_1 - 2239",
                    claimPrefixId = "UP 0001",
                    claimDocuments = listOf(
                        ClaimDocument(
                            id = 1L,
                            fileName = "claim_1.jpg",
                            fileSize = 1000L
                        )
                    )
                ),
                actual = cloudClaimDocument
            )
        } else {
            fail("CloudClaimDocumentDto must be not null.")
        }
    }

    @Test fun `Map cloud document DTOs to cloud documents successfully`() {
        // ARRANGE

        val cloudDocumentDto = "200_get_get_cloud_documents_successfully.json"
            .mapJsonToModel<CloudDocumentDto>()

        // ACT

        if (cloudDocumentDto != null) {
            val cloudDocument = CloudDocumentMapper
                .mapCloudDocumentDtoToCloudDocument(cloudDocumentDto)

            // ASSERT

            assertEquals(
                expected = CloudDocument(
                    userId = "67991761-77a1-44c7-a0f7-90d74a8bd8ed",
                    pets = listOf(
                        CloudPet(
                            id = 446L,
                            name = "Helga",
                            cloudDocumentPolicies = listOf(
                                CloudDocumentPolicy(
                                    id = "6f5300d0-d456-45ec-daa6-08daf4df4b01",
                                    ciId = 20000488L,
                                    policyStartDate = Date(1673913600000L)
                                )
                            )
                        )
                    ),
                    renters = listOf(
                        CloudRenters(
                            id = "67991761-77a1-44c7-a0f7-90d74a8bd8ed",
                            name = "Home",
                            cloudDocumentPolicies = listOf(
                                CloudDocumentPolicy(
                                    id = "6f5300d0-d456-45ec-daa6-08daf4df4b01",
                                    ciId = 20000500L,
                                    policyStartDate = Date(1673913600000L)
                                )
                            )
                        )
                    )
                ),
                actual = cloudDocument
            )
        } else {
            fail("CloudDocumentDto must be not null.")
        }
    }
}
