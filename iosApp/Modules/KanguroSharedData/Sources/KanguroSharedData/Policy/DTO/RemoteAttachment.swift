import Foundation

public struct RemoteAttachment: Codable, Equatable {

    public var id: Int
    public var fileName: String
    public var fileSize: Int

    public init(
        id: Int,
        fileName: String,
        fileSize: Int
    ) {
        self.id = id
        self.fileName = fileName
        self.fileSize = fileSize
    }
}
