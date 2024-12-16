import SwiftUI

public struct SecondaryButtonView: View {

    // MARK: - Stored Properties
    var buttonLabel: String
    var height: CGFloat
    var isAtPopUp: Bool

    // MARK: - Actions
    var action: SimpleClosure

    // MARK: - Initializer
    public init(_ buttonLabel: String,
                height: CGFloat = HeightSize.md,
                isAtPopUp: Bool = false,
                _ action: @escaping SimpleClosure) {
        self.buttonLabel = buttonLabel
        self.height = height
        self.isAtPopUp = isAtPopUp
        self.action = action
    }

    public var body: some View {
        VStack {
            Button {
                action()
            } label: {
                HStack(spacing: InlineSpacing.nano) {
                    if isAtPopUp {
                        Text(buttonLabel)
                            .calloutSecondaryDarkestExtraBold()
                    } else {
                        Text(buttonLabel)
                            .bodySecondaryDarkestExtraBold()
                            .padding(InsetSpacing.xs)
                    }
                }
            }
            .frame(maxWidth: .infinity)
            .frame(height: height)
            .background(.white)
            .overlay(
                RoundedRectangle(cornerRadius: CornerRadius.sm)
                    .stroke(Color.secondaryDarkest, lineWidth: 1)
            )
        }
    }
}

struct SecondaryButtonView_Previews: PreviewProvider {
    static var previews: some View {
        SecondaryButtonView("Back") {
        }
    }
}
