import KanguroSharedDomain
import UIKit

extension ClaimStatus {
    
    // MARK: - Computed Properties
    var primaryColor: UIColor {
        switch self {
        case .Submitted, .InReview, .Assigned, .MedicalHistoryInReview:
            return .tertiaryExtraDark
        case .Closed:
            return .negativeDarkest
        case .Approved, .Paid:
            return .positiveDark
        case .Denied:
            return .negativeDark
        case .PendingMedicalHistory:
            return .warningMedium
        default:
            return .clear
        }
    }
    var secondaryColor: UIColor {
        switch self {
        case .Submitted, .InReview, .Assigned, .Paid, .Approved, .MedicalHistoryInReview:
            return .warningMedium
        case .Denied:
            return .negativeLightest
        case .PendingMedicalHistory:
            return .warningMedium
        default:
            return primaryColor
        }
    }
    var image: UIImage? {
        switch self {
        case .Approved, .Paid:
            return UIImage(named: "ic-correct")
        case .Denied:
            return UIImage(named: "ic-clear-lightest")
        default:
            return nil
        }
    }
    var title: String {
        switch self {
        case .Submitted:
            return "claimStatus.submitted".localized
        case .Assigned:
            return "claimStatus.assigned".localized
        case .InReview:
            return "claimStatus.review".localized
        case .Closed:
            return "claimStatus.closed".localized
        case .Approved:
            return "claimStatus.approved".localized
        case .Denied:
            return "claimStatus.denied".localized
        case .Paid:
            return "claimStatus.paid".localized
        case .Draft:
            return "claimStatus.draft".localized
        case .Deleted:
            return "claimStatus.deleted".localized
        case .PendingMedicalHistory:
            return "claimStatus.pendingMedicalHistory".localized
        case .MedicalHistoryInReview:
            return "claimStatus.medicalHistoryInReview".localized
        case .Unknown:
            return "claimStatus.unknown".localized
        }
    }
}
