import Foundation
import KanguroSharedDomain
import KanguroNetworkDomain
import KanguroUserDomain
import KanguroNetworkData

public class RemoteUserRepository: UserRepositoryProtocol {

    private let network: NetworkProtocol
    
    public init(network: NetworkProtocol) {
        self.network = network
    }
    
    public func login(parameters: UserLoginParameters,
                      completion: @escaping((Result<User, RequestError>) -> Void)) {
        network.request(endpoint: UserModuleEndpoint.login,
                        method: .POST,
                        parameters: RemoteUserLoginParameters(email: parameters.email, 
                                                              password: parameters.password),
                        responseType: RemoteUser.self,
                        errorType: NetworkRequestError.self) { response in
            ResponseHandler.handle(mapper: UserMapper(), 
                                   response: response,
                                   completion: completion)
        }
    }
    
    public func updateRefreshToken(_ parameters: RefreshTokenParameters,
                                   completion: @escaping ((Result<Token, RequestError>) -> Void)) {
        let remoteParameters = RemoteRefreshTokenParameters(refreshToken: parameters.refreshToken)
        network.request(endpoint: UserModuleEndpoint.refreshToken,
                        method: .POST,
                        parameters: remoteParameters,
                        responseType: RemoteToken.self,
                        errorType: NetworkRequestError.self) { response in
            ResponseHandler.handle(mapper: TokenMapper(), 
                                   response: response,
                                   completion: completion)
        }
    }
    
    public func createAccountDeletionOrder(_ parameters: Bool,
                                           completion: @escaping((Result<Void, RequestError>) -> Void)) {
        network.request(endpoint: UserModuleEndpoint.deleteAccount,
                        method: .PUT,
                        parameters: parameters,
                        errorType: NetworkRequestError.self) { response in
            switch response {
            case .success:
                completion(.success(()))
            case .failure(let error), .customError(let error):
                completion(.failure(RequestErrorMapper.map(error)))
            }
        }
    }
    
    public func getBankAccount(completion: @escaping (Result<BankAccount, RequestError>) -> Void) {
        network.request(endpoint: UserModuleEndpoint.bankAccount,
                        method: .GET,
                        responseType: RemoteBankAccount.self,
                        errorType: NetworkRequestError.self) { response in
            ResponseHandler.handle(mapper: BankAccountMapper(), 
                                   response: response,
                                   completion: completion)
        }
    }
    
    public func updateBankAccount(_ parameters: BankAccount,
                                  completion: @escaping ((Result<Void, RequestError>) -> Void)) {
        network.request(endpoint: UserModuleEndpoint.bankAccount,
                        method: .PUT,
                        parameters: BankAccountMapper.reverseMap(input: parameters),
                        errorType: NetworkRequestError.self) { response in
            switch response {
            case .success:
                completion(.success(()))
            case .failure(let error), .customError(let error):
                completion(.failure(RequestErrorMapper.map(error)))
            }
        }
    }
    
    public func updateFirebaseToken(_ parameters: FirebaseTokenParameters,
                                    completion: @escaping ((Result<Void, RequestError>) -> Void)) {
        network.request(endpoint: UserModuleEndpoint.firebaseToken,
                        method: .PUT,
                        parameters: RemoteFirebaseTokenParameters(firebaseToken: parameters.firebaseToken, 
                                                                  uuid: parameters.uuid),
                        errorType: NetworkRequestError.self) { response in
            switch response {
            case .success:
                completion(.success(()))
            case .failure(let error), .customError(let error):
                completion(.failure(RequestErrorMapper.map(error)))
            }
        }
    }
    
    public func createOtpSendRequest(completion: @escaping (Result<Void, RequestError>) -> Void) {
        network.request(endpoint: UserModuleEndpoint.otpSendRequest,
                        method: .POST,
                        errorType: NetworkRequestError.self) { response in
            switch response {
            case .success:
                completion(.success(()))
            case .failure(let error), .customError(let error):
                completion(.failure(RequestErrorMapper.map(error)))
            }
        }
    }
    
    public func getOtpValidation(_ parameters: CodeValidationDataParameters,
                                 completion: @escaping (Result<Void, RequestError>) -> Void) {
        network.request(endpoint: UserModuleEndpoint.otpValidation(email: parameters.email,
                                                                   code: parameters.code),
                        method: .GET,
                        responseType: Bool.self,
                        errorType: NetworkRequestError.self) { response in
            switch response {
            case .success(let isValidCode):
                guard isValidCode else {
                    completion(.failure(RequestError(errorType: .notAllowed, 
                                                     errorMessage: "Invalid code")))
                    return
                }
                completion(.success(()))
            case .failure(let error), .customError(let error):
                completion(.failure(RequestErrorMapper.map(error)))
            }
        }
    }
    
    public func getIsUserAccessOk(userId: String, 
                                  completion: @escaping (Result<Void, RequestError>) -> Void) {

        network.request(endpoint: UserModuleEndpoint.hasAccessBlocked(userID: userId),
                        method: .GET,
                        responseType: Bool.self,
                        errorType: NetworkRequestError.self) { response in
            switch response {
            case .success(let isUserBlocked):
                guard isUserBlocked else {
                    completion(.success(()))
                    return
                }
                completion(.failure(RequestError(errorType: .forbidden, 
                                                 errorMessage: "Unauthorized user access.")))
            case .failure(let error), .customError(let error):
                completion(.failure(RequestErrorMapper.map(error)))
            }
        }
    }
    
    public func patchCharity(_ parameters: UserDonationCause,
                             completion: @escaping (Result<Void, RequestError>) -> Void) {
        
        guard let cause = parameters.cause?.rawValue,
              let remoteDonationType = RemoteUserDonationType(rawValue: cause) else {
            return completion(.failure(RequestError(errorType: .couldNotMap, 
                                                    errorMessage: "Could not map")))
        }
        network.request(endpoint: UserModuleEndpoint.syncUserDonation,
                        method: .PATCH,
                        parameters: RemoteUserDonationCause(userId: parameters.userId,
                                                            charityId: parameters.charityId,
                                                            title: parameters.title,
                                                            cause: remoteDonationType),
                        errorType: NetworkRequestError.self) { response in
            switch response {
            case .success:
                completion(.success(()))
            case .failure(let error), .customError(let error):
                completion(.failure(RequestErrorMapper.map(error)))
            }
        }
    }
}

extension RemoteUserRepository: GetUserRepositoryProtocol {

    public func getUser() -> Result<User, RequestError> {
        assertionFailure("not implemented")
        return .failure(RequestError(errorType: .timeout, 
                                     errorMessage: "Request timeout"))
    }

    public func getUser(completion: @escaping (Result<User, RequestError>) -> Void) {
        network.request(endpoint: UserModuleEndpoint.getUser,
                        method: .GET,
                        responseType: RemoteUser.self,
                        errorType: NetworkRequestError.self) { response in
            ResponseHandler.handle(mapper: UserMapper(), 
                                   response: response,
                                   completion: completion)
        }
    }
}
