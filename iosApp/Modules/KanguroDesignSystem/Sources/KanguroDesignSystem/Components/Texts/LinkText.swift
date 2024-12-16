import SwiftUI

public struct LinkText: View, Identifiable {

    // MARK: - Stored Properties
    public let id = UUID()
    var text: String

    // MARK: Actions
    var didTapLink: SimpleClosure

    public init(text: String, didTapLink: @escaping SimpleClosure) {
        self.text = text
        self.didTapLink = didTapLink
    }

    public var body: some View {
        Button {
            didTapLink()
        } label: {
            Text(text)
                .paragraphTertiaryDarkestRegularItalic()
                .underline()
        }
    }
}

struct LinkText_Previews: PreviewProvider {
    static var previews: some View {
        LinkText(text: "+ My link text", didTapLink: {})
    }
}
