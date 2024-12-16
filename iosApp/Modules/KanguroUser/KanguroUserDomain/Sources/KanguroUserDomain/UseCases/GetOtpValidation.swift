import Foundation
import KanguroSharedDomain

public final class GetOtpValidation: GetOtpValidationUseCaseProtocol {

    private let userRepo: UserRepositoryProtocol
    
    public init(userRepo: UserRepositoryProtocol) {
        self.userRepo = userRepo
    }
    
    public func execute(_ parameters: CodeValidationDataParameters, completion: @escaping (Result<Void, KanguroSharedDomain.RequestError>) -> Void) {
        userRepo.getOtpValidation(parameters) { result in
            completion(result)
        }
    }
}
