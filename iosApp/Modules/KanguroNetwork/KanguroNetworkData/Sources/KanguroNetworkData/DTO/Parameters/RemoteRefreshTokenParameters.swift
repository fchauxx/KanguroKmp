import Foundation

public struct RemoteRefreshTokenParameters: Codable {

    public let refreshToken: String

    public init(refreshToken: String) {
        self.refreshToken = refreshToken
    }
}
