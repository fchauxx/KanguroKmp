package com.insurtech.kanguro.data.mapper

import com.insurtech.kanguro.domain.model.ClaimDirectPayment
import com.insurtech.kanguro.networking.dto.ClaimDirectPaymentDto

object ClaimDirectPaymentMapper {

    fun claimDirectPaymentToClaimDirectPaymentDto(claimDirectPayment: ClaimDirectPayment): ClaimDirectPaymentDto =
        ClaimDirectPaymentDto(
            petId = claimDirectPayment.petId,
            type = claimDirectPayment.type?.let { ClaimTypeMapper.claimTypeToClaimTypeDto(it) },
            invoiceDate = claimDirectPayment.invoiceDate,
            description = claimDirectPayment.description,
            amount = claimDirectPayment.amount,
            veterinarianId = claimDirectPayment.veterinarianId,
            pledgeOfHonor = claimDirectPayment.pledgeOfHonor,
            pledgeOfHonorExtension = claimDirectPayment.pledgeOfHonorExtension,
            veterinarianName = claimDirectPayment.veterinarianName,
            veterinarianEmail = claimDirectPayment.veterinarianEmail,
            veterinarianClinic = claimDirectPayment.veterinarianClinic
        )
}
