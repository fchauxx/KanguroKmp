import Foundation

public enum BillingCycle: String, Identifiable {
    
    public var id: UUID {
        return UUID()
    }
    
    case YEARLY
    case QUARTERLY
    case MONTHLY
}
