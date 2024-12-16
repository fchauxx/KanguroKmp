import Foundation

public struct RemoteUploadedDocumentParameters: Codable {

    public var fileIds: [Int]

    public init(fileIds: [Int]) {
        self.fileIds = fileIds
    }
}
