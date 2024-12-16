import Foundation
import Resolver
import KanguroSharedDomain
import KanguroSharedData
import KanguroNetworkDomain
import KanguroPetDomain
import KanguroPetData

class PetModuleDependencies {
    
    // MARK: - Stored Properties
    var petRepository: PetRepositoryProtocol?
    var getPetService: GetPetUseCaseProtocol?
    var getPetsService: GetPetsUseCaseProtocol?
    var updatePetPictureService: UpdatePetPictureUseCaseProtocol?
    
    // MARK: - Initializers
    init(
        petRepository: PetRepositoryProtocol? = nil,
        getPetService: GetPetUseCaseProtocol? = nil,
        getPetsService: GetPetsUseCaseProtocol? = nil,
        updatePetPictureService: UpdatePetPictureUseCaseProtocol? = nil
    ) {
        self.petRepository = petRepository
        self.getPetService = getPetService
        self.getPetsService = getPetsService
        self.updatePetPictureService = updatePetPictureService
    }
}

// MARK: - ModuleDependencies
extension PetModuleDependencies: ModuleDependencies {
    
    func setupDependencies() {
        let network: NetworkProtocol = Resolver.resolve()
        let petRepo: PetRepositoryProtocol = PetRepository(network: network)
        Resolver.register { petRepo }

        let getPetService = self.getPetService ?? GetPet(petRepo: petRepo)
        Resolver.register { getPetService }

        let getPetsService = self.getPetsService ?? GetPets(petRepo: petRepo)
        Resolver.register { getPetsService }

        let updatePetPictureService = self.updatePetPictureService ?? UpdatePetPicture(petRepo: petRepo)
        Resolver.register { updatePetPictureService }
    }
}
