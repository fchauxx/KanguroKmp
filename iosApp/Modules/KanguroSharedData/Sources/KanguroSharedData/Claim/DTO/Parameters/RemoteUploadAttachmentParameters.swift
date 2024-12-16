import Foundation

public struct RemoteUploadAttachmentParameters: Codable {
    public var files: String
    public var fileInputType: RemoteClaimFileInputType

    public init(files: String, fileInputType: RemoteClaimFileInputType) {
        self.files = files
        self.fileInputType = fileInputType
    }
}
