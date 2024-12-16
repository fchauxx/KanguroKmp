import Foundation

public struct RemoteSessionId: Codable {
    public var sessionId: String

    public init(sessionId: String) {
        self.sessionId = sessionId
    }
}
