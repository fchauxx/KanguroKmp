import Foundation
import KanguroSharedDomain
import Foundation
import KanguroNetworkDomain

public struct CloudDocumentsPolicyMapper: ModelMapper {
    public typealias T = [CloudDocumentPolicy]

    public init() {}
    
    public static func map<T>(_ input: some Codable) throws -> T {
        guard let input: [RemoteCloudDocumentPolicy] = input as? [RemoteCloudDocumentPolicy] else {
            throw RequestError(errorType: .couldNotMap, errorMessage: "Could not map")
        }
        let cloudDocumentPolicies: [CloudDocumentPolicy] = try input.map {
            try CloudDocumentPolicyMapper.map($0)
        }
        guard let result: T = cloudDocumentPolicies as? T else {
            throw RequestError(errorType: .couldNotMap, errorMessage: "Could not map")
        }
        return result
    }
}

public struct CloudDocumentPolicyMapper: ModelMapper {
    public typealias T = CloudDocumentPolicy

    public init() {}
    
    public static func map<T>(_ input: some Codable) throws -> T {
        guard let input: RemoteCloudDocumentPolicy = input as? RemoteCloudDocumentPolicy else {
            throw RequestError(errorType: .couldNotMap, errorMessage: "Could not map")
        }
        var policyAttachments: [PolicyAttachment]? = nil
        var policyDocuments: [PolicyDocumentData]? = nil
        var claimDocuments: [ClaimDocument]? = nil
        if let remotePolicyAttachments: [RemotePolicyAttachment] = input.policyAttachments {
            policyAttachments = try PolicyAttachmentsMapper.map(remotePolicyAttachments)
        }
        if let remotePolicyDocuments: [RemotePolicyDocumentData] = input.policyDocuments {

            policyDocuments = try PolicyDocumentsMapper.map(remotePolicyDocuments)
        }
        if let remoteClaimDocument: [RemoteClaimDocument] = input.claimDocuments {
            claimDocuments = try ClaimDocumentsMapper.map(remoteClaimDocument)
        }

        guard let result: T = CloudDocumentPolicy(
            id: input.id,
            ciId: input.ciId,
            policyStartDate: input.policyStartDate?.date,
            policyAttachments: policyAttachments,
            policyDocuments: policyDocuments,
            claimDocuments: claimDocuments
        ) as? T else {
            throw RequestError(errorType: .couldNotMap, errorMessage: "Could not map")
        }
        return result
    }
}
