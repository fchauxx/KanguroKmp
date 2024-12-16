import SwiftUI

struct FootnoteNeutralMediumBold: ViewModifier {

    func body(content: Content) -> some View {
        content
            .font(.lato(.latoBold, size: 11))
            .foregroundColor(.neutralMedium)
    }
}
