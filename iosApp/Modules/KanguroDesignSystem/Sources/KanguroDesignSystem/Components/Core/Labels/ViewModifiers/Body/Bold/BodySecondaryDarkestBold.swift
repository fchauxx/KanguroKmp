import SwiftUI

struct BodySecondaryDarkestBold: ViewModifier {

    func body(content: Content) -> some View {
        content
            .font(.lato(.latoBold, size: 16))
            .foregroundColor(.secondaryDarkest)
    }
}
