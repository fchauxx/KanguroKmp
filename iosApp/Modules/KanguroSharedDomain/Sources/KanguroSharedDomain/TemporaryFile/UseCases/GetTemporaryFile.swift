import Foundation

public struct GetTemporaryFile: GetTemporaryFilesUseCaseProtocol {
    
    private let temporaryFileRepo: TemporaryFileRepositoryProtocol

    public init(temporaryFileRepo: TemporaryFileRepositoryProtocol) {
        self.temporaryFileRepo = temporaryFileRepo
    }

    public func execute(completion: @escaping ((Result<TemporaryFile, RequestError>) -> Void)) {
        temporaryFileRepo.getTemporaryFile { result in
            completion(result)
        }
    }
}
