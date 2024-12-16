import SwiftUI

struct CalloutSecondaryDarkestRegular: ViewModifier {

    func body(content: Content) -> some View {
        content
            .font(.lato(.latoRegular, size: 14))
            .foregroundColor(.secondaryDarkest)
    }
}
