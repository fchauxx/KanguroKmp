import UIKit

enum TabBarMenuItemType: String, Codable {
    
    case dashboard
    case cloud
    case coverage
    case more
    case bot
    
    // MARK: - Renters
    case home
    case pet
    case renters
    case chat
    
    var title: String {
        return "tabBarItem.\(self)".localized
    }
    
    var defaultIcon: UIImage? {
        return UIImage(named: "ic-tab-\(self)-default")
    }
    
    var activeIcon: UIImage? {
        return UIImage(named: "ic-tab-\(self)-selected")
    }
    
    var defaultColor: UIColor {
        return .secondaryMedium
    }
    
    var activeColor: UIColor {
        return .secondaryDarkest
    }
}
