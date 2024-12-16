import Foundation

public struct RemoteCommunication: Codable {

    // MARK: - Stored Properties
    public let id: Int?
    public let type: RemoteCommunicationType?
    public let sender: RemoteCommunicationSender?
    public let message: String?
    public let fileURL: String?
    public let createdAt: String?

    public init(
        id: Int?,
        type: RemoteCommunicationType?,
        sender: RemoteCommunicationSender?,
        message: String?,
        fileURL: String?,
        createdAt: String?
    ) {
        self.id = id
        self.type = type
        self.sender = sender
        self.message = message
        self.fileURL = fileURL
        self.createdAt = createdAt
    }
}
