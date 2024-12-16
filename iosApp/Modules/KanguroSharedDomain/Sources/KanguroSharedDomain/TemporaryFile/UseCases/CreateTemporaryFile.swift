import Foundation

public struct CreateTemporaryFile: CreateTemporaryFilesUseCaseProtocol {
    
    private let temporaryFileRepo: TemporaryFileRepositoryProtocol

    public init(temporaryFileRepo: TemporaryFileRepositoryProtocol) {
        self.temporaryFileRepo = temporaryFileRepo
    }

    public func execute(tempFile: Data,
                        completion: @escaping ((Result<TemporaryFile, RequestError>) -> Void)) {
        temporaryFileRepo.createTemporaryFile(tempFile: tempFile) { result in
            completion(result)
        }
    }
}
