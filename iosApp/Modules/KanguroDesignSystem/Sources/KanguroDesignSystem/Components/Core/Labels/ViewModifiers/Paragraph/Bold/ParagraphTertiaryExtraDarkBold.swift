import SwiftUI

struct ParagraphTertiaryExtraDarkBold: ViewModifier {

    func body(content: Content) -> some View {
        content
            .font(.lato(.latoBold, size: 13))
            .foregroundColor(.tertiaryExtraDark)
    }
}
