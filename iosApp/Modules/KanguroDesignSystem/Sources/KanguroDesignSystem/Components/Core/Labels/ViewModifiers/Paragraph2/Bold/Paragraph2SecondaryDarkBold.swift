import SwiftUI

struct Paragraph2SecondaryDarkBold: ViewModifier {

    func body(content: Content) -> some View {
        content
            .font(.lato(.latoBold, size: 12.8))
            .foregroundColor(.secondaryDark)
    }
}
