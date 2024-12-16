import Foundation
import Alamofire
import KanguroNetworkDomain
import KanguroSharedDomain

public class AFNetwork {
    
    // MARK: - Dependencies
    var networkConfig: NetworkConfig
    var interceptor: RequestInterceptor
    var notificationCenter: NotificationCenter
    var decoder: JSONDecoder
    
    // MARK: - Computed Properties
    var baseHeaders: HTTPHeaders {
        HTTPHeaders(arrayLiteral: HTTPHeader(name: "api_key", value: networkConfig.apiKey),
                    HTTPHeader(name: "Content-Type", value: networkConfig.contentType),
                    HTTPHeader(name: "Accept-Language", value: networkConfig.acceptLanguage),
                    HTTPHeader(name: "User-Agent", value: networkConfig.userAgent),
                    HTTPHeader(name: "App-Version", value: networkConfig.appVersion)
        )
    }
    var secondaryHeaders: HTTPHeaders {
        HTTPHeaders(arrayLiteral: HTTPHeader(name: "api_key", value: networkConfig.apiKey),
                    HTTPHeader(name: "Content-Type", value: "multipart/form-data"),
                    HTTPHeader(name: "Accept-Language", value: networkConfig.acceptLanguage),
                    HTTPHeader(name: "User-Agent", value: networkConfig.userAgent),
                    HTTPHeader(name: "App-Version", value: networkConfig.appVersion)
        )
    }
    
    var baseUrl: String {
        networkConfig.baseURL
    }
    
    public init(
        networkConfig: NetworkConfig,
        interceptor: RequestInterceptor,
        decoder: JSONDecoder,
        notificationCenter: NotificationCenter
    ) {
        self.networkConfig = networkConfig
        self.interceptor = interceptor
        self.decoder = decoder
        self.notificationCenter = notificationCenter
        notificationCenter.addObserver(self, selector: #selector(handleNotification), name: .languageUpdate, object: nil)
    }
    
    deinit {
        NotificationCenter.default.removeObserver(self, name: .languageUpdate, object: nil)
    }
    
    public func updateLanguage(acceptLanguage: String) {
        self.networkConfig.acceptLanguage = acceptLanguage
    }
    
    @objc func handleNotification(_ notification: NSNotification) {
        if let language = notification.userInfo?["language"] as? String {
            updateLanguage(acceptLanguage: language)
        }
    }
}

extension AFNetwork: NetworkProtocol {
    
    public func request<T, Parameters, E>(endpoint: Endpoint,
                                          method: HTTPVerb,
                                          parameters: Parameters,
                                          responseType: T.Type,
                                          errorType: E.Type,
                                          completion: @escaping ((RequestResponse<T, E>) -> Void)) where T : Decodable, T : Encodable, Parameters : Encodable, E : Decodable, E : Encodable, E : Error {
        let headers = baseHeaders
        
        AF.request(baseUrl.appending(endpoint.path),
                   method: HTTPMethod(rawValue: method.rawValue),
                   parameters: parameters,
                   encoder: JSONParameterEncoder.default,
                   headers: headers,
                   interceptor: interceptor) { [weak self] request in
            guard self != nil else { return }
            request.timeoutInterval = 30
        }.validate()
            .responseDecodable(of: T.self) { [weak self] response in
                guard let self = self else { return }
                completion(self.serializeResponse(response: response,
                                                  responseType: responseType,
                                                  errorType: errorType))
            }
    }
    
    public func request<T, E>(endpoint: Endpoint,
                              method: HTTPVerb,
                              responseType: T.Type,
                              errorType: E.Type,
                              completion: @escaping ((RequestResponse<T, E>) -> Void)) where T : Decodable, T : Encodable, E : Decodable, E : Encodable, E : Error {
        let headers = baseHeaders
        
        AF.request(baseUrl.appending(endpoint.path),
                   method: HTTPMethod(rawValue: method.rawValue),
                   headers: headers,
                   interceptor: interceptor) { [weak self] request in
            guard self != nil else { return }
            request.timeoutInterval = 30
        }.validate()
            .responseDecodable(of: T.self) { [weak self] response in
                guard let self = self else { return }
                completion(self.serializeResponse(response: response,
                                                  responseType: responseType,
                                                  errorType: errorType))
            }
    }
    
    public func request<E>(endpoint: Endpoint,
                           method: HTTPVerb,
                           responseStringUse: Bool,
                           responseType: String.Type,
                           errorType: E.Type,
                           completion: @escaping ((RequestResponse<String, E>) -> Void)) where E : Decodable, E : Encodable, E : Error {
        let headers = baseHeaders
        
        AF.request(baseUrl.appending(endpoint.path),
                   method: HTTPMethod(rawValue: method.rawValue),
                   headers: headers,
                   interceptor: interceptor) { [weak self] request in
            guard self != nil else { return }
            request.timeoutInterval = 30
        }.validate()
            .responseString { response in
                switch response.result {
                case .success(let value):
                    completion(.success(value))
                case .failure(let error):
                    completion(.failure(NetworkRequestError(statusCode: error.responseCode,
                                                            error: error.errorDescription,
                                                            data: nil,
                                                            isTokenError: false)))
                }
            }
    }
    
