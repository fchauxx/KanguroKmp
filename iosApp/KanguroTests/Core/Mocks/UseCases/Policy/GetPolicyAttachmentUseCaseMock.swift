import Foundation
@testable import Kanguro
import KanguroSharedDomain

final class GetPolicyAttachmentUseCaseMock: GetPolicyAttachmentUseCaseProtocol {
    typealias DataCompletion = ((Result<Data, RequestError>) -> Void)

    var callCount: Int {
        completions.count
    }
    var completions: [(PolicyAttachmentParameters, DataCompletion)] = []

    func execute(
        _ parameters: PolicyAttachmentParameters,
        completion: @escaping ((Result<Data, RequestError>) -> Void)
    ) {
        completions.append((parameters, completion))
    }

    func completeSuccessfully(_ index: Int? = nil) {
        guard !completions.isEmpty else { return }
        let completionIndex: Int = index ?? completions.count - 1
        completions[completionIndex].1(.success(Data()))
    }

    func completeWithError(_ index: Int? = nil) {
        guard !completions.isEmpty else { return }
        let completionIndex: Int = index ?? completions.count - 1
        completions[completionIndex].1(.failure(RequestError(errorType: .couldNotMap, errorMessage: "serverError.default".localized)))
    }
}
