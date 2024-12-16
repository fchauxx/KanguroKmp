import Foundation

public struct Reminder: Equatable {
    
    // MARK: - Stored Properties
    public var id: String?
    public var userId: String?
    public var petId: Int?
    public var claimId: String?
    public var dueDate: Date?
    public var type: ReminderType
    public var createdAt: Date?
    public var claimWarningType: ClaimWarningType?
        
    public init(id: String? = nil,
                userId: String? = nil,
                petId: Int? = nil,
                claimId: String? = nil,
                dueDate: Date? = nil,
                type: ReminderType,
                createdAt: Date? = nil,
                claimWarningType: ClaimWarningType? = nil) {
        self.id = id
        self.userId = userId
        self.petId = petId
        self.claimId = claimId
        self.dueDate = dueDate
        self.type = type
        self.createdAt = createdAt
        self.claimWarningType = claimWarningType
    }
}
