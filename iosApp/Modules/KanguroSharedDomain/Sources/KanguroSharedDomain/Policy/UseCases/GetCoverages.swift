import Foundation

public final class GetCoverages: GetCoveragesUseCaseProtocol {
    
    private let policyRepo: PolicyRepositoryProtocol
    
    public init(policyRepo: PolicyRepositoryProtocol) {
        self.policyRepo = policyRepo
    }
    
    public func execute(_ parameters: PolicyCoverageParameters, completion: @escaping ((Result<[CoverageData], RequestError>) -> Void)) {
        policyRepo.getCoverages(parameters) { result in
            completion(result)

        }
    }
}
