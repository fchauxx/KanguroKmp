import Foundation
import KanguroSharedDomain

public struct GetAdditionalParties: GetAdditionalPartiesUseCaseProtocol {
    
    private let rentersPolicyRepo: RentersPolicyRepositoryProtocol
    
    public init(rentersPolicyRepo: RentersPolicyRepositoryProtocol) {
        self.rentersPolicyRepo = rentersPolicyRepo
    }
    
    public func execute(policyId: String,
                        completion: @escaping ((Result<[AdditionalPartie], RequestError>) -> Void)) {
        rentersPolicyRepo.getAdditionalParties(policyId: policyId) { result in
            completion(result)
        }
    }
}
