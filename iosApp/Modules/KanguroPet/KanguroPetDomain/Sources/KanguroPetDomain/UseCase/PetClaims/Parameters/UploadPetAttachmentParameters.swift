import Foundation
import KanguroSharedDomain

public struct UploadPetAttachmentParameters {

    public var files: String
    public var fileInputType: ClaimFileInputType

    public init(files: String, fileInputType: ClaimFileInputType) {
        self.files = files
        self.fileInputType = fileInputType
    }
}
