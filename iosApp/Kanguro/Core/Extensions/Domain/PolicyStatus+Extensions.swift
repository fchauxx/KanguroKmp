import Foundation
import KanguroSharedDomain
import UIKit

extension PolicyStatus {
    // MARK: - Computed Properties
    var title: String {
        switch self {
        case .ACTIVE:
            return "dashboard.active.label".localized
        case .PENDING:
            return "dashboard.inactive.label".localized
        case .CANCELED:
            return "dashboard.canceled.label".localized
        case .TERMINATED:
            return "dashboard.terminated.label".localized
        }
    }

    var color: UIColor {
        switch self {
        case .ACTIVE:
            return .positiveDarkest
        case .PENDING, .CANCELED, .TERMINATED:
            return .negativeDarkest
        }
    }
}
