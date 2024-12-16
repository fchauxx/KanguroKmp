import Foundation

public protocol GetCharitiesUseCaseProtocol {
    
    func execute(completion: @escaping((Result<[DonationCause], RequestError>) -> Void))
}
