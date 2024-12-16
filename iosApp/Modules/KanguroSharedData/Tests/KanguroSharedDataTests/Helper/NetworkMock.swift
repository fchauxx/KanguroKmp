import Foundation
import KanguroSharedData
import KanguroNetworkDomain

final class NetworkMock: NetworkProtocol {
    
    var completions: [Any] = []
    
    func request<T, Parameters, E>(endpoint: Endpoint,
                                   method: HTTPVerb,
                                   parameters: Parameters,
                                   responseType: T.Type,
                                   errorType: E.Type, completion: @escaping ((RequestResponse<T, E>) -> Void)) where T : Decodable, T : Encodable, Parameters : Encodable, E : Decodable, E : Encodable, E : Error {
        completions.append(completion)
    }
    
    func request<T, E>(endpoint: Endpoint,
                       method: HTTPVerb,
                       responseType: T.Type,
                       errorType: E.Type,
                       completion: @escaping ((RequestResponse<T, E>) -> Void)) where T : Decodable, T : Encodable, E : Decodable, E : Encodable, E : Error {
        completions.append(completion)
    }
    
    func request<E>(endpoint: Endpoint,
                    method: HTTPVerb,
                    errorType: E.Type,
                    completion: @escaping ((RequestEmptyResponse<E>) -> Void)) where E : Decodable, E : Encodable, E : Error {
        completions.append(completion)
    }
    
    func request<E>(endpoint: Endpoint,
                    method: HTTPVerb,
                    responseStringUse: Bool,
                    responseType: String.Type,
                    errorType: E.Type,
                    completion: @escaping ((RequestResponse<String, E>) -> Void)) where E : Decodable, E : Encodable, E : Error {
        completions.append(completion)
    }
    
    func request<Parameters, E>(endpoint: Endpoint,
                                method: HTTPVerb,
                                parameters: Parameters,
                                errorType: E.Type,
                                completion: @escaping ((RequestEmptyResponse<E>) -> Void)) where Parameters : Encodable, E : Decodable, E : Encodable, E : Error {
        completions.append(completion)
    }
    
    func download<E>(endpoint: Endpoint,
                     fileName: String,
                     fileExt: String,
                     errorType: E.Type,
                     completion: @escaping ((RequestResponse<Data, E>) -> Void)) where E : Decodable, E : Encodable, E : Error {
        completions.append(completion)
    }
    
    func upload<T: Decodable,
                E: Decodable>(endpoint: Endpoint,
                              file: Data,
                              withName: String,
                              filename: String,
                              mimeType: String,
                              responseType: T.Type,
                              errorType: E.Type,
                              completion: @escaping ((RequestResponse<T, E>) -> Void)) {
        completions.append(completion)
    }
    
    
    func uploadToExternalURL<T, E>(url: String, file: Data, responseType: T.Type, errorType: E.Type, completion: @escaping ((KanguroNetworkDomain.RequestEmptyResponse<E>) -> Void)) where T : Decodable, E : Decodable, E : Encodable, E : Error {
        completions.append(completion)
    }

    func uploadToExternalURL<E: Decodable>(url: String,
                                           headers: [String: String],
                                           file: Data,
                                           withName: String,
                                           filename: String?,
                                           mimeType: String?,
                                           errorType: E.Type,
                                           completion: @escaping ((RequestEmptyResponse<E>) -> Void)) {
        completions.append(completion)
    }
}
