import UIKit

enum TextBuilderFontWeight: String {
    case black = "Black"
    case bold = "Bold"
    case medium = "Medium"
    case regular = "Regular"
    case light = "Light"
}

enum TextBuilderFontSize: CGFloat {
    case p10 = 10
    case p11 = 11
    case p12 = 12
    case p14 = 14
    case p16 = 16
    case p21 = 21
    case h24 = 24
    case h28 = 28
    case h32 = 32
    case h36 = 36
    case h40 = 40
    case h48 = 48
    case h64 = 64
    case h80 = 80
    case h96 = 96
}

enum TextBuilderFont {
    case raleway
    case lato
}

struct TextStyle {
    var color: UIColor = .primaryDarkest
    var weight: TextBuilderFontWeight = .regular
    var size: TextBuilderFontSize = .p14
    var font: TextBuilderFont = .lato
    var alignment: NSTextAlignment = .left
    var lines: Int = 0
    var minimumScaleFactor: CGFloat = 0.6
    var lineSpacing: CGFloat = 1.0
    var lineHeightMultiple = 1.0
    var underlined: Bool = false
}
