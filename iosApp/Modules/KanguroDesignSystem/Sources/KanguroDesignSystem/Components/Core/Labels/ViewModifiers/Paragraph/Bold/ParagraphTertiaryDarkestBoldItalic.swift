import SwiftUI

struct ParagraphTertiaryDarkestBoldItalic: ViewModifier {

    func body(content: Content) -> some View {
        content
            .font(.lato(.latoBoldItalic, size: 13))
            .foregroundColor(.tertiaryDarkest)
    }
}
