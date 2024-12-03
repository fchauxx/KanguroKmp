package com.insurtech.kanguro.data.remote.fakes

import com.insurtech.kanguro.data.source.CloudDocumentDataSource
import com.insurtech.kanguro.domain.model.CloudClaimDocument
import com.insurtech.kanguro.domain.model.CloudDocument
import com.insurtech.kanguro.domain.model.CloudDocumentPolicy

class FakeCloudDocumentRemoteDataSource : CloudDocumentDataSource {

    private var cloudDocument: CloudDocument? = null
    private var cloudDocumentPolicy: CloudDocumentPolicy? = null
    private var cloudClaimDocument: CloudClaimDocument? = null
    private var exception: Exception? = null

    fun setCloudDocument(cloudDocument: CloudDocument) {
        this.cloudDocument = cloudDocument
    }

    fun setCloudDocumentPolicy(cloudDocumentPolicy: CloudDocumentPolicy) {
        this.cloudDocumentPolicy = cloudDocumentPolicy
    }

    fun setCloudClaimDocument(cloudClaimDocument: CloudClaimDocument) {
        this.cloudClaimDocument = cloudClaimDocument
    }

    fun setException(exception: Exception) {
        this.exception = exception
    }

    override suspend fun getCloudDocuments(): CloudDocument = cloudDocument ?: throw exception!!

    override suspend fun getCloudDocumentPolicy(
        policyId: String
    ): CloudDocumentPolicy = cloudDocumentPolicy ?: throw exception!!

    override suspend fun getCloudClaimDocument(
        policyId: String,
        claimId: String
    ): CloudClaimDocument = cloudClaimDocument ?: throw exception!!
}
