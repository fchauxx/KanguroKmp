import Foundation

extension Array where Element == any KanguroChatbotMessage {
    public static func ==(lhs: Array<Element>, rhs: Array<Element>) -> Bool {
        if lhs.isEmpty && rhs.isEmpty { return true }
        guard lhs.count == rhs.count else { return false }
        for i in 0..<lhs.count {
            guard lhs[i].isEqualTo(rhs[i]) else {
                return false
            }
        }
        return true
    }

    public static func !=(lhs: Array<Element>, rhs: Array<Element>) -> Bool {
        return !(lhs == rhs)
    }
}
