import Foundation
import KanguroSharedDomain

public protocol PetUpdateFeedbackUseCaseProtocol {
    func execute(
        _ parameters: PetClaimParameters,
        feedback: PetFeedbackDataParameters,
        completion: @escaping ((Result<Void, RequestError>) -> Void)
    )
}
