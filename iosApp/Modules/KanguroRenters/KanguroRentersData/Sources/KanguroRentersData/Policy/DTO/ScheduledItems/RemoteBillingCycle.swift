import Foundation

public enum RemoteBillingCycle: String, Identifiable, Codable {
    
    public var id: UUID {
        return UUID()
    }
    
    case YEARLY
    case QUARTERLY
    case MONTHLY
}
