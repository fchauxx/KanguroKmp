import SwiftUI

struct CaptionSecondaryDarkestRegular: ViewModifier {

    func body(content: Content) -> some View {
        content
            .font(.lato(.latoRegular, size: 12))
            .foregroundColor(.secondaryDarkest)
    }
}
