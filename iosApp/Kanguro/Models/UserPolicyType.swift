import SwiftUI

enum UserPolicyType {
    
    case pet
    case renters
    case all
    case none
}

// MARK: - ComputedProperties
extension UserPolicyType {
    
    var bannerTexts: (title: String, subtitle: String)? {
        switch self {
        case .pet:
            return (title: "dashboard.bannerTitle.havePet".localized,
                    subtitle: "dashboard.bannerSubtitle.havePet".localized)
        case .renters:
            return (title: "dashboard.bannerTitle.haveRenters".localized,
                    subtitle: "dashboard.bannerSubtitle.haveRenters".localized)
        default:
            return nil
        }
    }
    var bannerColor: Color? {
        switch self {
        case .pet:
            return .primaryLight
        case .renters:
            return .tertiaryLight
        default:
            return nil
        }
    }
    var bannerImage: Image? {
        switch self {
        case .pet:
            return Image.petUpsellingBackgrounded
        case .renters:
            return Image.rentersUpsellingBackgrounded
        default:
            return nil
        }
    }
}
