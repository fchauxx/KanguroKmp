import Foundation

extension String: KanguroChatbotMessage {
    public func isEqualTo(_ other: any KanguroChatbotMessage) -> Bool {
        guard let another: String = other as? String else {
            return false
        }
        return self == another
    }
}
