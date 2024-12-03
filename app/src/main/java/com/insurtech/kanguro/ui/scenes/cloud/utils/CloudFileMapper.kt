package com.insurtech.kanguro.ui.scenes.cloud.utils

import com.insurtech.kanguro.domain.model.ClaimDocument
import com.insurtech.kanguro.domain.model.PolicyAttachment
import com.insurtech.kanguro.domain.model.PolicyDocument
import com.insurtech.kanguro.ui.scenes.cloud.CloudFileDocument
import com.insurtech.kanguro.ui.scenes.cloud.adapter.PolicyFilesType

class CloudFileMapper {

    fun mapFrom(policyAttachment: PolicyAttachment): CloudFileDocument {
        return CloudFileDocument(
            type = PolicyFilesType.PolicyAttachment,
            id = policyAttachment.id,
            name = policyAttachment.name,
            fileName = null,
            fileSize = policyAttachment.fileSize
        )
    }

    fun mapFrom(policyDocument: PolicyDocument): CloudFileDocument {
        return CloudFileDocument(
            type = PolicyFilesType.PolicyDocument,
            id = policyDocument.id,
            name = policyDocument.name,
            fileName = policyDocument.filename,
            fileSize = null
        )
    }

    fun mapFrom(claimDocument: ClaimDocument): CloudFileDocument {
        return CloudFileDocument(
            type = PolicyFilesType.ClaimDocuments,
            id = claimDocument.id,
            name = null,
            fileName = claimDocument.fileName,
            fileSize = claimDocument.fileSize
        )
    }
}
