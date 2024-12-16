import SwiftUI

struct TitleSecondaryDarkestBold: ViewModifier {

    func body(content: Content) -> some View {
        content
            .font(.raleway(.ralewayBold, size: 32))
            .foregroundColor(.secondaryDarkest)
    }
}