    public func request<E>(endpoint: Endpoint,
                           method: HTTPVerb,
                           errorType: E.Type,
                           completion: @escaping ((RequestEmptyResponse<E>) -> Void)) where E : Decodable, E : Encodable, E : Error {
        let headers = baseHeaders
        
        AF.request(baseUrl.appending(endpoint.path),
                   method: HTTPMethod(rawValue: method.rawValue),
                   headers: headers,
                   interceptor: interceptor) { [weak self] request in
            guard self != nil else { return }
            request.timeoutInterval = 30
        }.validate()
            .response { [weak self] response in
                guard let self = self else { return }
                completion(self.serializeResponse(response: response,
                                                  errorType: errorType))
            }
    }
    
    public func request<Parameters, E>(endpoint: Endpoint,
                                       method: HTTPVerb,
                                       parameters: Parameters,
                                       errorType: E.Type,
                                       completion: @escaping ((RequestEmptyResponse<E>) -> Void)) where Parameters : Encodable, E : Decodable, E : Encodable, E : Error {
        let headers = baseHeaders
        
        AF.request(baseUrl.appending(endpoint.path),
                   method: HTTPMethod(rawValue: method.rawValue),
                   parameters: parameters,
                   encoder: JSONParameterEncoder.default,
                   headers: headers,
                   interceptor: interceptor) { [weak self] request in
            guard self != nil else { return }
            request.timeoutInterval = 30
        }.validate()
            .response { [weak self] response in
                guard let self = self else { return }
                completion(self.serializeResponse(response: response,
                                                  errorType: errorType))
            }
    }
    
    public func download<E>(endpoint: Endpoint,
                            fileName: String,
                            fileExt: String,
                            errorType: E.Type,
                            completion: @escaping ((RequestResponse<Data, E>) -> Void)) where E : Decodable, E : Encodable, E : Error {
        let directory = FileManager.default.urls(for: .cachesDirectory, in: .userDomainMask)[0]
        let pdfUrl = directory.appendingPathComponent("\(fileName).\(fileExt)")
        let destination: DownloadRequest.Destination = { (_, _) in
            return (pdfUrl, [.removePreviousFile, .createIntermediateDirectories])
        }
        
        let headers = baseHeaders
        
        AF.download(baseUrl.appending(endpoint.path),
                    method: HTTPMethod.get,
                    headers: headers,
                    interceptor: interceptor,
                    to: destination).responseData { [weak self] response in
            guard self != nil else { return }
            switch response.result {
            case .success(let data):
                completion(.success(data))
            case .failure(let error):
                completion(.failure(NetworkRequestError(statusCode: error.responseCode,
                                                        error: error.errorDescription,
                                                        data: nil)))
            }
        }
    }
    
    public func upload<T, E>(endpoint: Endpoint,
                             file: Data,
                             withName: String,
                             filename: String,
                             mimeType: String,
                             responseType: T.Type,
                             errorType: E.Type,
                             completion: @escaping ((RequestResponse<T, E>) -> Void))  where T : Codable, E : Codable, E : Error {
        let headers = secondaryHeaders
        
        AF.upload(multipartFormData: { multipartFormData in
            multipartFormData.append(file, withName: withName, fileName: filename, mimeType: mimeType)
        },
                  to: baseUrl.appending(endpoint.path),
                  method: .post,
                  headers: headers,
                  interceptor: interceptor)
        .responseDecodable(of: T.self) { [weak self] response in
            guard let self else { return }
            completion(self.serializeResponse(response: response,
                                              responseType: responseType,
                                              errorType: errorType))
        }
    }
    
    public func uploadToExternalURL<E>(url: String,
                                          headers: [String: String],
                                          file: Data,
                                          withName: String,
                                          filename: String? = nil,
                                          mimeType: String? = nil,
                                          errorType: E.Type,
                                          completion: @escaping ((RequestEmptyResponse<E>) -> Void)) where E : Decodable, E : Encodable, E : Error {
        var localBaseHeaders: HTTPHeaders {
            return HTTPHeaders(headers.compactMap({
                HTTPHeader(name: $0.key, value: $0.value)
            }))
        }
        
        AF.upload(
            multipartFormData: { multipartFormData in
                multipartFormData.append(
                    file,
                    withName: withName,
                    fileName: filename,
                    mimeType: mimeType
                )
            },
            to: url,
            method: .put,
            headers: localBaseHeaders
        )
        .response { [weak self] response in
            guard let self else { return }
            completion(self.serializeResponse(response: response, errorType: errorType))
        }
    }
}

// MARK: - Private Methods
extension AFNetwork {
    
    private func serializeResponse<T: Decodable,
                                   E: Codable & Error>(response: DataResponse<T, AFError>,
                                                       responseType: T.Type,
                                                       errorType: E.Type) -> RequestResponse<T, E> {
        switch response.result {
        case .success(let response):
            return .success(response)
        case .failure(let error):
            guard let data = response.data,
                  let serializedError = try? decoder.decode(errorType, from: data) else {
                return .failure(NetworkRequestError(statusCode: error.responseCode,
                                                    error: error.errorDescription,
                                                    data: response.data))
            }
            return .customError(serializedError)
        }
    }
    
    private func serializeResponse<E: Codable & Error>(response: AFDataResponse<Data?>,
                                                       errorType: E.Type) -> RequestEmptyResponse<E> {
        switch response.result {
        case .success:
            return .success
        case .failure(let error):
            guard let data = response.data,
                  let serializedError = try? decoder.decode(errorType, from: data) else {
                return .failure(NetworkRequestError(statusCode: error.responseCode,
                                                    error: error.errorDescription,
                                                    data: response.data))
            }
            return .customError(serializedError)
        }
    }
}
