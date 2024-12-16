import Foundation

public struct PolicyAttachment: Equatable {

    public var id: Int
    public var name: String
    public var fileSize: Int

    public init(id: Int, name: String, fileSize: Int) {
        self.id = id
        self.name = name
        self.fileSize = fileSize
    }
}
