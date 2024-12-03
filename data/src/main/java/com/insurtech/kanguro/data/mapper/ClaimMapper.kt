package com.insurtech.kanguro.data.mapper

import com.insurtech.kanguro.common.enums.ClaimStatus
import com.insurtech.kanguro.domain.model.Claim
import com.insurtech.kanguro.domain.model.ClaimDocument
import com.insurtech.kanguro.domain.model.ClaimFeedbackBody
import com.insurtech.kanguro.domain.model.CommunicationBody
import com.insurtech.kanguro.networking.dto.ClaimDocumentDto
import com.insurtech.kanguro.networking.dto.ClaimDto
import com.insurtech.kanguro.networking.dto.ClaimFeedbackBodyDto
import com.insurtech.kanguro.networking.dto.CommunicationBodyDto

object ClaimMapper {

    fun mapClaimDocumentDtosToClaimDocuments(
        claimDocumentDtos: List<ClaimDocumentDto>
    ): List<ClaimDocument> {
        val claimDocuments = mutableListOf<ClaimDocument>()

        for (claimDocumentDto in claimDocumentDtos) {
            claimDocuments.add(mapClaimDocumentDtoToClaimDocument(claimDocumentDto))
        }

        return claimDocuments
    }

    fun mapCommunicationBodyToCommunicationBodyDto(communicationBody: CommunicationBody): CommunicationBodyDto =
        CommunicationBodyDto(
            message = communicationBody.message,
            files = communicationBody.files
        )

    fun mapClaimFeedbackBodyToClaimFeedbackBodyDto(
        claimFeedbackBody: ClaimFeedbackBody
    ): ClaimFeedbackBodyDto =
        ClaimFeedbackBodyDto(
            feedbackRate = claimFeedbackBody.feedbackRate,
            feedbackDescription = claimFeedbackBody.feedbackDescription
        )

    private fun mapClaimDocumentDtoToClaimDocument(claimDocumentDto: ClaimDocumentDto): ClaimDocument =
        ClaimDocument(
            id = claimDocumentDto.id,
            fileName = claimDocumentDto.fileName,
            fileSize = claimDocumentDto.fileSize
        )

    fun mapClaimDtosToClaims(claimDtos: List<ClaimDto>): List<Claim> {
        return claimDtos.mapNotNull { mapClaimDtoToClaim(it) }
    }

    fun mapClaimDtoToClaim(claimDto: ClaimDto): Claim? {
        return Claim(
            id = claimDto.id ?: return null,
            pet = claimDto.pet?.let { PetMapper.mapPetDtoToPet(it) } ?: return null,
            type = claimDto.type?.let { ClaimTypeMapper.claimTypeDtoToClaimType(it) } ?: return null,
            status = claimDto.status?.let { ClaimStatus.fromString(it) } ?: ClaimStatus.Unknown,
            chatbotSessionsIds = claimDto.chatbotSessionsIds,
            createdAt = claimDto.createdAt,
            updatedAt = claimDto.updatedAt,
            invoiceDate = claimDto.invoiceDate,
            amount = claimDto.amount,
            amountPaid = claimDto.amountPaid,
            amountTransferred = claimDto.amountTransferred,
            description = claimDto.description,
            decision = claimDto.decision,
            prefixId = claimDto.prefixId,
            deductibleContributionAmount = claimDto.deductibleContributionAmount,
            reimbursementProcess = claimDto.reimbursementProcess?.let {
                ReimbursementProcessMapper.reimbursementProcessDtoToReimbursementProcess(it)
            },
            statusDescription = claimDto.statusDescription?.let {
                ClaimStatusDescriptionMapper.mapStatusDescriptionDtoToStatusDescription(it)
            }
        )
    }
}
