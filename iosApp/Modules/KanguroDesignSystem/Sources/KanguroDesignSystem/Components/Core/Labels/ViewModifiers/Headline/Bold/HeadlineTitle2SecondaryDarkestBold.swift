import SwiftUI

struct HeadlineTitle2SecondaryDarkestBold: ViewModifier {

    func body(content: Content) -> some View {
        content
            .font(.lato(.latoBold, size: 12))
            .foregroundColor(.secondaryDarkest)
    }
}
