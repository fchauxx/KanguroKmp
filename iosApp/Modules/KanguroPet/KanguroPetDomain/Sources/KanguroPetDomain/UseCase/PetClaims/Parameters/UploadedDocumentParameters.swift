import Foundation

public struct UploadedDocumentParameters {

    public var fileIds: [Int]

    public init(fileIds: [Int]) {
        self.fileIds = fileIds
    }
}
