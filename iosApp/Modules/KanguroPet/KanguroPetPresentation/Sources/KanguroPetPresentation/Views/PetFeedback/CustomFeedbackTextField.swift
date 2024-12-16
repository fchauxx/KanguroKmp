import SwiftUI
import KanguroDesignSystem

struct CustomFeedbackTextField: View {
    // MARK: - Wrapped Properties
    @Binding var value: String

    // MARK: - Stored Properties
    var placeholder: String
    var keyboardType: UIKeyboardType

    // MARK: - Initializer
    init(
        placeholder: String,
        keyboardType: UIKeyboardType = .default,
        value: Binding<String>
    ) {
        self._value = value
        self.placeholder = placeholder
        self.keyboardType = keyboardType
    }

    var body: some View {
        ZStack(alignment: .leading) {
            if value.isEmpty {
                Text(placeholder)
                    .padding(.horizontal)
                    .frame(
                        maxWidth: .infinity,
                        maxHeight: .infinity,
                        alignment: .topLeading
                    )
            }

            VStack {
                TextField("", text: $value)
                Spacer()
            }
            .frame(idealHeight: 130, alignment: .top)
            .padding(.horizontal)
            .keyboardType(keyboardType)
        }
        .bodySecondaryDarkestBold()
        .multilineTextAlignment(.leading)
        .padding(InsetSpacing.xxs)
        .overlay(
          RoundedRectangle(cornerRadius: CornerRadius.sm)
            .stroke(Color.secondaryDarkest, lineWidth: 2)
        )
    }
}

#Preview {
    CustomFeedbackTextField(
        placeholder: "Placeholder",
        keyboardType: .default,
        value: .constant(""))
}
