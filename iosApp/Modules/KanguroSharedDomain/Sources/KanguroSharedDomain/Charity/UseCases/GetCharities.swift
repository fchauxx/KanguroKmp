import Foundation

public final class GetCharities: GetCharitiesUseCaseProtocol {
    
    private let charityRepo: CharityRepositoryProtocol
    
    public init(charityRepo: CharityRepositoryProtocol) {
        self.charityRepo = charityRepo
    }
    
    public func execute(completion: @escaping((Result<[DonationCause], RequestError>) -> Void)) {
        charityRepo.getCharities { result in
            completion(result)
        }
    }
}
