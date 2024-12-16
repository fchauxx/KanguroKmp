import Foundation

public final class UpdateProfile: UpdateProfileUseCaseProtocol {
    
    private let profileRepo: ProfileRepositoryProtocol
    
    public init(profileRepo: ProfileRepositoryProtocol) {
        self.profileRepo = profileRepo
    }
    
    public func execute(parameters: ProfileParameters,
                        completion: @escaping ((Result<Void, RequestError>) -> Void)) {
        profileRepo.updateProfile(parameters: parameters) { result in
            completion(result)
        }
    }
}
