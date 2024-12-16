import Foundation
import KanguroSharedDomain

public protocol GetPersonalPropertiesUseCaseProtocol {
    
    func execute(
        completion: @escaping ((Result<PersonalProperty, RequestError>) -> Void)
    )
}
