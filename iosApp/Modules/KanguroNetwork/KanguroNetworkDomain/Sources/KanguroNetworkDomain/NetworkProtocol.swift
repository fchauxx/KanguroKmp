import Foundation

public protocol NetworkProtocol {
    func request<T: Decodable,
                 Parameters: Encodable,
                 E: Decodable>(endpoint: Endpoint,
                               method: HTTPVerb,
                               parameters: Parameters,
                               responseType: T.Type,
                               errorType: E.Type,
                               completion: @escaping ((RequestResponse<T, E>) -> Void))
    
    func request<T: Decodable,
                 E: Decodable>(endpoint: Endpoint,
                               method: HTTPVerb,
                               responseType: T.Type,
                               errorType: E.Type,
                               completion: @escaping ((RequestResponse<T, E>) -> Void))
    
    func request<E: Decodable>(endpoint: Endpoint,
                               method: HTTPVerb,
                               responseStringUse: Bool,
                               responseType: String.Type,
                               errorType: E.Type,
                               completion: @escaping ((RequestResponse<String, E>) -> Void))
    
    func request<E: Decodable>(endpoint: Endpoint,
                               method: HTTPVerb,
                               errorType: E.Type,
                               completion: @escaping ((RequestEmptyResponse<E>) -> Void))
    
    func request<Parameters: Encodable,
                 E: Decodable>(endpoint: Endpoint,
                               method: HTTPVerb,
                               parameters: Parameters,
                               errorType: E.Type,
                               completion: @escaping ((RequestEmptyResponse<E>) -> Void))
    
    func download<E: Codable & Error>(endpoint: Endpoint,
                                      fileName: String,
                                      fileExt: String,
                                      errorType: E.Type,
                                      completion: @escaping ((RequestResponse<Data, E>) -> Void))
    
    func upload<T: Decodable,
                E: Decodable>(endpoint: Endpoint,
                              file: Data,
                              withName: String,
                              filename: String,
                              mimeType: String,
                              responseType: T.Type,
                              errorType: E.Type,
                              completion: @escaping ((RequestResponse<T, E>) -> Void))
    
    func uploadToExternalURL<E: Decodable>(url: String,
                                           headers: [String: String],
                                           file: Data,
                                           withName: String,
                                           filename: String?,
                                           mimeType: String?,
                                           errorType: E.Type,
                                           completion: @escaping ((RequestEmptyResponse<E>) -> Void))
}
