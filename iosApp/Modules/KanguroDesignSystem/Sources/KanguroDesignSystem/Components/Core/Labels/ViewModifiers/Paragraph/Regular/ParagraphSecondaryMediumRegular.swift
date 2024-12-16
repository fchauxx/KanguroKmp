import SwiftUI

struct ParagraphSecondaryMediumRegular: ViewModifier {

    func body(content: Content) -> some View {
        content
            .font(.lato(.latoRegular, size: 13))
            .foregroundColor(.secondaryMedium)
    }
}
