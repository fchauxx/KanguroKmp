import SwiftUI

struct Footnote2SecondaryMediumRegular: ViewModifier {

    func body(content: Content) -> some View {
        content
            .font(.lato(.latoRegular, size: 10.24))
            .foregroundColor(.secondaryMedium)
    }
}
