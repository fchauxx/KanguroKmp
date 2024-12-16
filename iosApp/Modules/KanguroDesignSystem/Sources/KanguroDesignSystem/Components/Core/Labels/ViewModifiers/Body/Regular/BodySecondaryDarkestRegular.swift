import SwiftUI

struct BodySecondaryDarkestRegular: ViewModifier {

    func body(content: Content) -> some View {
        content
            .font(.lato(.latoRegular, size: 16))
            .foregroundColor(.secondaryDarkest)
    }
}
