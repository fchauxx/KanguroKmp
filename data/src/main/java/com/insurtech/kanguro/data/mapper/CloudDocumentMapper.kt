package com.insurtech.kanguro.data.mapper

import com.insurtech.kanguro.domain.model.ClaimDocument
import com.insurtech.kanguro.domain.model.CloudClaimDocument
import com.insurtech.kanguro.domain.model.CloudDocument
import com.insurtech.kanguro.domain.model.CloudDocumentPolicy
import com.insurtech.kanguro.domain.model.CloudPet
import com.insurtech.kanguro.domain.model.CloudRenters
import com.insurtech.kanguro.domain.model.PolicyAttachment
import com.insurtech.kanguro.domain.model.PolicyDocument
import com.insurtech.kanguro.networking.dto.ClaimDocumentDto
import com.insurtech.kanguro.networking.dto.CloudClaimDocumentDto
import com.insurtech.kanguro.networking.dto.CloudDocumentDto
import com.insurtech.kanguro.networking.dto.CloudDocumentPolicyDto
import com.insurtech.kanguro.networking.dto.CloudPetDto
import com.insurtech.kanguro.networking.dto.CloudRentersDto
import com.insurtech.kanguro.networking.dto.PolicyAttachmentDto
import com.insurtech.kanguro.networking.dto.PolicyDocumentDto

object CloudDocumentMapper {

    fun mapCloudDocumentDtoToCloudDocument(
        cloudDocumentDto: CloudDocumentDto
    ): CloudDocument =
        CloudDocument(
            userId = cloudDocumentDto.userId,
            pets = mapCloudPetDtosToCloudPets(cloudDocumentDto.pets),
            renters = mapCloudRentersDtosToCloudRenters(cloudDocumentDto.renters)
        )

    fun mapCloudDocumentPolicyDtoToCloudDocumentPolicy(
        cloudDocumentPolicyDto: CloudDocumentPolicyDto
    ): CloudDocumentPolicy =
        CloudDocumentPolicy(
            id = cloudDocumentPolicyDto.id,
            ciId = cloudDocumentPolicyDto.ciId,
            policyStartDate = cloudDocumentPolicyDto.policyStartDate,
            policyAttachments = mapPolicyAttachmentDtosToPolicyAttachments(
                cloudDocumentPolicyDto.policyAttachments
            ),
            policyDocuments = mapPolicyDocumentDtosToPolicyDocuments(cloudDocumentPolicyDto.policyDocuments),
            claimDocuments = mapCloudClaimDocumentDtosToCloudClaimDocuments(cloudDocumentPolicyDto.claimDocuments)
        )

    fun mapCloudClaimDocumentDtoToCloudClaimDocument(
        cloudClaimDocumentDto: CloudClaimDocumentDto
    ): CloudClaimDocument =
        CloudClaimDocument(
            claimId = cloudClaimDocumentDto.claimId,
            claimPrefixId = cloudClaimDocumentDto.claimPrefixId,
            claimDocuments = mapClaimDocumentDtosToClaimDocuments(cloudClaimDocumentDto.claimDocuments)
        )

    private fun mapCloudPetDtosToCloudPets(
        cloudPetDtos: List<CloudPetDto>?
    ): List<CloudPet>? {
        if (cloudPetDtos == null) return null

        val cloudPets = mutableListOf<CloudPet>()

        for (cloudPetDto in cloudPetDtos) {
            cloudPets.add(mapCloudPetDtoToCloudPet(cloudPetDto))
        }

        return cloudPets
    }

    private fun mapCloudRentersDtosToCloudRenters(
        cloudRenterDtos: List<CloudRentersDto>?
    ): List<CloudRenters>? {
        if (cloudRenterDtos == null) return null

        val cloudRenters = mutableListOf<CloudRenters>()

        for (cloudRenterDto in cloudRenterDtos) {
            cloudRenters.add(mapCloudRenterDtoToCloudRenter(cloudRenterDto))
        }

        return cloudRenters
    }

    private fun mapCloudRenterDtoToCloudRenter(
        cloudRenterDto: CloudRentersDto
    ): CloudRenters =
        CloudRenters(
            id = cloudRenterDto.id,
            name = cloudRenterDto.name,
            cloudDocumentPolicies = mapCloudDocumentPolicyDtosToCloudDocumentPolicies(
                cloudRenterDto.cloudDocumentPolicies
            )
        )

