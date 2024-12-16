import SwiftUI

struct FootnoteNeutralMediumRegular: ViewModifier {

    func body(content: Content) -> some View {
        content
            .font(.lato(.latoRegular, size: 11))
            .foregroundColor(.neutralMedium)
    }
}
