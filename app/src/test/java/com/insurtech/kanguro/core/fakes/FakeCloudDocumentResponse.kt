package com.insurtech.kanguro.core.fakes

import com.insurtech.kanguro.domain.model.ClaimDocument
import com.insurtech.kanguro.domain.model.CloudClaimDocument
import com.insurtech.kanguro.domain.model.CloudDocument
import com.insurtech.kanguro.domain.model.CloudDocumentPolicy
import com.insurtech.kanguro.domain.model.CloudPet
import java.util.Date

fun fakeCloudDocuments(): CloudDocument {
    return CloudDocument(
        userId = "User_Id_1",
        pets = fakeCloudPets()
    )
}

fun fakeCloudPets(): List<CloudPet> {
    return listOf(
        CloudPet(
            id = 1,
            name = "Pet One",
            cloudDocumentPolicies = fakeCloudDocumentPolicies(1)
        ),
        CloudPet(
            id = 2,
            name = "Pet Two",
            cloudDocumentPolicies = fakeCloudDocumentPolicies(2)
        )
    )
}

fun fakeCloudDocumentPolicies(petId: Long): List<CloudDocumentPolicy> {
    return listOf(
        fakeCloudDocumentPolicy(petId, "Policy_1", Date(1677639601000)), // 2023-03-01
        fakeCloudDocumentPolicy(petId, "Policy_2", Date(1709262001000)) // 2024-03-01
    )
}

fun fakeCloudDocumentPolicy(petId: Long, policyId: String, date: Date) = CloudDocumentPolicy(
    id = "$petId - $policyId",
    ciId = petId,
    policyStartDate = date,
    policyAttachments = listOf(),
    policyDocuments = listOf(),
    claimDocuments = fakeCloudClaimDocuments("$petId - $policyId")
)

fun fakeCloudClaimDocuments(policyId: String): List<CloudClaimDocument> {
    return listOf(
        CloudClaimDocument(
            claimId = "Claim_1 - $policyId",
            claimPrefixId = "UP 0001",
            claimDocuments = fakeClaimDocuments()
        ),
        CloudClaimDocument(
            claimId = "Claim_2 - $policyId",
            claimPrefixId = "UP 0002",
            claimDocuments = emptyList()
        )
    )
}

fun fakeClaimDocuments(): List<ClaimDocument> {
    return listOf(
        ClaimDocument(
            id = 1,
            fileName = "claim_1.jpg",
            fileSize = 1000
        )
    )
}
