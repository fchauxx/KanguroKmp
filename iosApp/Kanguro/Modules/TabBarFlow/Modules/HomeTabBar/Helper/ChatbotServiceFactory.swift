import KanguroSharedData
import KanguroSharedDomain
import KanguroStorageDomain
import KanguroNetworkDomain
import KanguroPetDomain
import KanguroPetData

public struct ChatbotServiceFactory {

    public static func makeRemote() -> ChatbotModule {
        return ChatbotModule()
    }

    public static func makeLocal(
        delegate: ChatbotDelegate,
        mainDispatcher: Dispatcher,
        network: NetworkProtocol,
        secureStorage: SecureStorage
    ) -> LocalChatbotModule {
        let policyRepo = PolicyRepository(network: network)
        let claimRepo = PetClaimRepository(network: network)
        let remindersRepo = RemindersRepository(network: network)
        return LocalChatbotModule(
            delegate: delegate,
            mainDispatcher: mainDispatcher,
            type: SessionType.NewClaim,
            keychain: secureStorage,
            getPetsService: GetPets(petRepo: PetRepository(network: network)),
            getPoliciesService: GetPolicies(policyRepo: policyRepo),
            getCoverages: GetCoverages(policyRepo: policyRepo),
            getRemindersService: GetReminders(remindersRepo: remindersRepo),
            createClaimService: CreatePetClaim(claimRepo: claimRepo),
            createDocumentsService: CreatePetDocuments(claimRepo: claimRepo)
        )
    }
}
