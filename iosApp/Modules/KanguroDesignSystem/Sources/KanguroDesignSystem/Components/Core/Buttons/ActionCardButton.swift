import SwiftUI

public enum ActionCardButtonStyle {
    
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
            return TextStyle(font: .lato(.latoBold, size: 16),
                             color: .secondaryDarkest)
        case .secondary:
            return TextStyle(font: .lato(.latoBold, size: 16),
                             color: .secondaryDarkest)
        }
    }
    var iconColor: Color {
        switch self {
        case .primary:
            return .secondaryDark
        case .secondary:
            return .secondaryMedium
        }
    }
}

public struct ActionCardButton: View {
    
    // MARK: - Stored Properties
    public let title: String
    public var icon: Image
    public var style: ActionCardButtonStyle
    public var highlightTag: String?
    
    // MARK: - Actions
    public var didTapAction: SimpleClosure
    
    // MARK: - Initializers
    public init(title: String,
                icon: Image,
                style: ActionCardButtonStyle,
                highlightTag: String? = nil,
                didTapAction: @escaping SimpleClosure) {
        self.title = title
        self.icon = icon
        self.style = style
        self.didTapAction = didTapAction
        self.highlightTag = highlightTag
    }
    
    public var body: some View {
        Button {
            didTapAction()
        } label: {
            VStack {
                HStack {
                    HStack(spacing: StackSpacing.nano) {
                        icon
                            .renderingMode(.template)
                            .resizable()
                            .frame(width: InsetSpacing.xs,
                                   height: InsetSpacing.xs)
                            .foregroundColor(style.iconColor)
                        
                        Text(title)
                            .font(style.textStyle.font)
                            .foregroundColor(style.textStyle.color)
                            .multilineTextAlignment(.leading)

                        Spacer()
                        
                        if let tag = self.highlightTag {
                            HighlightTag(tag: tag)
                        }
                    }
                }
                .padding(InsetSpacing.xxs)
            } //: VStack
            .background(style.backgroundColor)
            .cornerRadius(CornerRadius.sm)
        }
    }
}

// MARK: - Previews
struct ActionCardButton_Previews: PreviewProvider {
    
    static var previews: some View {
        ActionCardButton(title: "What is covered",
                         icon: Image.uploadIcon,
                         style: .primary,
                         didTapAction: {})
        
        ActionCardButton(title: "What is covered",
                         icon: Image.uploadIcon,
                         style: .primary,
                         highlightTag: "NEW",
                         didTapAction: {})
    }
}
