import Foundation

public struct TemporaryFile {
    
    public var filename: String?
    public var blobType: String?
    public var url: String?
    public var id: Int?

    public init(filename: String? = nil,
                blobType: String? = nil,
                url: String? = nil,
                id: Int? = nil) {
        self.filename = filename
        self.blobType = blobType
        self.url = url
        self.id = id
    }
}
