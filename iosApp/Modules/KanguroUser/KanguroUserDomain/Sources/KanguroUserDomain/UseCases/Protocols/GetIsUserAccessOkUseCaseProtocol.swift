import Foundation
import KanguroSharedDomain

public protocol GetIsUserAccessOkUseCaseProtocol {
    
    func execute(userId: String, completion: @escaping (Result<Void, RequestError>) -> Void)
}
