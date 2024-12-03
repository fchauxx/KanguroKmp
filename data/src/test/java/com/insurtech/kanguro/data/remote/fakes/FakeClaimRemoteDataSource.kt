package com.insurtech.kanguro.data.remote.fakes

import com.insurtech.kanguro.data.source.ClaimDataSource
import com.insurtech.kanguro.domain.model.ClaimDocument
import com.insurtech.kanguro.networking.dto.ClaimDirectPaymentDto
import com.insurtech.kanguro.networking.dto.ClaimDirectPaymentResponseDto
import com.insurtech.kanguro.networking.dto.ClaimDto
import com.insurtech.kanguro.networking.dto.ClaimFeedbackBodyDto
import com.insurtech.kanguro.networking.dto.CommunicationBodyDto
import com.insurtech.kanguro.networking.dto.VeterinarianSignatureDto

class FakeClaimRemoteDataSource : ClaimDataSource {

    private var claimDocuments: List<ClaimDocument>? = null
    private var exception: Exception? = null

    fun setClaimDocuments(claimDocuments: List<ClaimDocument>) {
        this.claimDocuments = claimDocuments
    }

    fun setException(exception: Exception) {
        this.exception = exception
    }

    override suspend fun getClaimDocuments(claimId: String): List<ClaimDocument> =
        claimDocuments ?: throw exception!!

    override suspend fun sendClaimCommunications(
        claimId: String,
        claimCommunicationBodyDto: CommunicationBodyDto
    ): Boolean = true

    override suspend fun putClaimFeedback(
        claimId: String,
        claimFeedbackBodyDto: ClaimFeedbackBodyDto
    ): Boolean = true

    override suspend fun postDirectPayment(claim: ClaimDirectPaymentDto): ClaimDirectPaymentResponseDto {
        TODO("Not yet implemented")
    }

    override suspend fun postDirectPaymentVeterinarianSignature(
        claimId: String,
        veterinarianSignatureDto: VeterinarianSignatureDto
    ) {
        TODO("Not yet implemented")
    }

    override suspend fun getDirectPaymentPreSignedFileUrl(claimId: String): String {
        TODO("Not yet implemented")
    }

    override suspend fun getClaims(version: Int): List<ClaimDto> {
        TODO("Not yet implemented")
    }

    override suspend fun getClaim(version: Int, id: String): ClaimDto {
        TODO("Not yet implemented")
    }
}
