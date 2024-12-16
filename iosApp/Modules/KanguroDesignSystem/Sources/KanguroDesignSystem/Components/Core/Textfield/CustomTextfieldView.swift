import SwiftUI

public struct CustomTextfieldView: View {

    // MARK: - Wrapped Properties
    @Binding var value: String

    // MARK: - Stored Properties
    var fieldTitle: String
    var placeholder: String
    var keyboardType: UIKeyboardType

    // MARK: - Initializer
    public init(fieldTitle: String,
                placeholder: String,
                keyboardType: UIKeyboardType = .default,
                value: Binding<String>) {
        self._value = value
        self.fieldTitle = fieldTitle
        self.placeholder = placeholder
        self.keyboardType = keyboardType
    }

    public var body: some View {
        VStack(alignment: .leading) {
            Text(fieldTitle.uppercased())
                .footnoteNeutralMediumBold()
            TextField(placeholder, text: $value)
                .padding(InsetSpacing.xxs)
                .keyboardType(keyboardType)
                .overlay(RoundedRectangle(cornerRadius: CornerRadius.sm).stroke(Color.neutralMedium, lineWidth: 2))
        }
        .padding(.bottom, InsetSpacing.xxs)
    }
}

struct CustomTextfieldView_Previews: PreviewProvider {

    static var previews: some View {
        CustomTextfieldView(
            fieldTitle: "Item",
            placeholder: "Placeholder",
            keyboardType: .default,
            value: .constant("")
        )
    }
}
