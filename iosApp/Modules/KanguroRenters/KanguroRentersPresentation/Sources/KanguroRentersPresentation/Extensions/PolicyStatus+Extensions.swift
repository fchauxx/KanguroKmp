import Foundation
import KanguroSharedDomain

extension PolicyStatus {
    // MARK: - Computed Properties
    var title: String {
        switch self {
        case .ACTIVE:
            return "dashboard.active.label".localized()
        case .PENDING:
            return "dashboard.inactive.label".localized()
        case .CANCELED:
            return "dashboard.canceled.label".localized()
        case .TERMINATED:
            return "dashboard.terminated.label".localized()
        }
    }
}
