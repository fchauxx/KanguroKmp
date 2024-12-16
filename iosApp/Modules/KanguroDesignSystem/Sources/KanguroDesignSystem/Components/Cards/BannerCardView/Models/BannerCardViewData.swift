import SwiftUI

public enum BannerCardViewStyle {
    
    case vertical
    case horizontalLeft
    case horizontalRight
    
    public var titleStyle: TextStyle {
        switch self {
        case .horizontalLeft, .horizontalRight:
            return TextStyle(font: .museo(.museoSansBold, size: 21), color: .secondaryDarkest)
        case .vertical:
            return TextStyle(font: .lato(.latoBold, size: 16), color: .secondaryDark)
        }
    }
    public var subtitleStyle: TextStyle {
        switch self {
        case .horizontalLeft, .horizontalRight:
            return TextStyle(font: .lato(.latoRegular, size: 14), color: .secondaryDarkest)
        case .vertical:
            return TextStyle(font: .lato(.latoRegular, size: 13), color: .secondaryLight)
        }
    }
    public var iconSize: CGSize {
        switch self {
        case .vertical:
            return CGSize(width: IconSize.lg, height: IconSize.lg)
        case .horizontalLeft:
            return CGSize(width: IconSize.xxl, height: IconSize.xxl)
        case .horizontalRight:
            return CGSize(width: IconSize.huge, height: IconSize.xxxl)
        }
    }
    public var cornerRadius: CGFloat {
        switch self {
        case .vertical:
            return CornerRadius.md
        case .horizontalLeft, .horizontalRight:
            return CornerRadius.sm
        }
    }
}

public struct BannerCardViewData {
    
    let style: BannerCardViewStyle
    let backgroundColor: Color
    let leadingImage: Image
    let title: String
    let subtitle: String
    let tapAction: SimpleClosure
    
    public init(style: BannerCardViewStyle,
                backgroundColor: Color,
                leadingImage: Image,
                title: String,
                subtitle: String,
                tapAction: @escaping SimpleClosure) {
        self.style = style
        self.backgroundColor = backgroundColor
        self.leadingImage = leadingImage
        self.title = title
        self.subtitle = subtitle
        self.tapAction = tapAction
    }
}
