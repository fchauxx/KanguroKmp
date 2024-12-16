import Foundation

public final class GetTermsOfService: GetTermsOfServiceUseCaseProtocol {
    
    private let termsOfServiceRepo: TermsOfServiceRepositoryProtocol
    
    public init(termsOfServiceRepo: TermsOfServiceRepositoryProtocol) {
        self.termsOfServiceRepo = termsOfServiceRepo
    }
    
    public func execute(parameters: TermsOfServiceParameters,
                        completion: @escaping ((Result<Data, RequestError>) -> Void)) {
        termsOfServiceRepo.getTermsOfService(parameters: parameters) { result in
            completion(result)
        }
    }
}
