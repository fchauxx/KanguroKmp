import Foundation
import KanguroSharedDomain
public protocol UpdateFirebaseTokenUseCaseProtocol {
    
    func execute(_ parameters: FirebaseTokenParameters,
                 completion: @escaping ((Result<Void,RequestError>) -> Void))
}
