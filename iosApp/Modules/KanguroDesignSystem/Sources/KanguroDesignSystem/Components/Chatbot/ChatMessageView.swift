import SwiftUI

public enum ChatMessageStyle {
    case roundedButTopLeft
    case roundedButBottomRight
    case allRounded
}

public enum ChatMessageHorizontalAlignment {
    case left
    case right
}

public struct ChatMessageView: View {

    // MARK: - Stored Properties
    private let style: ChatMessageStyle
    private let alignment: ChatMessageHorizontalAlignment
    private let message: String
    private var image: Image?
    private var avatar: Image?
    private let backgroundColor: Color

    // MARK: - ComputedProperties
    var corners: UIRectCorner {
        switch style {
        case .roundedButTopLeft:
            return UIRectCorner(arrayLiteral: [.bottomLeft, .bottomRight, .topRight])
        case .roundedButBottomRight:
            return UIRectCorner(arrayLiteral: [.topLeft, .topRight, .bottomLeft])
        case .allRounded:
            return UIRectCorner(arrayLiteral: [.allCorners])
        }
    }

    // MARK: - Initializers
    public init(
        style: ChatMessageStyle,
        alignment: ChatMessageHorizontalAlignment,
        message: String,
        backgroundColor: Color,
        avatar: Image?,
        image: Image? = nil

    ) {
        self.style = style
        self.alignment = alignment
        self.message = message
        self.backgroundColor = backgroundColor
        self.avatar = avatar
        self.image = image
    }

    public var body: some View {
        HStack(spacing: 0) {
            HStack(alignment: .top, spacing: InlineSpacing.nano) {
                if alignment == .right {
                    Spacer()
                }
                Group {
                    if let avatar {
                        avatar
                            .resizable()
                            .accessibilityHidden(true)
                    }
                }
                .frame(width: IconSize.sm,
                       height: IconSize.sm,
                       alignment: .topLeading)
                
                HStack {
                    Text(message)
                        .bodySecondaryDarkestRegular()
                        .padding(InsetSpacing.nano)
                        .frame(alignment: self.alignment == .right ? .topTrailing : .topLeading)
                        .background(backgroundColor)
                        .cornerRadius(CornerRadius.sm, corners: corners)
                    if style != .roundedButTopLeft && alignment == .left {
                        Spacer()
                    }
                }
                .padding(.leading, avatar != nil ? 0 : InsetSpacing.md)
            }
            if style == .roundedButTopLeft {
                Spacer()
            }
        }
        .accessibilityValue(message)
    }
}

// MARK: - Preview
struct ChatMessageView_Previews: PreviewProvider {
    static var previews: some View {
        ChatMessageView(
            style: .roundedButTopLeft,
            alignment: .left,
            message: "What's the name of your spouse ?",
            backgroundColor: .neutralBackground,
            avatar: Image("javier", bundle: .module)
        )
            .previewDevice(.init(rawValue: "iPhone 8"))
            .previewDisplayName("iPhone 8")

        ChatMessageView(
            style: .allRounded,
            alignment: .left,
            message: "Before we start, let me ask you a few questions.",
            backgroundColor: .neutralBackground,
            avatar: nil
        )
            .previewDevice(.init(rawValue: "iPhone 14 Pro Max"))
            .previewDisplayName("iPhone 14 Pro Max")

        ChatMessageView(
            style: .roundedButBottomRight,
            alignment: .right,
            message: "Lauren Ipsum.",
            backgroundColor: .secondaryLightest,
            avatar: nil
        )
            .previewDevice(.init(rawValue: "iPhone 14 Pro Max"))
            .previewDisplayName("iPhone 14 Pro Max")
        
        ChatMessageView(
            style: .allRounded,
            alignment: .right,
            message: "Lauren Ipsum.",
            backgroundColor: .secondaryLightest,
            avatar: nil
        )
            .previewDevice(.init(rawValue: "iPhone 14 Pro Max"))
            .previewDisplayName("iPhone 14 Pro Max")
    }
}
