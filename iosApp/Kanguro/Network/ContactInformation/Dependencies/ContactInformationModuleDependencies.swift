import Foundation
import Resolver
import KanguroSharedDomain
import KanguroSharedData
import KanguroNetworkDomain

class ContactInformationModuleDependencies {

    // MARK: - Stored Properties
    var contactInformationRepository: ContactInformationRepository?
    var getContactInformationService: GetContactInformationUseCaseProtocol?

    // MARK: - Initializers
    public init(contactInformationRepository: ContactInformationRepository? = nil,
                contactInformationService: GetContactInformationUseCaseProtocol? = nil) {
        self.contactInformationRepository = contactInformationRepository
        self.getContactInformationService = contactInformationService
    }
}

// MARK: - ModuleDependencies
extension ContactInformationModuleDependencies: ModuleDependencies {

    func setupDependencies() {
        let network: NetworkProtocol = Resolver.resolve()
        let contactInformationRepository = self.contactInformationRepository ?? ContactInformationRepository(network: network)
        Resolver.register { contactInformationRepository }

        let getContactInformationService = self.getContactInformationService ?? GetContactInformation(contactInformationRepo: contactInformationRepository)
        Resolver.register { getContactInformationService }

    }
}
