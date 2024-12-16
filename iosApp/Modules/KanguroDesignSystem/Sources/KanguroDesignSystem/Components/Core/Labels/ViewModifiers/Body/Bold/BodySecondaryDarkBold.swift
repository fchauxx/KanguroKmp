import SwiftUI

struct BodySecondaryDarkBold: ViewModifier {

    func body(content: Content) -> some View {
        content
            .font(.lato(.latoBold, size: 16))
            .foregroundColor(.secondaryDark)
    }
}
