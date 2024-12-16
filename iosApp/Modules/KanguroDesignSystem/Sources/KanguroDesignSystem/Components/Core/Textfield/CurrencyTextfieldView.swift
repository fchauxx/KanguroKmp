import SwiftUI

class FormattedTextModel: ObservableObject {
    @Published var formattedText = ""
    var value: Double {
        (Double(self.formattedText) ?? 0.0) / 100
    }
}

public struct CurrencyTextfieldView: View {
    
    // MARK: - Wrapped Properties
    @FocusState private var keyboardFocused: Bool
    @ObservedObject var model = FormattedTextModel()
    
    // MARK: - Stored Properties
    var fieldTitle: String
    var placeholder: String
    var keyboardType: UIKeyboardType
    
    // MARK: - Actions
    var didEndEditingAction: BindingClosure
    
    // MARK: - Initializer
    public init(fieldTitle: String,
                placeholder: String,
                keyboardType: UIKeyboardType = .numberPad,
                didEndEditingAction: @escaping BindingClosure) {
        self.fieldTitle = fieldTitle
        self.placeholder = placeholder
        self.keyboardType = keyboardType
        self.didEndEditingAction = didEndEditingAction
    }
    
    public var body: some View {
        VStack(alignment: .leading) {
            let valueFormatted = Binding<String>(get: { () -> String in
                return String(format: "%.2f", self.model.value)
            }) { (stringValue) in
                var stringValue = stringValue
                stringValue.removeAll { (character) -> Bool in
                    !character.isNumber
                }
                self.model.formattedText = stringValue
            }
            Text(fieldTitle.uppercased())
                .footnoteNeutralMediumBold()
            TextField(placeholder, text: valueFormatted)
                .padding(InsetSpacing.xxs)
                .keyboardType(keyboardType)
                .focused($keyboardFocused)
                .overlay(RoundedRectangle(cornerRadius: CornerRadius.sm).stroke(Color.neutralMedium, lineWidth: 2))
                .toolbar {
                    if keyboardFocused {
                        ToolbarItemGroup(placement: .keyboard) {
                            Spacer()
                            Button(action: {
                                keyboardFocused = false
                                didEndEditingAction(valueFormatted)
                            }, label: {
                                Text("Done")
                            })
                        }
                    }
                }
        }
        .padding(.bottom, InsetSpacing.xxs)
    }
}

struct CurrencyTextfieldView_Previews: PreviewProvider {
    
    static var previews: some View {
        CurrencyTextfieldView(fieldTitle: "Value",
                              placeholder: "U$",
                              keyboardType: .decimalPad,
                              didEndEditingAction: {_ in})
    }
}
