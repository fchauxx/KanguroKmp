import Foundation
import KanguroSharedDomain

public struct GetPersonalProperties: GetPersonalPropertiesUseCaseProtocol {
    
    private let rentersPolicyRepo: RentersPolicyRepositoryProtocol
    
    public init(rentersPolicyRepo: RentersPolicyRepositoryProtocol) {
        self.rentersPolicyRepo = rentersPolicyRepo
    }
    
    public func execute(completion: @escaping ((Result<PersonalProperty,
                                                RequestError>) -> Void)) {
        rentersPolicyRepo.getRenterPersonalPropertyLimits { result in
            completion(result)
        }
    }
}
