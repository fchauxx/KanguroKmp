import Foundation

public struct UploadTemporaryFile: UploadTemporaryFilesUseCaseProtocol {
    
    private let temporaryFileRepo: TemporaryFileRepositoryProtocol
    
    public init(temporaryFileRepo: TemporaryFileRepositoryProtocol) {
        self.temporaryFileRepo = temporaryFileRepo
    }
    
    public func execute(url: String,
                        headers: [String: String],
                        tempFile: Data,
                        completion: @escaping ((Result<Void, RequestError>) -> Void)) {
        temporaryFileRepo.uploadTemporaryFile(url: url, 
                                              headers: headers,
                                              tempFile: tempFile) { result in
            completion(result)
        }
    }
}
