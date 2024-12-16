import SwiftUI

struct Title3SecondaryDarkestBold: ViewModifier {

    func body(content: Content) -> some View {
        content
            .font(.raleway(.ralewayBold, size: 24))
            .foregroundColor(.secondaryDarkest)
    }
}
