import SwiftUI

struct CaptionSecondaryDarkRegular: ViewModifier {

    func body(content: Content) -> some View {
        content
            .font(.lato(.latoRegular, size: 12))
            .foregroundColor(.secondaryDark)
    }
}

struct CaptionSecondaryDarkRegularBig: ViewModifier {

    func body(content: Content) -> some View {
        content
            .font(.lato(.latoRegular, size: 16))
            .foregroundColor(.secondaryDark)
    }
}
