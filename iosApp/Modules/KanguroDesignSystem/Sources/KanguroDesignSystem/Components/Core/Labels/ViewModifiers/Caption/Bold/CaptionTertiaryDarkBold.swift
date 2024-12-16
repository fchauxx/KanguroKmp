import SwiftUI

struct CaptionTertiaryDarkBold: ViewModifier {

    func body(content: Content) -> some View {
        content
            .font(.lato(.latoBold, size: 12))
            .foregroundColor(.tertiaryDark)
    }
}
