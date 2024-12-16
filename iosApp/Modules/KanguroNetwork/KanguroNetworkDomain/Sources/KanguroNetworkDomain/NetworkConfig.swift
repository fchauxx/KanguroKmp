import Foundation

public struct NetworkConfig {
    public var apiKey: String
    public var contentType: String
    public var acceptLanguage: String
    public var userAgent: String
    public var appVersion: String
    public var baseURL: String

    public init(
        apiKey: String,
        contentType: String = "application/json",
        acceptLanguage: String,
        userAgent: String,
        appVersion: String,
        baseURL: String
    ) {
        self.apiKey = apiKey
        self.contentType = contentType
        self.acceptLanguage = acceptLanguage
        self.userAgent = userAgent
        self.appVersion = appVersion
        self.baseURL = baseURL
    }
}
