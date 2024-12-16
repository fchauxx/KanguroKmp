import SwiftUI

struct FootnoteSecondaryBlack: ViewModifier {

    func body(content: Content) -> some View {
        content
            .font(.lato(.latoBlack, size: 11))
            .foregroundColor(.secondaryLight)
    }
}
