import SwiftUI

public enum VerticalItemStyle {
    
    case primary
    case secondary
    
    public var backgroundColor: Color {
        switch self {
        case .primary:
            return Color.neutralBackground
        case .secondary:
            return Color.white
        }
    }
    var textStyle: TextStyle {
        switch self {
        case .primary:
            return TextStyle(font: .lato(.latoRegular, size: 12),
                             color: .secondaryDarkest)
        case .secondary:
            return TextStyle(font: .lato(.latoRegular, size: 12),
                             color: .secondaryDarkest)
        }
    }
    var iconColor: Color {
        switch self {
        case .primary:
            return .secondaryDarkest
        case .secondary:
            return .secondaryMedium
        }
    }
}

public struct VerticalItem: View {
    
    // MARK: - Stored Properties
    public let title: String
    public var icon: Image
    public var style: VerticalItemStyle
    
    // MARK: - Initializers
    public init(title: String,
                icon: Image,
                style: VerticalItemStyle) {
        self.title = title
        self.icon = icon
        self.style = style
    }
    
    public var body: some View {
        VStack {
            HStack {
                HStack(spacing: StackSpacing.nano) {
                    Circle()
                        .foregroundColor(.primaryLight)
                        .frame(height: HeightSize.md)
                        .overlay(
                            icon
                                .renderingMode(.template)
                                .resizable()
                                .scaledToFit()
                                .frame(width: InsetSpacing.xs, height: InsetSpacing.xs)
                                .foregroundColor(style.iconColor)
                        )
                    
                    Text(title)
                        .font(style.textStyle.font)
                        .foregroundColor(style.textStyle.color)
                    
                    Spacer()
                }
            }
            .padding(InsetSpacing.xxs)
        } //: VStack
        .background(style.backgroundColor)
        .cornerRadius(CornerRadius.sm)
    }
}

// MARK: - Previews
struct VerticalItem_Previews: PreviewProvider {
    
    static var previews: some View {
        VerticalItem(title: "Theft", icon: Image.uploadIcon, style: .primary)
    }
}
