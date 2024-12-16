import Foundation
import KanguroSharedDomain
import KanguroPetDomain
@testable import Kanguro

final class GetPetsUseCaseMock: GetPetsUseCaseProtocol {
    typealias PetsCompletion = ((Result<[Pet], RequestError>) -> Void)

    var callCount: Int {
        completions.count
    }
    var completions: [PetsCompletion] = []

    func execute(
        completion: @escaping (
            (Result<[Pet],RequestError>) -> Void)
    ) {
        completions.append(completion)
    }

    func completeSuccessfully(with policies: [Pet], at index: Int? = nil) {
        guard !completions.isEmpty else { return }
        let completionIndex: Int = index ?? completions.count - 1
        completions[completionIndex](.success(policies))
    }

    func completeWithError(_ index: Int? = nil) {
        guard !completions.isEmpty else { return }
        let completionIndex: Int = index ?? completions.count - 1
        completions[completionIndex](.failure(RequestError(errorType: .couldNotMap, errorMessage: "serverError.default".localized)))
    }
}
