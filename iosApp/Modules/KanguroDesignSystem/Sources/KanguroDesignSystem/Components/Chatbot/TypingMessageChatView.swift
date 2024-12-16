import SwiftUI

public struct TypingMessageChatView: View {

    public init() {}

    public var body: some View {
        VStack {
            LottieView(name: "chatbot-loading", loopMode: .loop)
        }
    }
}

// MARK: - Preview
struct TypingMessageChatView_Previews: PreviewProvider {
    static var previews: some View {
        TypingMessageChatView()
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
    }
}
