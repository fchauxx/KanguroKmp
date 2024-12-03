package com.insurtech.kanguro.data.repository

import com.insurtech.kanguro.data.Result
import com.insurtech.kanguro.domain.model.CloudClaimDocument
import com.insurtech.kanguro.domain.model.CloudDocument
import com.insurtech.kanguro.domain.model.CloudDocumentPolicy
import kotlinx.coroutines.flow.Flow

interface ICloudDocumentRepository {

    suspend fun getCloudDocuments(): Flow<Result<CloudDocument>>

    suspend fun getCloudDocumentPolicy(policyId: String): Flow<Result<CloudDocumentPolicy>>

    suspend fun getCloudClaimDocument(
        policyId: String,
        claimId: String
    ): Flow<Result<CloudClaimDocument>>
}
