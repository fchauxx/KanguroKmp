import SwiftUI

struct Title3SecondaryDarkestRegular: ViewModifier {

    func body(content: Content) -> some View {
        content
            .font(.raleway(.ralewayRegular, size: 24))
            .foregroundColor(.secondaryDarkest)
    }
}
