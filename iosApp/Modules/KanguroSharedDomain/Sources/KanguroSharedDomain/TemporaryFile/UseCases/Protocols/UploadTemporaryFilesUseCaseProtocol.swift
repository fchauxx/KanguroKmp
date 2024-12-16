import Foundation

public protocol UploadTemporaryFilesUseCaseProtocol {
    
    func execute(
        url: String,
        headers: [String: String],
        tempFile: Data,
        completion: @escaping ((Result<Void, RequestError>) -> Void)
    )
}
