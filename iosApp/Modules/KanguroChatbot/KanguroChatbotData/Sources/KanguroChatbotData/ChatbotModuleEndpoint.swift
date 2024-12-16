import KanguroNetworkDomain

public enum ChatbotModuleEndpoint: Endpoint {

    case session
    case step(sessionId: String)

    private var apiVersion: String {
        "v2"
    }

    public var path: String {
        switch self {
        case .session:
            return "\(apiVersion)/Chatbot"
        case .step(let sessionId):
            return "\(apiVersion)/Chatbot/\(sessionId)"
        }
    }
}
