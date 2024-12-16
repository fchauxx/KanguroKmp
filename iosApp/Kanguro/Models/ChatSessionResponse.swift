import Foundation
import KanguroUserDomain

struct ChatSessionResponse: Codable {
    
    var sessionId: String
    var type: SessionType
    var status: ChatUserStatus
}

enum ChatUserStatus: String, Codable {

    case Active
    case Finished
}
