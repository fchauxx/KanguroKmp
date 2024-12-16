import Foundation

public struct RemoteLanguageParameters: Codable {

    public var language: String

    public init(language: String) {
        self.language = language
    }
}
