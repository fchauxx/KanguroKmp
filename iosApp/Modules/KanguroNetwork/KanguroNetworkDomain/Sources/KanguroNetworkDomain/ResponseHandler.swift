import Foundation
import KanguroSharedDomain

public struct ResponseHandler {
    public static func handle<T>(
        mapper: some ModelMapper,
        response: RequestResponse<some Codable, NetworkRequestError>,
        completion: ((Result<T,RequestError>) -> Void)
    ) {
        switch response {
        case .success(let items):
            do {
                let domainItems: T = try type(of: mapper).map(items)
                completion(.success(domainItems))
            } catch {
                completion(.failure(RequestError(errorType: .couldNotMap, 
                                                 errorMessage: "Could not map")))
            }
        case .failure(let error):
            completion(.failure(RequestErrorMapper.map(error)))
        case .customError(let error):
            completion(.failure(RequestErrorMapper.map(error)))
        }
    }
}

public final class RequestErrorMapper {
    public static func map(_ input: NetworkRequestError) -> RequestError {
        if input.isTokenError == true {
            return RequestError(errorType: .tokenError, 
                                errorMessage: input.error)
        }
        guard let statusCode = input.statusCode else {
            return RequestError(errorType: .undefined, 
                                errorMessage: input.error)
        }
        switch statusCode {
        case 500...599:
            return RequestError(errorType: .serverError, 
                                errorMessage: input.error)
        case 400:
            return RequestError(errorType: .invalidRequest, 
                                errorMessage: input.error)
        case 401:
            return RequestError(errorType: .notAllowed, 
                                errorMessage: input.error)
        case 404:
            return RequestError(errorType: .notFound, 
                                errorMessage: input.error)
        case 408:
            return RequestError(errorType: .timeout, 
                                errorMessage: input.error)
        case 409:
            return RequestError(errorType: .conflict, 
                                errorMessage: input.error)
        default:
            return RequestError(errorType: .undefined, 
                                errorMessage: input.error)
        }
    }
}
