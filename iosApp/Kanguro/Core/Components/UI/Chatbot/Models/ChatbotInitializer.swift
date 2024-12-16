import Foundation

struct ChatbotData {
    
    // MARK: - Stored Properties
    var type: SessionType?
    var currentPetId: Int?
    var sessionId: String?
    var chatInteractionStep: ChatInteractionStep?
    
    // MARK: - Computed Properties
    var isStarterSetup: Bool {
        guard let actions = chatInteractionStep?.actions else { return false }
        return (actions.isEmpty &&
                (type == .AdditionalInformation || type == .PetMedicalHistoryDocuments))
    }
    var currentMessagesCount: Int {
        return chatInteractionStep?.messages.count ?? 0
    }
    var chatInteractionStepSessionId: String {
        return chatInteractionStep?.sessionId ?? ""
    }
    var isCommunicationType: Bool {
        return type == .Communication
    }
    var isPetInfoFinished: Bool {
        guard let step = chatInteractionStep else { return false }
        return ((type == .AdditionalInformation || type == .PetMedicalHistoryDocuments)
                && step.actions.isEmpty
                && step.type == .Finish)
    }
    var isChatStepEmpty: Bool {
        return chatInteractionStep?.messages.isEmpty ?? true
    }
    var chatType: ChatbotInteractionType {
        return chatInteractionStep?.type ?? .TextInput
    }
}
