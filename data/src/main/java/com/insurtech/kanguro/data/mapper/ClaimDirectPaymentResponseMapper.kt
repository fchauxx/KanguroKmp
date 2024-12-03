package com.insurtech.kanguro.data.mapper

import com.insurtech.kanguro.common.enums.ClaimStatus
import com.insurtech.kanguro.domain.model.ClaimDirectPaymentResponse
import com.insurtech.kanguro.networking.dto.ClaimDirectPaymentResponseDto

object ClaimDirectPaymentResponseMapper {

    fun claimDirectPaymentResponseDtoToClaimDirectPaymentResponse(dto: ClaimDirectPaymentResponseDto): ClaimDirectPaymentResponse? {
        return ClaimDirectPaymentResponse(
            id = dto.id ?: return null,
            petId = dto.petId ?: return null,
            type = dto.type?.let { ClaimTypeMapper.claimTypeDtoToClaimType(it) } ?: return null,
            status = dto.status?.let { ClaimStatus.fromString(it) } ?: return null,
            decision = dto.decision,
            createdAt = dto.createdAt ?: return null,
            updatedAt = dto.updatedAt ?: return null,
            invoiceDate = dto.invoiceDate ?: return null,
            description = dto.description ?: return null,
            prefixId = dto.prefixId,
            amount = dto.amount ?: return null,
            amountPaid = dto.amountPaid,
            amountTransferred = dto.amountTransferred,
            deductibleContributionAmount = dto.deductibleContributionAmount,
            copayAmount = dto.copayAmount,
            claimFeedback = dto.claimFeedback?.let {
                ClaimFeedbackMapper.claimFeedbackDtoToClaimFeedback(it)
            },
            chatbotSessionsIds = dto.chatbotSessionsIds,
            pet = dto.pet?.let { PetMapper.mapPetDtoToPet(it) },
            hasPendingCommunications = dto.hasPendingCommunications,
            reimbursementProcess = dto.reimbursementProcess?.let {
                ReimbursementProcessMapper.reimbursementProcessDtoToReimbursementProcess(it)
            } ?: return null,
            veterinarianId = dto.veterinarianId,
            fileUrl = dto.fileUrl ?: return null
        )
    }
}
