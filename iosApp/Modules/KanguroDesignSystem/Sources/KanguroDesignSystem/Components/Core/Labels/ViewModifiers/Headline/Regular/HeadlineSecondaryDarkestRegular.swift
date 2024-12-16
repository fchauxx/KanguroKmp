import SwiftUI

struct HeadlineSecondaryDarkestRegular: ViewModifier {

    func body(content: Content) -> some View {
        content
            .font(.raleway(.ralewayRegular, size: 21))
            .foregroundColor(.secondaryDarkest)
    }
}
