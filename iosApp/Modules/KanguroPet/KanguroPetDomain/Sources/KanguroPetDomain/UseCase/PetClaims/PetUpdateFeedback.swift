import Foundation
import KanguroSharedDomain

public final class PetUpdateFeeback: PetUpdateFeedbackUseCaseProtocol {

    private let claimRepo: PetClaimRepositoryProtocol

    public init(claimRepo: PetClaimRepositoryProtocol) {
        self.claimRepo = claimRepo
    }

    public func execute(
        _ parameters: PetClaimParameters,
        feedback: PetFeedbackDataParameters,
        completion: @escaping ((Result<Void, RequestError>) -> Void)
    ) {
        claimRepo.updateFeedback(parameters, feedback: feedback) { result in
            completion(result)
        }
    }
}
