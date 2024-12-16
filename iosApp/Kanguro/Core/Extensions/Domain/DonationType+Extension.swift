import Foundation
import KanguroSharedDomain
import UIKit

extension KanguroSharedDomain.DonationType {
    
    // MARK: - Computed Properties
    var image: UIImage? {
        switch self {
        case .Animals:
            return UIImage(named: "ic-primary-paw") ?? nil
        case .GlobalWarming:
            return UIImage(named: "ic-primary-global") ?? nil
        case .SocialCauses:
            return UIImage(named: "ic-primary-socialcause") ?? nil
        case .LatinCommunities:
            return UIImage(named: "ic-primary-community") ?? nil
        }
    }
    
    var title: String {
        switch self {
        case .Animals:
            return "donation.animals.card".localized
        case .GlobalWarming:
            return "donation.globalWarming.card".localized
        case .SocialCauses:
            return "donation.social.card".localized
        case .LatinCommunities:
            return "donation.latinCommunities.card".localized
        }
    }
}
