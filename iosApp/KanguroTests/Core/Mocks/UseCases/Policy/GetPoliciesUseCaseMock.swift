import Foundation
@testable import Kanguro
import KanguroSharedDomain

final class GetPoliciesUseCaseMock: GetPoliciesUseCaseProtocol {

    typealias PoliciesCompletion = ((Result<[Policy],RequestError>) -> Void)

    var callCount: Int {
        completions.count
    }

    var completions: [PoliciesCompletion] = []
    var failure: RequestError?
    var success: [Policy]?

    init(
        shouldFailWith: RequestError? = nil,
        shouldSucceedWith: [Policy]? = nil
    ) {
        self.failure = shouldFailWith
        self.success = shouldSucceedWith
    }

    func execute(
        completion: @escaping (
            (Result<[KanguroSharedDomain.Policy],RequestError>) -> Void)
    ) {
        completions.append(completion)
        if let failure = failure {
            completion(.failure(failure))
        } else {
            if let success = success {
                completion(.success(success))
            }
        }
    }

    func completeSuccessfully(with policies: [Policy], at index: Int? = nil) {
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
