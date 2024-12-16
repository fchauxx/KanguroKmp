public enum ClaimStatus: String, Equatable {

    case Submitted
    case Assigned
    case InReview
    case Approved
    case Draft
    case Closed
    case Deleted
    case Denied
    case Paid
    case PendingMedicalHistory
    case MedicalHistoryInReview
    case Unknown

    public var index: Int {
        switch self {
        case .Submitted:
            return 1
        case .Assigned:
            return 2
        case .InReview:
            return 3
        case .Closed:
            return 4
        default:
            return 0
        }
    }
}

public extension ClaimStatus {
    init(value: String) {
        switch value.lowercased() {
        case "submitted": self = .Submitted
        case "assigned": self = .Assigned
        case "inreview" : self = .InReview
        case "approved" : self = .Approved
        case "draft" : self = .Draft
        case "closed" : self = .Closed
        case "deleted" : self = .Deleted
        case "denied" : self = .Denied
        case "paid" : self = .Paid
        case "pendingmedicalhistory" : self = .PendingMedicalHistory
        case "medicalhistoryinreview" : self = .MedicalHistoryInReview
        default: self = .Unknown
        }
    }
}
