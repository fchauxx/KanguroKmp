import Foundation
import Resolver
import KanguroSharedDomain
import KanguroSharedData
import KanguroNetworkDomain
import KanguroPetData
import KanguroPetDomain

class CloudDocumentModuleDependencies {
    
    // MARK: - Stored Properties
    var getClaimDocumentByPolicyAndClaimService: GetClaimDocumentByPolicyAndClaimUseCaseProtocol?
    var getCloudDocumentService: (any GetCloudDocumentUseCaseProtocol)?
    var getCloudDocumentByPolicyService: GetCloudDocumentsByPolicyUseCaseProtocol?

    // MARK: - Initializers
    public init(
        getClaimDocumentByPolicyAndClaimService: GetClaimDocumentByPolicyAndClaimUseCaseProtocol? = nil,
        getCloudDocumentService: (any GetCloudDocumentUseCaseProtocol)? = nil,
        getCloudDocumentByPolicyService: GetCloudDocumentsByPolicyUseCaseProtocol? = nil
    ) {
        self.getClaimDocumentByPolicyAndClaimService = getClaimDocumentByPolicyAndClaimService
        self.getCloudDocumentService = getCloudDocumentService
        self.getCloudDocumentByPolicyService = getCloudDocumentByPolicyService
    }
}

// MARK: - ModuleDependencies
extension CloudDocumentModuleDependencies: ModuleDependencies {
    
    func setupDependencies() {
        let network: NetworkProtocol = Resolver.resolve()
        let petCloudDocumentRepository: any CloudDocumentRepositoryProtocol = PetCloudDocumentRepository(network: network) 
        Resolver.register { petCloudDocumentRepository }
        let getClaimDocumentByPolicyAndClaimService = GetClaimDocumentByPolicyAndClaim(cloudDocumentRepo: petCloudDocumentRepository)
        Resolver.register { getClaimDocumentByPolicyAndClaimService as GetClaimDocumentByPolicyAndClaimUseCaseProtocol }
        let getPetCloudDocumentService = GetPetCloudDocument(petCloudDocumentRepo: petCloudDocumentRepository)
        Resolver.register { getPetCloudDocumentService }
        let getCloudDocumentByPolicyService = GetCloudDocumentByPolicy(cloudDocumentRepo: petCloudDocumentRepository)
        Resolver.register { getCloudDocumentByPolicyService as GetCloudDocumentsByPolicyUseCaseProtocol }
    }
}
