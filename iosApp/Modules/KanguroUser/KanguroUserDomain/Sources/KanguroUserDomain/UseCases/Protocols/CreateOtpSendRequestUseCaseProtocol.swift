import Foundation
import KanguroSharedDomain

public protocol CreateOtpSendRequestUseCaseProtocol {
    
    func execute(completion: @escaping (Result<Void,RequestError>) -> Void)
}
