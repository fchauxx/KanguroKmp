import UIKit

enum ChatMessageFormat: String, Codable {
    
    case Text
    case Image
    case Map
    case Summary
    case PetImage
}

enum ChatMessageSender: String, Codable {

    case Bot
    case User
}
