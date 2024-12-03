package com.insurtech.kanguro.data.repository.impl

import com.insurtech.kanguro.data.Result
import com.insurtech.kanguro.data.mapper.ClaimDirectPaymentMapper
import com.insurtech.kanguro.data.mapper.ClaimDirectPaymentResponseMapper
import com.insurtech.kanguro.data.mapper.ClaimMapper
import com.insurtech.kanguro.data.repository.IRefactoredClaimRepository
import com.insurtech.kanguro.data.source.ClaimDataSource
import com.insurtech.kanguro.domain.model.Claim
import com.insurtech.kanguro.domain.model.ClaimDirectPayment
import com.insurtech.kanguro.domain.model.ClaimDirectPaymentResponse
import com.insurtech.kanguro.domain.model.ClaimDocument
import com.insurtech.kanguro.domain.model.CommunicationBody
import com.insurtech.kanguro.networking.dto.ClaimFeedbackBodyDto
import com.insurtech.kanguro.networking.dto.VeterinarianSignatureDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RefactoredClaimRepository @Inject constructor(
    private val claimRemoteDataSource: ClaimDataSource
) : IRefactoredClaimRepository {

    override suspend fun getClaimDocuments(claimId: String): Flow<Result<List<ClaimDocument>>> =
        flow {
            try {
                val claimDocuments = claimRemoteDataSource.getClaimDocuments(claimId)
                emit(Result.Success(claimDocuments))
            } catch (e: Exception) {
                emit(Result.Error(e))
            }
        }

    override suspend fun sendClaimCommunications(
        claimId: String,
        claimCommunicationBody: CommunicationBody
    ): Result<Boolean> =
        Result.Success(
            claimRemoteDataSource.sendClaimCommunications(
                claimId = claimId,
                claimCommunicationBodyDto = ClaimMapper
                    .mapCommunicationBodyToCommunicationBodyDto(claimCommunicationBody)
            )
        )

    override suspend fun putClaimFeedback(
        claimId: String,
        rating: Int,
        description: String?
    ): Result<Boolean> =
        Result.Success(
            claimRemoteDataSource.putClaimFeedback(
                claimId = claimId,
                claimFeedbackBodyDto = ClaimFeedbackBodyDto(
                    feedbackRate = rating,
                    feedbackDescription = description
                )
            )
        )

    override suspend fun postDirectPayment(claim: ClaimDirectPayment): Result<ClaimDirectPaymentResponse> {
        return try {
            val claimDirectPaymentDto =
                ClaimDirectPaymentMapper.claimDirectPaymentToClaimDirectPaymentDto(claim)

            val claimDirectPaymentResponse =
                ClaimDirectPaymentResponseMapper.claimDirectPaymentResponseDtoToClaimDirectPaymentResponse(
                    claimRemoteDataSource.postDirectPayment(claimDirectPaymentDto)
                )

            if (claimDirectPaymentResponse != null) {
                Result.Success(claimDirectPaymentResponse)
            } else {
                Result.Error(NullPointerException())
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun postDirectPaymentVeterinarianSignature(
        claimId: String,
        fileIds: List<Int>
    ): Result<Boolean> {
        return try {
            claimRemoteDataSource.postDirectPaymentVeterinarianSignature(
                claimId,
                VeterinarianSignatureDto(fileIds)
            )
            Result.Success(true)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun getDirectPaymentPreSignedFileUrl(claimId: String): Result<String> {
        return try {
            val preSignedFileUrl = claimRemoteDataSource.getDirectPaymentPreSignedFileUrl(claimId)
            Result.Success(preSignedFileUrl)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun getClaims(): Result<List<Claim>> {
        return try {
            val claims = ClaimMapper.mapClaimDtosToClaims(
                claimRemoteDataSource.getClaims(2)
            )
            Result.Success(claims)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun getClaim(id: String): Result<Claim> {
        return try {
            val claim = ClaimMapper.mapClaimDtoToClaim(
                claimRemoteDataSource.getClaim(2, id)
            )

            if (claim == null) {
                return Result.Error(NullPointerException())
            }

            Result.Success(claim)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}
