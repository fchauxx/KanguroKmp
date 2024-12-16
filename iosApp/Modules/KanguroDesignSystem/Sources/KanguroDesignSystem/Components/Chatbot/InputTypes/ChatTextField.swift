import SwiftUI

public struct ChatTextField: View {
    
    // MARK: - Published Properties
    @State private var text: String = ""
    
    // MARK: - Stored Properties
    var placeholder: String?
    
    // MARK: - Actions
    var didEndEditingAction: StringClosure
    
    public init(placeholder: String? = nil,
                didEndEditingAction: @escaping StringClosure) {
        self.placeholder = placeholder
        self.didEndEditingAction = didEndEditingAction
    }
    
    // MARK: - Body
    public var body: some View {
        VStack {
            TextField(placeholder ?? "", text: $text)
                .bodySecondaryDarkestRegular()
                .padding(.horizontal, InsetSpacing.xxs)
                .autocorrectionDisabled(true)
                .keyboardType(.alphabet)
                .frame(height: HeightSize.sm)
                .background(Color.white)
                .cornerRadius(CornerRadius.md)
                .overlay(
                    HStack {
                        Spacer()
                        Button {
                            didEndEditingAction(text)
                            text = ""
                        } label: {
                            Image.sendIcon
                                .resizable()
                                .frame(width: IconSize.sm,
                                       height: IconSize.sm)
                                .padding(InsetSpacing.nano)
                        }
                    }
                )
                .overlay(
                    RoundedRectangle(cornerRadius: CornerRadius.md)
                        .stroke(Color.neutralLight, lineWidth: 1)
                )
                .padding(.horizontal, StackSpacing.xxxs)
                .padding(.vertical, StackSpacing.nano)
        } //: VStack
        .background(Color.neutralBackground)
    }
}

// MARK: - Previews
struct ChatTextField_Previews: PreviewProvider {
    
    static var previews: some View {
        ChatTextField(placeholder: "placeholder",
                      didEndEditingAction: { text in })
    }
}
