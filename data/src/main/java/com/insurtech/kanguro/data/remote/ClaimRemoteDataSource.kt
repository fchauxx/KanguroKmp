package com.insurtech.kanguro.data.remote

import com.insurtech.kanguro.data.mapper.ClaimMapper
import com.insurtech.kanguro.data.source.ClaimDataSource
import com.insurtech.kanguro.domain.model.ClaimDocument
import com.insurtech.kanguro.networking.api.RefactoredKanguroClaimsApiService
import com.insurtech.kanguro.networking.dto.ClaimDirectPaymentDto
import com.insurtech.kanguro.networking.dto.ClaimDirectPaymentResponseDto
import com.insurtech.kanguro.networking.dto.ClaimDto
import com.insurtech.kanguro.networking.dto.ClaimFeedbackBodyDto
import com.insurtech.kanguro.networking.dto.CommunicationBodyDto
import com.insurtech.kanguro.networking.dto.VeterinarianSignatureDto
import com.insurtech.kanguro.networking.transformer.managedExecution
import javax.inject.Inject

class ClaimRemoteDataSource @Inject constructor(
    private val refactoredKanguroClaimsApiService: RefactoredKanguroClaimsApiService
) : ClaimDataSource {

    override suspend fun getClaimDocuments(claimId: String): List<ClaimDocument> =
        managedExecution {
            ClaimMapper.mapClaimDocumentDtosToClaimDocuments(
                refactoredKanguroClaimsApiService.getClaimDocuments(claimId)
            )
        }

    override suspend fun sendClaimCommunications(
        claimId: String,
        claimCommunicationBodyDto: CommunicationBodyDto
    ): Boolean =
        try {
            refactoredKanguroClaimsApiService
                .sendClaimCommunications(
                    claimId = claimId,
                    communication = claimCommunicationBodyDto
                )
            true
        } catch (e: Exception) {
            false
        }

    override suspend fun putClaimFeedback(
        claimId: String,
        claimFeedbackBodyDto: ClaimFeedbackBodyDto
    ): Boolean =
        try {
            val response = refactoredKanguroClaimsApiService
                .putClaimFeedback(
                    claimId = claimId,
                    feedback = claimFeedbackBodyDto
                )
            response.isSuccessful
        } catch (e: Exception) {
            false
        }

    override suspend fun postDirectPayment(claim: ClaimDirectPaymentDto): ClaimDirectPaymentResponseDto =
        managedExecution {
            refactoredKanguroClaimsApiService.postDirectPayment(claim)
        }

    override suspend fun postDirectPaymentVeterinarianSignature(
        claimId: String,
        veterinarianSignatureDto: VeterinarianSignatureDto
    ) {
        managedExecution {
            refactoredKanguroClaimsApiService.postDirectPaymentVeterinarianSignature(
                claimId,
                veterinarianSignatureDto
            )
        }
    }

    override suspend fun getDirectPaymentPreSignedFileUrl(claimId: String): String =
        managedExecution {
            refactoredKanguroClaimsApiService.getDirectPaymentPreSignedFileUrl(claimId).string()
        }

    override suspend fun getClaims(version: Int): List<ClaimDto> =
        managedExecution {
            refactoredKanguroClaimsApiService.getClaims(version)
        }

    override suspend fun getClaim(version: Int, id: String): ClaimDto =
        managedExecution {
            refactoredKanguroClaimsApiService.getClaim(version, id)
        }
}
