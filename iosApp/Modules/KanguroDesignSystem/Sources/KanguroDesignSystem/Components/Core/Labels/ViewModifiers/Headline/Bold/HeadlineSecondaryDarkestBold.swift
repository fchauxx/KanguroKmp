import SwiftUI

struct HeadlineSecondaryDarkestBold: ViewModifier {

    func body(content: Content) -> some View {
        content
            .font(.raleway(.ralewayBold, size: 21))
            .foregroundColor(.secondaryDarkest)
    }
}
