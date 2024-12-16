import Foundation

public enum ChatbotExternalFlow: Equatable {
    
    case scheduledItem(policyId: String)
    case uploadFile
}
