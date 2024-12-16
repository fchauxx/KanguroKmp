import Foundation

struct NextStepParameters: Codable {
    
    let sessionId: String
    var value: String? = ""
    var action: ChatbotAction? = .none
}
