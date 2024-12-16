import SwiftUI

struct Paragraph2SecondaryMediumRegular: ViewModifier {

    func body(content: Content) -> some View {
        content
            .font(.lato(.latoRegular, size: 12.8))
            .foregroundColor(.secondaryMedium)
    }
}
