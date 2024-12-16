import Foundation

public protocol TemporaryFileRepositoryProtocol {
    
    func createTemporaryFile(tempFile: Data,
                             completion: @escaping ((Result<TemporaryFile, RequestError>) -> Void))
    
    func getTemporaryFile(completion: @escaping ((Result<TemporaryFile, RequestError>) -> Void))
    
    func uploadTemporaryFile(url: String,
                             headers: [String: String],
                             tempFile: Data,
                             completion: @escaping ((Result<Void, RequestError>) -> Void))
}
