import Foundation
import KanguroSharedDomain

extension ClaimWarningType {

    // MARK: - Computed Properties
    var title: String {
        switch self {
        case .missingDocument:
            return "reminder.missingDocument".localized
        }
    }
}
