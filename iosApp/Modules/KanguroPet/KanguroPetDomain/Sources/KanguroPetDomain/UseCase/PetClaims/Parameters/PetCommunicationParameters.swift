import Foundation

public struct PetCommunicationParameters {

    public var id: String?
    public var message: String?
    public var files: [String] = []

    public init(
        id: String? = nil,
        message: String? = nil,
        files: [String] = []
    ) {
        self.id = id
        self.message = message
        self.files = files
    }
}
