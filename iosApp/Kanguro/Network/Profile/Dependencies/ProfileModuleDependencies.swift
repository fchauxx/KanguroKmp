import Foundation
import Resolver
import KanguroSharedDomain
import KanguroSharedData
import KanguroNetworkDomain

class ProfileModuleDependencies {
    
    // MARK: - Stored Properties
    var profileRepository: ProfileRepositoryProtocol?
    var updateProfileService: UpdateProfileUseCaseProtocol?
    
    // MARK: - Initializers
    public init(profileRepository: ProfileRepositoryProtocol? = nil,
                updateProfileService: UpdateProfileUseCaseProtocol? = nil) {
        self.profileRepository = profileRepository
        self.updateProfileService = updateProfileService
    }
}

// MARK: - ModuleDependencies
extension ProfileModuleDependencies: ModuleDependencies {
    
    func setupDependencies() {
        let network: NetworkProtocol = Resolver.resolve()
        let profileRepository = self.profileRepository ?? ProfileRepository(network: network)
        Resolver.register { profileRepository }
        
        let updateProfileService = self.updateProfileService ?? UpdateProfile(profileRepo: profileRepository)
        Resolver.register { updateProfileService }
    }
}
