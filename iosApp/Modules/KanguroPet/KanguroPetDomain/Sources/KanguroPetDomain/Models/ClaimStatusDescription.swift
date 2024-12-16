public struct ClaimStatusDescription: Codable, Equatable {
    public var description: String
    public var type: StatusDescriptionType

    public init(description: String, type: StatusDescriptionType) {
        self.description = description
        self.type = type
    }
}
