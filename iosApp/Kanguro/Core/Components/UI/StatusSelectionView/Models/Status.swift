import UIKit
import SwiftUI
import KanguroSharedDomain

public enum Status: CaseIterable {
    
    case none
    case active
    case inactive
    case terminated
    
    // MARK: - Computed Properties
    var title: String {
        switch self {
        case .active:
            return "commom.active".localized
        case .inactive:
            return "commom.inactive".localized
        case .none:
            return "commom.all".localized
        case .terminated:
            return "commom.terminated".localized
        }
    }
    var color: UIColor {
        switch self {
        case .active:
            return .positiveDark
        case .inactive, .terminated:
            return .negativeDark
        case .none:
            return .clear
        }
    }
    var policyStatus: [KanguroSharedDomain.PolicyStatus] {
        switch self {
        case .active:
            return [.ACTIVE]
        case .inactive:
            return [.CANCELED, .PENDING]
        case .terminated:
            return [.TERMINATED]
        case .none:
            return [.TERMINATED, .CANCELED, .ACTIVE, .PENDING]
        }
    }
}
