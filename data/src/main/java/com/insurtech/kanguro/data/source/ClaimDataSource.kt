package com.insurtech.kanguro.data.source

import com.insurtech.kanguro.domain.model.ClaimDocument
import com.insurtech.kanguro.networking.dto.ClaimDirectPaymentDto
import com.insurtech.kanguro.networking.dto.ClaimDirectPaymentResponseDto
import com.insurtech.kanguro.networking.dto.ClaimDto
import com.insurtech.kanguro.networking.dto.ClaimFeedbackBodyDto
import com.insurtech.kanguro.networking.dto.CommunicationBodyDto
import com.insurtech.kanguro.networking.dto.VeterinarianSignatureDto

interface ClaimDataSource {

    suspend fun getClaimDocuments(claimId: String): List<ClaimDocument>

    suspend fun sendClaimCommunications(
        claimId: String,
        claimCommunicationBodyDto: CommunicationBodyDto
    ): Boolean

    suspend fun putClaimFeedback(
        claimId: String,
        claimFeedbackBodyDto: ClaimFeedbackBodyDto
    ): Boolean

    suspend fun postDirectPayment(claim: ClaimDirectPaymentDto): ClaimDirectPaymentResponseDto

    suspend fun postDirectPaymentVeterinarianSignature(
        claimId: String,
        veterinarianSignatureDto: VeterinarianSignatureDto
    )

    suspend fun getDirectPaymentPreSignedFileUrl(claimId: String): String

    suspend fun getClaims(version: Int): List<ClaimDto>

    suspend fun getClaim(version: Int, id: String): ClaimDto
}
