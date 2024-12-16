import Foundation

public protocol CreateTemporaryFilesUseCaseProtocol {
    
    func execute(
        tempFile: Data,
        completion: @escaping((Result<TemporaryFile, RequestError>) -> Void)
    )
}
