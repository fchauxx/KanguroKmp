package com.insurtech.kanguro.data.source

import com.insurtech.kanguro.domain.model.CloudClaimDocument
import com.insurtech.kanguro.domain.model.CloudDocument
import com.insurtech.kanguro.domain.model.CloudDocumentPolicy

interface CloudDocumentDataSource {

    suspend fun getCloudDocuments(): CloudDocument

    suspend fun getCloudDocumentPolicy(policyId: String): CloudDocumentPolicy

    suspend fun getCloudClaimDocument(
        policyId: String,
        claimId: String
    ): CloudClaimDocument
}
