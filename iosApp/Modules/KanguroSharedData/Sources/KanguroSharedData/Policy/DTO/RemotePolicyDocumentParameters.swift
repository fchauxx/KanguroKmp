import Foundation

public struct RemotePolicyDocumentParameters: Codable {

    public var policyId: String
    public var documentId: Int64
    public var filename: String

    public init(
        policyId: String,
        documentId: Int64,
        filename: String
    ) {
        self.policyId = policyId
        self.documentId = documentId
        self.filename = filename
    }
}
