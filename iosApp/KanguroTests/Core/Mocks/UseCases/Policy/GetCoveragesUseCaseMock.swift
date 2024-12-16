import Foundation
import KanguroSharedDomain
@testable import Kanguro

final class GetCoveragesUseCaseMock: GetCoveragesUseCaseProtocol {

    typealias CoveragesCompletion = ((Result<[CoverageData], RequestError>) -> Void)

    var callCount: Int {
        completions.count
    }
    var completions: [(PolicyCoverageParameters, CoveragesCompletion)] = []

    func execute(_ parameters: PolicyCoverageParameters, completion: @escaping ((Result<[CoverageData], RequestError>) -> Void)) {
        completions.append((parameters, completion))
    }

    func completeSuccessfully(with coverages: [CoverageData], at index: Int? = nil) {
        guard !completions.isEmpty else { return }
        let completionIndex: Int = index ?? completions.count - 1
        completions[completionIndex].1(.success(coverages))
    }

    func completeWithError(_ index: Int? = nil) {
        guard !completions.isEmpty else { return }
        let completionIndex: Int = index ?? completions.count - 1
        completions[completionIndex].1(.failure(RequestError(errorType: .couldNotMap, errorMessage: "serverError.default".localized)))
    }
}
