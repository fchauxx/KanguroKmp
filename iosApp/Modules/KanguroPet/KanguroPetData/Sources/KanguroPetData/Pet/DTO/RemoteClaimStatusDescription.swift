
public struct RemoteClaimStatusDescription: Codable, Equatable {
    public var description: String?
    public var type: String?

    public init(description: String? = nil, type: String? = nil) {
        self.description = description
        self.type = type
    }
}
