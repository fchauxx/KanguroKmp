import UIKit
import Resolver
import KanguroSharedDomain
import KanguroStorageDomain
import KanguroUserDomain
import KanguroPetDomain
import KanguroFeatureFlagDomain

final class ChatInteractionData {
    
    // MARK: - Dependencies
    @LazyInjected var keychain: SecureStorage
    @LazyInjected var getLocalUserService: GetLocalUser
    @LazyInjected var getFeatureFlagBoolService: GetFeatureFlagBoolUseCaseProtocol

    // MARK: - Stored Properties
    var currentId: ChatInteractionId
    var pets: [Pet]
    var chosenPet: Pet? = nil
    var hasPreventive: Bool = false
    var claimSummary: ChatSummary?
   
    // MARK: - Computed Properties
    var user: User? {
        guard let user: User = try? getLocalUserService.execute().get() else { return nil }
        return user
    }
    var userName: String {
        guard let user: User = try? getLocalUserService.execute().get(),
              let givenName = user.givenName else { return "" }
        if let surname = user.surname {
            return "\(givenName) \(surname)"
        } else {
            return givenName
        }
    }
    var petOptions: [ChatAction] {
        var petOptions: [ChatAction] = []
        for (index, pet) in pets.enumerated() {
            petOptions.append(ChatAction(order: index,
                                         label: pet.name ?? "",
                                         value: String(pet.id),
                                         action: .PetSelected,
                                         isMainAction: true,
                                         userResponseMessage: nil))
        }
        return petOptions
    }
    var petClaimCauseActions: [ChatAction] {
        if hasPreventive == true {
            return [ChatAction(order: 1,
                               label: "chatbot.action.accident.label".localized,
                               value: "chatbot.action.accident.label".localized,
                               action: .Accident,
                               isMainAction: nil,
                               userResponseMessage: nil),
                    ChatAction(order: 2,
                               label: "chatbot.action.illness.label".localized,
                               value: "chatbot.action.illness.label".localized,
                               action: .Illness,
                               isMainAction: nil,
                               userResponseMessage: nil),
                    ChatAction(order: 3,
                               label: "chatbot.action.preventive.label".localized,
                               value: "chatbot.action.other.label".localized,
                               action: .Other,
                               isMainAction: nil,
                               userResponseMessage: nil)]
        } else {
            return [ChatAction(order: 1,
                               label: "chatbot.action.accident.label".localized,
                               value: "chatbot.action.accident.label".localized,
                               action: .Accident,
                               isMainAction: nil,
                               userResponseMessage: nil),
                    ChatAction(order: 2,
                               label: "chatbot.action.illness.label".localized,
                               value: "chatbot.action.illness.label".localized,
                               action: .Illness,
                               isMainAction: nil,
                               userResponseMessage: nil)]
        }
    }
    var chosenPetName: String {
        guard let petName = chosenPet?.name else { return "Pet" }
        return petName
    }
        
    // MARK: - Life Cycle
    init(pets: [Pet], currentId: ChatInteractionId) {
        self.pets = pets
        self.currentId = currentId
    }
    
    func getInitialMessages(chatbotType: SessionType) -> ChatInteraction? {
        
        switch chatbotType {
        case .NewClaim:
            return ChatInteraction(id: .SelectPet,
                                   chatInteractionStep: ChatInteractionStep(type: .ButtonList,
                                                                            orientation: .Vertical,
                                                                            actions: actions,
                                                                            messages: messages,
                                                                            sessionId: ""),
                                   nextInteractionId: .PledgeOfHonour)
        default:
            return nil
        }
    }
}

// MARK: - Private Methods
extension ChatInteractionData {
    
    func setupSummary() -> String {
        
        guard let claimSummary else { return "" }
        return "{\"pet\":\"\(claimSummary.pet)\",\"claim\":\"\(claimSummary.claim)\",\"date\":\"\(claimSummary.date.getFormatted(format: "yyyy-MM-dd'T'HH:mm:ss", timezone: TimeZone(abbreviation: "UTC")!))+00:00\",\"attachments\":\(claimSummary.attachments),\"amount\":\(claimSummary.amount.description),\"paymentMethod\":\"\("summary.bankAccount".localized)\"}"
    }
}

// MARK: - Public Methods
extension ChatInteractionData {
    
    func updateStep() {
        self.currentId = nextChatInteraction.nextInteractionId
    }
    
    func updateSummary(summary: ChatSummary) {
        claimSummary = summary
    }
    
    func updatePreventiveStatus(_ status: Bool) {
        hasPreventive = status
    }
}
