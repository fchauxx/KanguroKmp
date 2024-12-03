package com.insurtech.kanguro.data.repository.impl

import com.insurtech.kanguro.data.Result
import com.insurtech.kanguro.data.repository.ICloudDocumentRepository
import com.insurtech.kanguro.data.source.CloudDocumentDataSource
import com.insurtech.kanguro.domain.model.CloudClaimDocument
import com.insurtech.kanguro.domain.model.CloudDocument
import com.insurtech.kanguro.domain.model.CloudDocumentPolicy
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CloudDocumentRepository @Inject constructor(
    private val cloudDocumentDataSource: CloudDocumentDataSource
) : ICloudDocumentRepository {

    override suspend fun getCloudDocuments(): Flow<Result<CloudDocument>> =
        flow {
            try {
                val cloudDocuments = cloudDocumentDataSource.getCloudDocuments()
                emit(Result.Success(cloudDocuments))
            } catch (e: Exception) {
                emit(Result.Error(e))
            }
        }

    override suspend fun getCloudDocumentPolicy(policyId: String): Flow<Result<CloudDocumentPolicy>> =
        flow {
            try {
                val cloudDocumentPolicy = cloudDocumentDataSource
                    .getCloudDocumentPolicy(policyId)
                emit(Result.Success(cloudDocumentPolicy))
            } catch (e: Exception) {
                emit(Result.Error(e))
            }
        }

    override suspend fun getCloudClaimDocument(
        policyId: String,
        claimId: String
    ): Flow<Result<CloudClaimDocument>> =
        flow {
            try {
                val cloudClaimDocument = cloudDocumentDataSource
                    .getCloudClaimDocument(policyId, claimId)
                emit(Result.Success(cloudClaimDocument))
            } catch (e: Exception) {
                emit(Result.Error(e))
            }
        }
}
