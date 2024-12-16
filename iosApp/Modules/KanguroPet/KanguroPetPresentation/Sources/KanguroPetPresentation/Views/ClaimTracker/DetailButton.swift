import SwiftUI
import KanguroDesignSystem

public struct DetailButton: View {
    
    private let label: String
    private let onPressed: () -> Void

    public init(label: String, onPressed: @escaping () -> Void) {
        self.label = label
        self.onPressed = onPressed
    }

    public var body: some View {
        Button {
            onPressed()
        } label: {
            Text(label)
                .captionSecondaryDarkestBold()
                .padding(.horizontal, StackSpacing.xxxs)
                .padding(.vertical, StackSpacing.nano)

        }
        .buttonStyle(CustomButtonStyle())
    }
}

struct CustomButtonStyle: ButtonStyle {
    let pressedColor = Color(red: 204 / 255, green: 211 / 255, blue: 221 / 255)

    func makeBody(configuration: Configuration) -> some View {
        configuration.label
            .frame(height: 28)
            .background(configuration.isPressed ?  pressedColor : .white)
            .foregroundStyle(.white)
            .clipShape(Capsule())
            .animation(.easeOut(duration: 0.05), value: configuration.isPressed)
            .overlay(
                RoundedRectangle(cornerRadius: CornerRadius.smd)
                    .stroke(Color.secondaryDarkest, lineWidth: 1)
            )
    }
}


struct DetailButton_Previews: PreviewProvider {
    static var previews: some View {
        DetailButton(label: "Detail") {}
    }
}
