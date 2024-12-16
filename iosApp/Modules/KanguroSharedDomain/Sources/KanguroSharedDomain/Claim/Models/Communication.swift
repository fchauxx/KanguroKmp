import Foundation

public struct Communication: Equatable {

    // MARK: - Stored Properties
    public let id: Int?
    public let type: CommunicationType?
    public let sender: CommunicationSender?
    public let message: String?
    public let fileURL: String?
    public let createdAt: Date?

    // MARK: - Computed Properties
    public var isValidMessage: Bool {
        return !(message?.isEmpty ?? true)
    }

    public init(
        id: Int?,
        type: CommunicationType?,
        sender: CommunicationSender?,
        message: String?,
        fileURL: String?,
        createdAt: Date?
    ) {
        self.id = id
        self.type = type
        self.sender = sender
        self.message = message
        self.fileURL = fileURL
        self.createdAt = createdAt
    }
}
