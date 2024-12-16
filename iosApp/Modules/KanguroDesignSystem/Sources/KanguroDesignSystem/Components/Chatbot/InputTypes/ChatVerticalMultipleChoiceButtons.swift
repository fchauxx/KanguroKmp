import SwiftUI

public struct ChatVerticalMultipleChoiceButtons: View {

    // MARK: - Stored Properties
    private let choices: [MultipleChoiceViewData]
    private let backgroundColor: Color
    private let borderColor: Color

    // MARK: - Initializers
    public init(
        choices: [MultipleChoiceViewData],
        backgroundColor: Color,
        borderColor: Color
    ) {
        self.choices = choices
        self.backgroundColor = backgroundColor
        self.borderColor = borderColor
        guard !choices.isEmpty else {
            return
        }
    }

    public var body: some View {
        VStack(spacing: 0) {
            ForEach(choices) { choice in
                Button(action: choice.action) {
                    VStack {
                        Divider()
                            .background(borderColor)
                        HStack(spacing: StackSpacing.nano) {
                            if let icon = choice.icon {
                                icon.image
                                    .resizable()
                                    .frame(width: IconSize.sm, height: IconSize.sm)
                                    .padding([.leading], StackSpacing.xxxs)
                            }
                            buttonText(choice)
                                .padding([.leading], choice.icon != nil ? 0 : StackSpacing.xxxs)
                            Spacer()
                        }
                        .frame(height: ActionButtonSize.sm)
                    }
                }
            }.background(backgroundColor)
        }.frame(minWidth: 0, maxWidth: .infinity, minHeight: ActionButtonSize.sm)
    }

    @ViewBuilder
    private func buttonText(_ currentChoice: MultipleChoiceViewData) -> some View {
            Text(currentChoice.choice)
                .bodySecondaryDarkestRegular()
    }
}

// MARK: - Preview
struct ChatVerticalMultipleChoiceButtons_Previews: PreviewProvider {

    static var previews: some View {
        let choices1: [MultipleChoiceViewData] = [
            MultipleChoiceViewData(choice: "Take a picture(s)", action: { print("Take a picture tapped") }),
            MultipleChoiceViewData(choice: "Select picture(s)", action: { print("Select picture tapped") })
        ]

        let choices2: [MultipleChoiceViewData] = [
            MultipleChoiceViewData(choice: "Record video(s)", icon: .camera, action: { print("Record video tapped") })
        ]

        let choices3: [MultipleChoiceViewData] = [
            MultipleChoiceViewData(choice: "Take a picture(s)", icon: .camera, action: { print("Take a picture tapped") }),
            MultipleChoiceViewData(choice: "Select picture(s)", icon: .gallery, action: { print("Select picture tapped")}),
            MultipleChoiceViewData(choice: "Select file(s)", icon: .files, action: {print("Select file tapped")})
        ]

        ChatVerticalMultipleChoiceButtons(
            choices: choices1,
            backgroundColor: .neutralBackground,
            borderColor: .neutralLightest
        )
        .previewDevice(.init(rawValue: "iPhone 8"))
        .previewDisplayName("iPhone 8")

        ChatVerticalMultipleChoiceButtons(
            choices: choices2,
            backgroundColor: .neutralBackground,
            borderColor: .neutralLightest
        )
        .previewDevice(.init(rawValue: "iPhone 14 Pro Max"))
        .previewDisplayName("iPhone 14 Pro Max")

        ChatVerticalMultipleChoiceButtons(
            choices: choices3,
            backgroundColor: .neutralBackground,
            borderColor: .neutralLightest
        )
        .previewDevice(.init(rawValue: "iPhone 14 Pro Max"))
        .previewDisplayName("iPhone 14 Pro Max")
    }
}
