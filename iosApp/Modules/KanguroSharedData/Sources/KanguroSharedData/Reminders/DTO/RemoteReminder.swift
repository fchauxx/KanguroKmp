import Foundation
import UIKit

public enum RemoteReminderType: String, Codable, Equatable {
    
    case MedicalHistory
    case Claim
}

public enum RemoteClaimWarningType: String, Codable, Equatable {
    
    case missingDocument
}

public struct RemoteReminder: Codable, Equatable {
    
    // MARK: - Stored Properties
    public var id: String?
    public var userId: String?
    public var petId: Int?
    public var claimId: String?
    public var dueDate: String?
    public var type: RemoteReminderType
    public var createdAt: String?
    public var claimWarningType: RemoteClaimWarningType?
    
    public init(id: String? = nil,
                userId: String? = nil,
                petId: Int? = nil,
                claimId: String? = nil,
                dueDate: String? = nil,
                type: RemoteReminderType,
                createdAt: String? = nil,
                claimWarningType: RemoteClaimWarningType? = nil) {
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
