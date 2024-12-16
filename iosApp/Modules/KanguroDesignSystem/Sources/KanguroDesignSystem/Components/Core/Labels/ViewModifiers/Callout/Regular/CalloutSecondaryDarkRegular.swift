import SwiftUI

struct CalloutSecondaryDarkRegular: ViewModifier {

    func body(content: Content) -> some View {
        content
            .font(.lato(.latoRegular, size: 14))
            .foregroundColor(.secondaryDark)
    }
}
