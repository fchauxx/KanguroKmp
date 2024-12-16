import SwiftUI

public struct ChatMultipleChoiceButtons: View {

    // MARK: - Stored Properties
    private let choices: [MultipleChoiceViewData]
    private let fontColor: Color
    private let backgroundColor: Color
    private let borderColor: Color
    private let defaultChoice: MultipleChoiceViewData?

    // MARK: - Initializers
    public init(
        choices: [MultipleChoiceViewData],
        fontColor: Color,
        backgroundColor: Color,
        borderColor: Color
    ) {
        self.choices = choices
        self.fontColor = fontColor
        self.backgroundColor = backgroundColor
        self.borderColor = borderColor
        guard !choices.isEmpty else {
            defaultChoice = nil
            return
        }
        defaultChoice = choices.first { $0.isDefaultChoice == true} ?? choices.last!
    }

    public var body: some View {
        HStack(alignment: .bottom, spacing: 0) {
            ForEach(choices) { choice in
                Button(action: choice.action ) {
                    HStack(spacing: 0) {
                        Spacer()
                        buttonText(choice)
                        Spacer()
                    }.overlay(Rectangle()
                        .frame(width: nil, height: 0.5, alignment: .top)
                        .foregroundColor(borderColor), alignment: .top)
                    .overlay(Rectangle()
                        .frame(width: isLastChoice(choice) ? 0 : 0.5, height: nil, alignment: .trailing)
                        .foregroundColor(borderColor), alignment: .trailing)
                }
            }.background(backgroundColor)
        }.frame(minWidth: 0, maxWidth: .infinity, minHeight: ActionButtonSize.lg)
    }
    // MARK: - Helper methods
    private func isLastChoice(_ currentChoice: MultipleChoiceViewData) -> Bool {
        guard !choices.isEmpty else { return false }
        return choices.last! == currentChoice
    }

    @ViewBuilder
    private func buttonText(_ currentChoice: MultipleChoiceViewData) -> some View {
        if currentChoice == defaultChoice {
            Text(currentChoice .choice).bold()
                .padding(InsetSpacing.md)
                .foregroundColor(fontColor)
        } else {
            Text(currentChoice.choice)
                .padding(InsetSpacing.md)
                .foregroundColor(fontColor)
        }
    }
}

// MARK: - Preview
struct ChatMultipleChoiceButtons_Previews: PreviewProvider {

    static var previews: some View {
        let choices1: [MultipleChoiceViewData] = [
            MultipleChoiceViewData(choice: "No", action: { print("NO tapped") }),
            MultipleChoiceViewData(choice: "Yes", isDefaultChoice: true, action: { print("YES tapped") })
        ]

        let choices2: [MultipleChoiceViewData] = [
            MultipleChoiceViewData(choice: "PRESS ME", action: { print("PRESS ME tapped") })
        ]

        let choices3: [MultipleChoiceViewData] = [
            MultipleChoiceViewData(choice: "Skip", action: { print("SKIP tapped") }),
            MultipleChoiceViewData(choice: "Abort", action: { print("ABORT tapped")}),
            MultipleChoiceViewData(choice: "Go on", action: {print("GO ON tapped")})
        ]

        ChatMultipleChoiceButtons(
            choices: choices1,
            fontColor: .secondaryDarkest,
            backgroundColor: .neutralBackground,
            borderColor: .neutralLightest
        )
        .previewDevice(.init(rawValue: "iPhone 8"))
        .previewDisplayName("iPhone 8")

        ChatMultipleChoiceButtons(
            choices: choices2,
            fontColor: .secondaryDarkest,
            backgroundColor: .neutralBackground,
            borderColor: .neutralLightest
        )
        .previewDevice(.init(rawValue: "iPhone 14 Pro Max"))
        .previewDisplayName("iPhone 14 Pro Max")

        ChatMultipleChoiceButtons(
            choices: choices3,
            fontColor: .secondaryDarkest,
            backgroundColor: .neutralBackground,
            borderColor: .neutralLightest
        )
        .previewDevice(.init(rawValue: "iPhone 14 Pro Max"))
        .previewDisplayName("iPhone 14 Pro Max")
    }
}
