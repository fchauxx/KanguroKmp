import Foundation
import Resolver
import KanguroPetDomain
import KanguroPetData
import KanguroNetworkDomain

class VetModuleDependencies {

    // MARK: - Stored Properties
    var vetRepository: VeterinariansRepositoryProtocol?
    var getVeterinariansService: GetVeterinariansUseCaseProtocol?

    // MARK: - Initializers
    init(
        vetRepository: VeterinariansRepositoryProtocol? = nil,
        getVeterinariansService: GetVeterinariansUseCaseProtocol? = nil
    ) {
        self.vetRepository = vetRepository
        self.getVeterinariansService = getVeterinariansService
    }
}

// MARK: - ModuleDependencies
extension VetModuleDependencies: ModuleDependencies {

    func setupDependencies() {
        let network: NetworkProtocol = Resolver.resolve()
        let vetRepo: VeterinariansRepositoryProtocol = VeterinariansRepository(network: network)
        Resolver.register { vetRepo }

        let getVeterinariansService = self.getVeterinariansService ?? GetVeterinarians(vetRepo: vetRepo)
        Resolver.register { getVeterinariansService }
    }
}
