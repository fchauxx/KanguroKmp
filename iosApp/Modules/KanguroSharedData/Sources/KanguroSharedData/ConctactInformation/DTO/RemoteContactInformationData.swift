import Foundation

public struct RemoteContactInformationData: Codable, Equatable {

    // MARK: - Stored Properties
    public var number: String
    public var text: String

    public init(
        number: String,
        text: String
    ) {
        self.number = number
        self.text = text
    }
}