    private fun mapCloudPetDtoToCloudPet(cloudPetDto: CloudPetDto): CloudPet =
        CloudPet(
            id = cloudPetDto.id,
            name = cloudPetDto.name,
            cloudDocumentPolicies = mapCloudDocumentPolicyDtosToCloudDocumentPolicies(
                cloudPetDto.cloudDocumentPolicies
            )
        )

    private fun mapCloudDocumentPolicyDtosToCloudDocumentPolicies(
        cloudDocumentPolicyDtos: List<CloudDocumentPolicyDto>?
    ): List<CloudDocumentPolicy>? {
        if (cloudDocumentPolicyDtos == null) return null

        val cloudDocumentPolicies = mutableListOf<CloudDocumentPolicy>()

        for (cloudDocumentPolicyDto in cloudDocumentPolicyDtos) {
            cloudDocumentPolicies.add(mapCloudDocumentPolicyDtoToCloudDocumentPolicy(cloudDocumentPolicyDto))
        }

        return cloudDocumentPolicies
    }

    private fun mapPolicyAttachmentDtosToPolicyAttachments(
        policyAttachmentDtos: List<PolicyAttachmentDto>?
    ): List<PolicyAttachment>? {
        if (policyAttachmentDtos == null) return null

        val policyAttachments = mutableListOf<PolicyAttachment>()

        for (policyAttachmentDto in policyAttachmentDtos) {
            policyAttachments.add(mapPolicyAttachmentDtoToPolicyAttachment(policyAttachmentDto))
        }

        return policyAttachments
    }

    private fun mapPolicyAttachmentDtoToPolicyAttachment(
        policyAttachmentDto: PolicyAttachmentDto
    ): PolicyAttachment =
        PolicyAttachment(
            id = policyAttachmentDto.id,
            name = policyAttachmentDto.name,
            fileSize = policyAttachmentDto.fileSize
        )

    private fun mapPolicyDocumentDtosToPolicyDocuments(
        policyDocumentDtos: List<PolicyDocumentDto>?
    ): List<PolicyDocument>? {
        if (policyDocumentDtos == null) return null

        val policyDocuments = mutableListOf<PolicyDocument>()

        for (policyDocumentDto in policyDocumentDtos) {
            policyDocuments.add(mapPolicyDocumentDtoToPolicyDocument(policyDocumentDto))
        }

        return policyDocuments
    }

    private fun mapPolicyDocumentDtoToPolicyDocument(
        policyDocumentDto: PolicyDocumentDto
    ): PolicyDocument =
        PolicyDocument(
            id = policyDocumentDto.id,
            name = policyDocumentDto.name,
            filename = policyDocumentDto.filename
        )

    private fun mapCloudClaimDocumentDtosToCloudClaimDocuments(
        cloudClaimDocumentDtos: List<CloudClaimDocumentDto>?
    ): List<CloudClaimDocument>? {
        if (cloudClaimDocumentDtos == null) return null

        val cloudClaimDocuments = mutableListOf<CloudClaimDocument>()

        for (cloudClaimDocumentDto in cloudClaimDocumentDtos) {
            cloudClaimDocuments.add(
                mapCloudClaimDocumentDtoToCloudClaimDocument(cloudClaimDocumentDto)
            )
        }

        return cloudClaimDocuments
    }

    private fun mapClaimDocumentDtosToClaimDocuments(
        claimDocumentDtos: List<ClaimDocumentDto>?
    ): List<ClaimDocument>? {
        if (claimDocumentDtos == null) return null

        val claimDocuments = mutableListOf<ClaimDocument>()

        for (claimDocumentDto in claimDocumentDtos) {
            claimDocuments.add(mapClaimDocumentDtoToClaimDocument(claimDocumentDto))
        }

        return claimDocuments
    }

    private fun mapClaimDocumentDtoToClaimDocument(
        claimDocumentDto: ClaimDocumentDto
    ): ClaimDocument =
        ClaimDocument(
            id = claimDocumentDto.id,
            fileName = claimDocumentDto.fileName,
            fileSize = claimDocumentDto.fileSize
        )
}
