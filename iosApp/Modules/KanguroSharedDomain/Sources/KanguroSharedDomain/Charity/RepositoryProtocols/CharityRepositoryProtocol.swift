import Foundation

public protocol CharityRepositoryProtocol {
    
    func getCharities(completion: @escaping((Result<[DonationCause], RequestError>) -> Void))
}
