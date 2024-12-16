import Foundation
import KanguroNetworkDomain
import Alamofire
import KanguroSharedDomain
import KanguroUserDomain
import Sentry

public class CoreInterceptor: RequestInterceptor {

    public typealias TokenRequest = (RequestResponse<RemoteToken, NetworkRequestError>) -> Void

    var refreshTokenPaths: [String] = []
    var getUser: () -> KanguroUserDomain.User?
    var getRefreshToken: ((RemoteRefreshTokenParameters, @escaping TokenRequest) -> Void)

    // MARK: - Stored Properties
    private let retryLimit = 3
    private let retryDelay: TimeInterval = 3

    public init(
        refreshTokenPaths: [String],
        getUser: @escaping () -> KanguroUserDomain.User?,
        getRefreshToken: @escaping ((RemoteRefreshTokenParameters,
                                     @escaping TokenRequest) -> Void)
    ) {
        self.refreshTokenPaths = refreshTokenPaths
        self.getUser = getUser
        self.getRefreshToken = getRefreshToken
    }

    public func adapt(_ urlRequest: URLRequest, 
                      for session: Session,
                      completion: @escaping (Result<URLRequest, Error>) -> Void) {

        if isTokenNotNeeded(urlRequest: urlRequest) {
            completion(.success(urlRequest))
            return
        }

        let user: KanguroUserDomain.User? = getUser()

        guard let user = user,
              let expirationDate = user.expiresOn,
              let refreshToken = user.refreshToken else {
            DispatchQueue.main.async {
                SentrySDK.capture(message: "User \(user?.email ?? "") with token not valid or nil. URL: \(urlRequest.url?.path ?? "")")
            }
            forceUserLogout()
            completion(.failure(NetworkRequestError(statusCode: 401,
                                                    error: "Unauthorized request")))
            return
        }

        if Date() < expirationDate {
            guard let userAccessToken = user.accessToken else { return }
            completion(.success(self.setToken(urlRequest: urlRequest, token: userAccessToken)))
        } else {
            let parameters = RemoteRefreshTokenParameters(refreshToken: refreshToken)


            getRefreshToken(parameters) { [weak self] response in
                guard let self = self else { return }
                switch response {
                case .success(let newToken):
                    guard let newAccessToken = newToken.accessToken else {
                        completion(.failure(NetworkRequestError(statusCode: 400,
                                                                error: "Access token not found",
                                                                isTokenError: true)))
                        forceUserLogout()
                        return
                    }
                    completion(.success(self.setToken(urlRequest: urlRequest, 
                                                      token: newAccessToken)))
                case .failure(var error), .customError(var error):
                    error.isTokenError = true
                    completion(.failure(error))
                }
            }
        }
    }

    public func retry(_ request: Request, 
                      for session: Session,
                      dueTo error: Error,
                      completion: @escaping (RetryResult) -> Void) {

        if case .requestAdaptationFailed(let errorType) = error.asAFError {
            if let error = errorType as? NetworkRequestError, error.isTokenError == true {
                if request.retryCount >= retryLimit {
                    forceUserLogout()
                    completion(.doNotRetry)
                } else {
                    completion(.retryWithDelay(retryDelay))
                }
            }
        } else {
            completion(.doNotRetry)
        }
    }
}

// MARK: - Private Methods
extension CoreInterceptor {

    private func setToken(urlRequest: URLRequest, token: String) -> URLRequest {
        var urlRequest = urlRequest
        urlRequest.addValue("Bearer \(token)", forHTTPHeaderField: "Authorization")
        return urlRequest
    }

    private func isTokenNotNeeded(urlRequest: URLRequest) -> Bool {
        guard !refreshTokenPaths.isEmpty,
              let requestPath = urlRequest.url?.path else { return false }
        for path in refreshTokenPaths {
            if requestPath.contains(path) {
                return true
            }
        }
        return false
    }

    private func forceUserLogout() {
        NotificationCenter.default.post(name: Notification.Name(rawValue: "logoutUser"),
                                        object: self)
    }
}
