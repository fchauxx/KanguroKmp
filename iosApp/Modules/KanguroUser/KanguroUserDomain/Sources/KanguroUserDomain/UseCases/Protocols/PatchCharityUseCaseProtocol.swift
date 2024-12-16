import Foundation
import KanguroSharedDomain

public protocol PatchCharityUseCaseProtocol {
    
    func execute(_ parameters: UserDonationCause,
                 completion: @escaping(Result<Void, RequestError>) -> Void)
}
