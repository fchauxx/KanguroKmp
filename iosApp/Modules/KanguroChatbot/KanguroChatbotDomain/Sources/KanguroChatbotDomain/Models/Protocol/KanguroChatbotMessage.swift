import Foundation

public protocol KanguroChatbotMessage: Equatable {
    func isEqualTo(_ other: any KanguroChatbotMessage) -> Bool
}

