import Foundation
import KanguroSharedDomain
import UIKit

extension KanguroSharedDomain.ReminderType {

    // MARK: - Computed Properties
    public var title: String {
        switch self {
        case .MedicalHistory:
            return "reminder.medicalHistory".localized
        case .Claim:
            return "reminder.claim".localized
        }
    }
}

extension KanguroSharedDomain.Reminder {
    
    // MARK: - Computed Properties
    public var eventDate: Date? {
        return dueDate ?? nil
    }
    public var daysUntilEvent: Int? {
        guard let eventDate = eventDate else { return nil }
        return eventDate.dayBetweenDate(Date())
    }
    public var isPastEvent: Bool? {
        guard let daysUntilEvent = daysUntilEvent else { return nil }
        return (daysUntilEvent < 0) ? true : false
    }
    public var isImminentEvent: Bool? {
        guard let daysUntilEvent = daysUntilEvent,
              let isPastEvent = isPastEvent else { return nil }
        return (isPastEvent || daysUntilEvent > 30) ? false : true
    }
    public var isCriticalDateEvent: Bool? {
        guard let isImminentEvent = isImminentEvent,
              let isPastEvent = isPastEvent else { return nil }
        return (isImminentEvent || isPastEvent)
    }
    public var remainingDays: String? {
        guard let isImminentEvent = isImminentEvent,
              let eventDate = eventDate,
              let daysUntilEvent = daysUntilEvent else { return nil }
        let fullNamedDate = "\(eventDate.fullNamedDate)"
        let nearDate = "reminders.dueIn.label".localized + "\(daysUntilEvent) " + "commom.days".localized
        return isImminentEvent ? nearDate : fullNamedDate
    }
}
