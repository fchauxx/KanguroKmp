import UIKit

// MARK: - Static Properties
extension UIFont {
    
    static func raleway(withSize size: CGFloat, withWeight weight: TextBuilderFontWeight) -> UIFont {
        UIFont(name: "Raleway-\(weight.rawValue)", size: size) ?? UIFont.systemFont(ofSize: size)
    }
    
    static func lato(withSize size: CGFloat, withWeight weight: TextBuilderFontWeight) -> UIFont {
        UIFont(name: "Lato-\(weight.rawValue)", size: size) ?? UIFont.systemFont(ofSize: size)
    }
}
