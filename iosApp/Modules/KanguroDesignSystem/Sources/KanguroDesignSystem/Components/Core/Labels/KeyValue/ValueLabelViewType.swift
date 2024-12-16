import SwiftUI

public enum ValueLabelViewType {

    case primary
    case secondary
    case tertiary
    case quaternary

    var keyFontStyle: TextStyle {
        switch self {
        case .primary:
            return TextStyle(font: .lato(.latoRegular, size: 16), color: .secondaryDark)
        case .secondary:
            return TextStyle(font: .lato(.latoRegular, size: 14), color: .secondaryDark)
        case .tertiary:
            return TextStyle(font: .lato(.latoRegular, size: 12.8), color: .secondaryMedium)
        case .quaternary:
            return TextStyle(font: .lato(.latoRegular, size: 10.24), color: .secondaryMedium)
        }
    }

    var valueFontStyle: TextStyle {
        switch self {
        case .primary:
            return TextStyle(font: .lato(.latoBold, size: 16), color: .secondaryDarkest)
        case .secondary:
            return TextStyle(font: .lato(.latoRegular, size: 14), color: .secondaryDarkest)
        case .tertiary:
            return TextStyle(font: .lato(.latoBold, size: 12.8), color: .secondaryDark)
        case .quaternary:
            return TextStyle(font: .lato(.latoRegular, size: 10.24), color: .secondaryDark)
        }
    }
}
