import Foundation

public struct RemoteToken: Codable, Equatable {

    public var accessToken: String?
    public var expiresOn: String?
    public var refreshToken: String?
    public var idToken: String?

    public init(
        accessToken: String? = nil,
        expiresOn: String? = nil,
        refreshToken: String? = nil,
        idToken: String? = nil
    ) {
        self.accessToken = accessToken
        self.expiresOn = expiresOn
        self.refreshToken = refreshToken
        self.idToken = idToken
    }
}
