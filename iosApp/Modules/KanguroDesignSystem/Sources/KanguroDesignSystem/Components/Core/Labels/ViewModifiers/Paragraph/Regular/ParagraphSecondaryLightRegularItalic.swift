import SwiftUI

struct ParagraphSecondaryLightRegularItalic: ViewModifier {

    func body(content: Content) -> some View {
        content
            .font(.lato(.latoRegularItalic, size: 13))
            .foregroundColor(.secondaryLight)
    }
}
