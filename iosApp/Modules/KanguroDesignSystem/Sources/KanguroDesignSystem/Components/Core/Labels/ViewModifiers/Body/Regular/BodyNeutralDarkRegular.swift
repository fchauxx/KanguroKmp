import SwiftUI

struct BodyNeutralDarkRegular: ViewModifier {

    func body(content: Content) -> some View {
        content
            .font(.lato(.latoRegular, size: 16))
            .foregroundColor(.neutralDark)
    }
}
