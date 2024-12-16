import Foundation
import Resolver
import UIKit
import KanguroSharedData
import KanguroSharedDomain
import KanguroStorageDomain
import KanguroNetworkDomain
import KanguroNetworkData
import KanguroUserDomain
import KanguroUserData

class NetworkModuleDependencies {

    // MARK: - Stored Properties
    var network: NetworkProtocol?
    var interceptor: CoreInterceptor?
    var networkConfig: NetworkConfig?
    var environment: Environment?

    // MARK: - Initializers
    init(
        coreNetwork: NetworkProtocol? = nil,
        coreInterceptor: CoreInterceptor? = nil,
        networkConfig: NetworkConfig? = nil
    ) {
        self.network = coreNetwork
        self.interceptor = coreInterceptor
        self.networkConfig = networkConfig
    }
}

// MARK: - ModuleDependencies
extension NetworkModuleDependencies: ModuleDependencies {

    func setupDependencies() {
        let environment = self.environment ?? Environment()
        let userDefaults: Storage = Resolver.resolve()
        let savedLangString: String? = userDefaults.get(key: "preferredLanguage")
        let appLanguage: Language = Language(rawValue: savedLangString ?? "en") ?? .English

        let networkConfig = self.networkConfig ?? NetworkConfig(
            apiKey: environment.apiKey,
            acceptLanguage: appLanguage.rawValue,
            userAgent: "\(UIDevice.current.name) \(UIDevice.current.systemVersion)",
            appVersion: Bundle.main.object(forInfoDictionaryKey: "CFBundleShortVersionString") as? String ?? "",
            baseURL: environment.baseURL
        )
        Resolver.register { networkConfig }

        let refreshTokenPaths: [String] = [
            UserModuleEndpoint.refreshToken.path,
            UserModuleEndpoint.login.path,
            ResetPasswordModuleEndpoint.resetPassword.path
        ]

        let coreInterceptor = self.interceptor ?? CoreInterceptor(
            refreshTokenPaths: refreshTokenPaths,
            getUser: {
                let getLocalUserService: GetLocalUser = Resolver.resolve()
                guard let user = try? getLocalUserService.execute().get() else {
                    return nil
                }
                return user
            },
            getRefreshToken: { param, completion in
                let refreshToken: RefreshTokenUseCaseProtocol = Resolver.resolve()
                let parameters = RefreshTokenParameters(refreshToken: param.refreshToken)
                refreshToken.execute(parameters) { result in
                    switch result {
                    case .success(let token):
                        let getLocalUserService: GetLocalUser = Resolver.resolve()
                        guard var user = try? getLocalUserService.execute().get() else {
                            return completion(.failure(NetworkRequestError(statusCode: 500)))
                        }

                        let remoteToken = TokenMapper.reverseMap(token)
                        user.accessToken = remoteToken.accessToken
                        user.expiresOn = token.expiresOn
                        user.refreshToken = remoteToken.refreshToken
                        user.idToken = remoteToken.idToken
                        let updateLocalUserService: UpdateLocalUser = Resolver.resolve()
                        updateLocalUserService.execute(user) { result in
                            switch result {
                            case .success:
                                completion(.success(remoteToken))
                            case .failure(let error):
                                completion(.failure(NetworkRequestError(statusCode: 500, error: error.errorMessage)))
                            }
                        }
                    case .failure(let error):
                        completion(.failure(NetworkRequestError(statusCode: 500, error: error.errorMessage)))
                    }
                }
            })
        Resolver.register { coreInterceptor }

        let coreNetwork = self.network ?? AFNetwork(
            networkConfig: networkConfig,
            interceptor: coreInterceptor,
            decoder: JSONDecoder(),
            notificationCenter: NotificationCenter.default
        )
        Resolver.register { coreNetwork }

        self.network = coreNetwork
    }
}
