import Foundation
import KanguroSharedDomain
import Foundation
import KanguroNetworkDomain

public struct ClaimDocumentsMapper: ModelMapper {
    public typealias T = [ClaimDocument]

    public init() {}
    
    public static func map<T>(_ input: some Codable) throws -> T {
        guard let input: [RemoteClaimDocument] = input as? [RemoteClaimDocument] else {
            throw RequestError(errorType: .couldNotMap, errorMessage: "Could not map")
        }
        let claimDocuments: [ClaimDocument] = try input.map {
            try ClaimDocumentMapper.map($0)
        }
        guard let result: T = claimDocuments as? T else {
            throw RequestError(errorType: .couldNotMap, errorMessage: "Could not map")
        }
        return result
    }
}

public struct ClaimDocumentMapper: ModelMapper {
    public typealias T = ClaimDocument

    public init() {}
    
    public static func map<T>(_ input: some Codable) throws -> T {
        guard let input: RemoteClaimDocument = input as? RemoteClaimDocument else {
            throw RequestError(errorType: .couldNotMap, errorMessage: "Could not map")
        }

        var attachments: [Attachment]? = nil
        if let remoteAttachments: [RemoteAttachment] = input.claimDocuments {
            attachments = try AttachmentsMapper.map(remoteAttachments)
        }

        let claimDocument = ClaimDocument(
            claimPrefixId: input.claimPrefixId,
            claimId: input.claimId,
            claimDocuments: attachments
        )

        guard let result: T = claimDocument as? T else {
            throw RequestError(errorType: .couldNotMap, errorMessage: "Could not map")
        }
        return result
    }
}

public struct AttachmentsMapper: ModelMapper {
    public typealias T = [Attachment]
    
    public init() {}

    public static func map<T>(_ input: some Codable) throws -> T {
        guard let input: [RemoteAttachment] = input as? [RemoteAttachment] else {
            throw RequestError(errorType: .couldNotMap, errorMessage: "Could not map")
        }
        let attachments = input.map {
            Attachment(
                id: $0.id,
                fileName: $0.fileName,
                fileSize: $0.fileSize
            )
        }
        guard let result: T = attachments as? T else {
            throw RequestError(errorType: .couldNotMap, errorMessage: "Could not map")
        }
        return result
    }
}
