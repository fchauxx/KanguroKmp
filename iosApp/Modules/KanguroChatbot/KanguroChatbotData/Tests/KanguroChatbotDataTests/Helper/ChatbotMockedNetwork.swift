import KanguroNetworkDomain
import Foundation

final class ChatbotMockedNetwork: NetworkProtocol {

    var shouldFail: Bool = false
    var defaultError: NetworkRequestError = NetworkRequestError(statusCode: 415)
    var result: Codable?
    var expectedData: Data = Data()

    func request<T, Parameters, E>(
        endpoint: Endpoint,
        method: HTTPVerb,
        parameters: Parameters,
        responseType: T.Type,
        errorType: E.Type,
        completion: @escaping ((RequestResponse<T, E>) -> Void)
    ) where T : Decodable, T : Encodable, Parameters : Encodable, E : Decodable, E : Encodable, E : Error {
        guard !shouldFail else {
            completion(.failure(defaultError))
            return
        }
        guard let result: T = result as? T else {
            completion(.failure(NetworkRequestError(statusCode: 500)))
            return
        }
        completion(.success(result))
    }

    func request<T, E>(
        endpoint: Endpoint,
        method: HTTPVerb,
        responseType: T.Type,
        errorType: E.Type,
        completion: @escaping ((RequestResponse<T, E>) -> Void)
    ) where T : Decodable, T : Encodable, E : Decodable, E : Encodable, E : Error {
        guard !shouldFail else {
            completion(.failure(defaultError))
            return
        }
        guard let result: T = result as? T else {
            completion(.failure(NetworkRequestError(statusCode: 500)))
            return
        }
        completion(.success(result))
    }

    func request<E>(endpoint: Endpoint,
                               method: HTTPVerb,
                               responseStringUse: Bool,
                               responseType: String.Type,
                               errorType: E.Type,
                               completion: @escaping ((RequestResponse<String, E>) -> Void)) where E : Decodable, E : Encodable, E : Error {
        guard !shouldFail else {
            completion(.failure(defaultError))
            return
        }
        completion(.success(""))
    }

    func request<E>(
        endpoint: Endpoint,
        method: HTTPVerb,
        errorType: E.Type,
        completion: @escaping ((RequestEmptyResponse<E>) -> Void)
    ) where E : Decodable, E : Encodable, E : Error {
        guard !shouldFail else {
            completion(.failure(defaultError))
            return
        }
        completion(.success)
    }

    func request<Parameters, E>(
        endpoint: Endpoint,
        method: HTTPVerb,
        parameters: Parameters,
        errorType: E.Type,
        completion: @escaping ((RequestEmptyResponse<E>) -> Void)
    ) where Parameters : Encodable, E : Decodable, E : Encodable, E : Error {
        guard !shouldFail else {
            completion(.failure(defaultError))
            return
        }
        completion(.success)
    }

    func download<E>(
        endpoint: Endpoint,
        fileName: String,
        fileExt: String,
        errorType: E.Type,
        completion: @escaping ((RequestResponse<Data, E>) -> Void)
    ) where E : Decodable, E : Encodable, E : Error {
        guard !shouldFail else {
            completion(.failure(defaultError))
            return
        }
        completion(.success(expectedData))
    }

    func upload<T: Decodable,
                E: Decodable>(endpoint: Endpoint,
                              file: Data,
                              withName: String,
                              filename: String,
                              mimeType: String,
                              responseType: T.Type,
                              errorType: E.Type,
                              completion: @escaping ((RequestResponse<T, E>) -> Void))
        where T : Decodable, T : Encodable, E : Decodable, E : Encodable, E : Error {
            guard !shouldFail else {
                completion(.failure(defaultError))
                return
            }
            guard let result: T = result as? T else {
                completion(.failure(NetworkRequestError(statusCode: 500)))
                return
            }
            completion(.success(result))
        }


    func uploadToExternalURL<E: Decodable>(url: String,
                                           headers: [String: String],
                                           file: Data,
                                           withName: String,
                                           filename: String?,
                                           mimeType: String?,
                                           errorType: E.Type,
                                           completion: @escaping ((RequestEmptyResponse<E>) -> Void)) {
        completion(.success)
    }
}

