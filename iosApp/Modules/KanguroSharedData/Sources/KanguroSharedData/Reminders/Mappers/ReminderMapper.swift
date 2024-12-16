import Foundation
import KanguroSharedDomain
import KanguroNetworkDomain

public struct RemindersMapper: ModelMapper {
    public typealias T = [Reminder]

    public static func map<T>(_ input: some Codable) throws -> T {
        guard let input: [RemoteReminder] = input as? [RemoteReminder] else { throw RequestError(errorType: .couldNotMap, errorMessage: "Could not map") }
        let reminders: [Reminder] = try input.map {
            return try ReminderMapper.map($0)
        }
        guard let result = reminders as? T else { throw RequestError(errorType: .couldNotMap, errorMessage: "Could not map") }
        return result
    }
}

public struct ReminderMapper: ModelMapper {
    public typealias T = Reminder

    public static func map<T>(_ input: some Codable) throws -> T {
        guard let input: RemoteReminder = input as? RemoteReminder else {
            throw RequestError(errorType: .couldNotMap, errorMessage: "Could not map")
        }
        
        var claimWarningType: ClaimWarningType? = nil
        guard let reminderType = ReminderType(rawValue: input.type.rawValue) else { throw RequestError(errorType: .couldNotMap, errorMessage: "Could not map") }
        if let remoteClaimWarningType: RemoteClaimWarningType = input.claimWarningType {
            claimWarningType = ClaimWarningType(rawValue: remoteClaimWarningType.rawValue)
        }
        let reminder = Reminder(id: input.id,
                                userId: input.userId,
                                petId: input.petId,
                                claimId: input.claimId,
                                dueDate: input.dueDate?.date,
                                type: reminderType,
                                createdAt: input.createdAt?.date,
                                claimWarningType: claimWarningType
        )
        guard let result: T = reminder as? T else {
            throw RequestError(errorType: .couldNotMap, errorMessage: "Could not map")
        }
        return result
    }
}
