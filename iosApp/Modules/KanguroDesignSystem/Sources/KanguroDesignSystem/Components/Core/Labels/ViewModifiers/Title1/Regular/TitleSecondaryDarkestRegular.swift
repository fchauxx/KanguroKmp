import SwiftUI

struct TitleSecondaryDarkestRegular: ViewModifier {

    func body(content: Content) -> some View {
        content
            .font(.raleway(.ralewayRegular, size: 32))
            .foregroundColor(.secondaryDarkest)
    }
}
