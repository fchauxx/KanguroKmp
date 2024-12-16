import Foundation

public struct RemoteContactInformation: Codable, Equatable {

    // MARK: - Stored Properties
    public var type: RemoteContactInformationType
    public var action: String
    public var data: RemoteContactInformationData

    public init(
        type: RemoteContactInformationType,
        action: String,
        data: RemoteContactInformationData
    ) {
        self.type = type
        self.action = action
        self.data = data
    }
}
