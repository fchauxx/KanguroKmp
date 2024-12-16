import Foundation
import KanguroSharedDomain

public protocol GetOtpValidationUseCaseProtocol {
    
    func execute(_ parameters: CodeValidationDataParameters,
                 completion: @escaping (Result<Void, RequestError>) -> Void)
}
