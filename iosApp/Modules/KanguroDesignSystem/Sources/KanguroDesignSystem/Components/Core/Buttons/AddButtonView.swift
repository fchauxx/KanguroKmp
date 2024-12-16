import SwiftUI

public struct AddButtonView: View {

    // MARK: - Stored Properties
    var buttonLabel: String

    // MARK: - Actions
    var action: SimpleClosure

    public init(_ buttonLabel: String,
                _ action: @escaping SimpleClosure) {
        self.buttonLabel = buttonLabel
        self.action = action
    }

    public var body: some View {

        VStack {
            Button {
                action()
            } label: {
                Text(buttonLabel)
                    .captionWhiteBold()
            }
            .frame(height: 28)
            .frame(minWidth: 55)
            .background(Color.secondaryDarkest)
            .cornerRadius(CornerRadius.md)
        }
    }
}

struct AddButtonView_Previews: PreviewProvider {
    static var previews: some View {
        AddButtonView("Add") {
            print("")
        }
    }
}
