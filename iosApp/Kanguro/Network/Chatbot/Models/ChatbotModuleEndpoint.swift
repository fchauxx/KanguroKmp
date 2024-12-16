import Foundation
import KanguroSharedData
import KanguroNetworkDomain

enum ChatbotModuleEndpoint: Endpoint {
    
    case nextStep
    case sessionStart
    case getSession
    case getPetId(sessionId: String)
    
    var path: String {
        switch self {
        case .nextStep:
            return "chatbot/next-step"
        case .sessionStart:
            return "chatbot/session/start"
        case .getSession:
            return "chatbot/session"
        case .getPetId(let sessionId):
            return "chatbot/session/\(sessionId)"
        }
    }
}
