import Foundation

public struct RemotePolicyDocumentData: Codable, Equatable {

    public var id: Int64?
    public var name: String?
    public var filename: String?

    public init(
        id: Int64? = nil,
        name: String? = nil,
        filename: String? = nil
    ) {
        self.id = id
        self.name = name
        self.filename = filename
    }
}
