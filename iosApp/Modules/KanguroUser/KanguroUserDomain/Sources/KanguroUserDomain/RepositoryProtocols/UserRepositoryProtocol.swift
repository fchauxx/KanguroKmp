import Foundation
import KanguroSharedDomain

public protocol UserRepositoryProtocol {
    
    func login(parameters: UserLoginParameters,
               completion: @escaping((Result<User,RequestError>) -> Void))
    
    func updateRefreshToken(_ parameters: RefreshTokenParameters,
                            completion: @escaping((Result<Token,RequestError>) -> Void))
    
    func createAccountDeletionOrder(_ parameters: Bool,
                       completion: @escaping((Result<Void,RequestError>) -> Void))
    
    func getBankAccount(completion: @escaping (Result<BankAccount,RequestError>) -> Void)
    
    func updateBankAccount(_ parameters: BankAccount,
                           completion: @escaping ((Result<Void,RequestError>) -> Void))
    
    func updateFirebaseToken(_ parameters: FirebaseTokenParameters,
                             completion: @escaping ((Result<Void,RequestError>) -> Void))
    
    func createOtpSendRequest(completion: @escaping (Result<Void,RequestError>) -> Void)
    
    func getOtpValidation(_ parameters: CodeValidationDataParameters,
                          completion: @escaping (Result<Void, RequestError>) -> Void)
    
    func getIsUserAccessOk(userId: String, completion: @escaping (Result<Void, RequestError>) -> Void)
    
    func patchCharity(_ parameters: UserDonationCause,
                      completion: @escaping(Result<Void, RequestError>) -> Void)
}
