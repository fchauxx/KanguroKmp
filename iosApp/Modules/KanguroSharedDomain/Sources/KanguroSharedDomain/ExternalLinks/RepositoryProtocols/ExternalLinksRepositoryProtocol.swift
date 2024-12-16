import Foundation

public protocol ExternalLinksRepositoryProtocol {

    func redirectToPartnerWebsite(
        partnerName: String,
        parameters: UserIdParameters,
        completion: @escaping((Result<URLRedirect, RequestError>) -> Void)
    )
}
