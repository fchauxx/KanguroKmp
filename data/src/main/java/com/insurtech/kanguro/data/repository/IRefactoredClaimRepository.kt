package com.insurtech.kanguro.data.repository

import com.insurtech.kanguro.data.Result
import com.insurtech.kanguro.domain.model.Claim
import com.insurtech.kanguro.domain.model.ClaimDirectPayment
import com.insurtech.kanguro.domain.model.ClaimDirectPaymentResponse
import com.insurtech.kanguro.domain.model.ClaimDocument
import com.insurtech.kanguro.domain.model.CommunicationBody
import kotlinx.coroutines.flow.Flow

interface IRefactoredClaimRepository {

    suspend fun getClaimDocuments(claimId: String): Flow<Result<List<ClaimDocument>>>

    suspend fun sendClaimCommunications(
        claimId: String,
        claimCommunicationBody: CommunicationBody
    ): Result<Boolean>

    suspend fun putClaimFeedback(
        claimId: String,
        rating: Int,
        description: String?
    ): Result<Boolean>

    suspend fun postDirectPayment(claim: ClaimDirectPayment): Result<ClaimDirectPaymentResponse>

    suspend fun postDirectPaymentVeterinarianSignature(claimId: String, fileIds: List<Int>): Result<Boolean>

    suspend fun getDirectPaymentPreSignedFileUrl(claimId: String): Result<String>

    suspend fun getClaims(): Result<List<Claim>>

    suspend fun getClaim(id: String): Result<Claim>
}
