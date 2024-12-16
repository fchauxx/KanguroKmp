import SwiftUI

struct ParagraphSecondaryDarkestBlack: ViewModifier {

    func body(content: Content) -> some View {
        content
            .font(.lato(.latoBlack, size: 13))
            .foregroundColor(.secondaryDarkest)
    }
}
