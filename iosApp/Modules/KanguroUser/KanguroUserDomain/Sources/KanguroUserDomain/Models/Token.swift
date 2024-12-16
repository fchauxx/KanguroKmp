import Foundation

public struct Token: Equatable {

    public var accessToken: String?
    public var expiresOn: Date?
    public var refreshToken: String?
    public var idToken: String?

    public init(
        accessToken: String? = nil,
        expiresOn: Date? = nil,
        refreshToken: String? = nil,
        idToken: String? = nil
    ) {
        self.accessToken = accessToken
        self.expiresOn = expiresOn
        self.refreshToken = refreshToken
        self.idToken = idToken
    }
}
