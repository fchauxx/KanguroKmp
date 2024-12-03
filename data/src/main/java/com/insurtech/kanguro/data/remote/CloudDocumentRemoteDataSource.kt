package com.insurtech.kanguro.data.remote

import com.insurtech.kanguro.data.mapper.CloudDocumentMapper
import com.insurtech.kanguro.data.source.CloudDocumentDataSource
import com.insurtech.kanguro.domain.model.CloudClaimDocument
import com.insurtech.kanguro.domain.model.CloudDocument
import com.insurtech.kanguro.domain.model.CloudDocumentPolicy
import com.insurtech.kanguro.networking.api.RefactoredKanguroCloudDocumentService
import com.insurtech.kanguro.networking.transformer.managedExecution
import javax.inject.Inject

class CloudDocumentRemoteDataSource @Inject constructor(
    private val refactoredKanguroCloudDocumentService: RefactoredKanguroCloudDocumentService
) : CloudDocumentDataSource {

    override suspend fun getCloudDocuments(): CloudDocument =
        managedExecution {
            CloudDocumentMapper
                .mapCloudDocumentDtoToCloudDocument(
                    refactoredKanguroCloudDocumentService
                        .getCloudDocuments()
                )
        }

    override suspend fun getCloudDocumentPolicy(policyId: String): CloudDocumentPolicy =
        managedExecution {
            CloudDocumentMapper
                .mapCloudDocumentPolicyDtoToCloudDocumentPolicy(
                    refactoredKanguroCloudDocumentService
                        .getCloudDocumentPolicy(policyId)
                )
        }

    override suspend fun getCloudClaimDocument(
        policyId: String,
        claimId: String
    ): CloudClaimDocument =
        managedExecution {
            CloudDocumentMapper
                .mapCloudClaimDocumentDtoToCloudClaimDocument(
                    refactoredKanguroCloudDocumentService
                        .getCloudClaimDocument(
                            policyId = policyId,
                            claimId = claimId
                        )
                )
        }
}
