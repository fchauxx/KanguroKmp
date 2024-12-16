import Foundation
import KanguroSharedDomain
import KanguroNetworkDomain

public class TemporaryFileRepository: TemporaryFileRepositoryProtocol {
    
    private let network: NetworkProtocol
    
    public init(network: NetworkProtocol) {
        self.network = network
    }
    
    public func createTemporaryFile(tempFile: Data,
                                    completion: @escaping ((Result<TemporaryFile, RequestError>) -> Void)) {
        
        network.upload(endpoint: TemporaryFileModuleEndpoint.createTemporaryFile,
                       file: tempFile,
                       withName: "file",
                       filename: "file.jpg",
                       mimeType: "image/jpeg",
                       responseType: RemoteTemporaryFile.self,
                       errorType: NetworkRequestError.self) { response in
            ResponseHandler.handle(mapper: TemporaryFileMapper(),
                                   response: response,
                                   completion: completion)
        }
    }
    
    public func uploadTemporaryFile(url: String,
                                    headers: [String: String],
                                    tempFile: Data,
                                    completion: @escaping ((Result<Void, RequestError>) -> Void)) {
        
        network.uploadToExternalURL(url: url,
                                    headers: headers,
                                    file: tempFile,
                                    withName: "file",
                                    filename: nil,
                                    mimeType: "application/octet-stream",
                                    errorType: NetworkRequestError.self) { response in
            switch response {
            case .success:
                completion(.success(()))
            case .failure(let error), .customError(let error):
                completion(.failure(RequestErrorMapper.map(error)))
            }
        }
    }
    
    
    public func getTemporaryFile(completion: @escaping ((Result<TemporaryFile, RequestError>) -> Void)) {
        network.request(endpoint: TemporaryFileModuleEndpoint.getTemporaryFile,
                        method: .GET,
                        responseType: RemoteTemporaryFile.self,
                        errorType: NetworkRequestError.self) { response in
            ResponseHandler.handle(mapper: TemporaryFileMapper(),
                                   response: response,
                                   completion: completion)
        }
    }
}
