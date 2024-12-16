import Foundation
import UIKit

struct ChatMessage: Codable, Equatable {
    
    // MARK: - Stored Properties
    var format: ChatMessageFormat
    var content: String
    var order: Int
    var sender: ChatMessageSender? = .Bot
}
