import Foundation

public protocol GetCloudDocumentUseCaseProtocol {
    func execute(
        completion: @escaping ((Result<CloudDocument, RequestError>) -> Void)
    )
}
