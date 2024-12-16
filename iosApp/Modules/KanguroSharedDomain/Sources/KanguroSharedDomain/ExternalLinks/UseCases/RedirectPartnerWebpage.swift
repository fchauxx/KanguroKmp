import Foundation

public final class RedirectPartnerWebpage: RedirectPartnerWebpageUseCaseProtocol {

    private let externalLinksRepo: ExternalLinksRepositoryProtocol

    public init(externalLinksRepo: ExternalLinksRepositoryProtocol) {
        self.externalLinksRepo = externalLinksRepo
    }

    public func execute(
        partnerName: String,
        parameters: UserIdParameters,
        completion: @escaping ((Result<URLRedirect, RequestError>) -> Void)
    ) {
        externalLinksRepo.redirectToPartnerWebsite(partnerName: partnerName, parameters: parameters) { result in
            completion(result)
        }
    }
}
