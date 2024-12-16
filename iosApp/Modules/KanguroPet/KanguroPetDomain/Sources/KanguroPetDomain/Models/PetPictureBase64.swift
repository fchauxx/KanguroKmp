import Foundation

public struct PetPictureBase64 {
    public var fileInBase64: String
    public var fileExtension: String

    public init(
        fileInBase64: String,
        fileExtension: String
    ) {
        self.fileInBase64 = fileInBase64
        self.fileExtension = fileExtension
    }
}
