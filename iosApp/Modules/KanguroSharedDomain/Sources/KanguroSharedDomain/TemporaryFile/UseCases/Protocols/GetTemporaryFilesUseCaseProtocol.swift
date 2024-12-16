import Foundation

public protocol GetTemporaryFilesUseCaseProtocol {
    
    func execute(
        completion: @escaping((Result<TemporaryFile, RequestError>) -> Void)
    )
}
