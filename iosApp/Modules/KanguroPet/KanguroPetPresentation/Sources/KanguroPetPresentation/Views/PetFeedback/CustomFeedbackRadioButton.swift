import SwiftUI
import KanguroDesignSystem

struct CustomFeedbackRadioButton: View {

    // MARK: - Property wrappers
    @Binding private var isSelected: Bool

    // MARK: - Stored Properties
    let size: CGFloat
    let borderColor: Color
    let selectedCircleColor: Color
    let bottomText: String

    var didSelectAction: SimpleClosure

    // MARK: - Initializers
    public init(isSelected: Binding<Bool> = .constant(false),
                size: CGFloat,
                borderColor: Color = Color.secondaryDark,
                selectedCircleColor: Color = Color.white,
                didSelectAction: @escaping SimpleClosure,
                bottomText: String) {
        self._isSelected = isSelected
        self.size = size
        self.borderColor = borderColor
        self.selectedCircleColor = selectedCircleColor
        self.didSelectAction = didSelectAction
        self.bottomText = bottomText
    }

    public var body: some View {
        VStack(alignment: .center, spacing: InsetSpacing.xxxs) {
            Button {
                isSelected.toggle()
            } label: {
                ZStack {
                    Circle()
                        .foregroundColor(isSelected ? selectedCircleColor : Color.clear)
                    if isSelected {
                        Image(systemName: "checkmark")
                            .scaleEffect(0.6)
                            .font(Font.title.bold())
                            .foregroundColor(.secondaryDarkest)
                    }
                }
            }
            .frame(width: size, height: size)
            .overlay(
                Circle()
                    .stroke(Color.secondaryDarkest, lineWidth: 3))

            Text(bottomText)
                .bodySecondaryDarkestBold()
        }
    }
}

#Preview {
    CustomFeedbackRadioButton(isSelected: .constant(true),
                              size: 30,
                              didSelectAction: {},
                              bottomText: "5")
}

#Preview {
    CustomFeedbackRadioButton(isSelected: .constant(false),
                              size: 30,
                              didSelectAction: {},
                              bottomText: "10")
}
