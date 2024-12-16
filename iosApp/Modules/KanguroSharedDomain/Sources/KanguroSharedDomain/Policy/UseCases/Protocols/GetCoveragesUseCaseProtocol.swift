import Foundation

public protocol GetCoveragesUseCaseProtocol {
    func execute(
        _ parameters: PolicyCoverageParameters,
        completion: @escaping(
            (Result<[CoverageData], RequestError>) -> Void)
        )
}
