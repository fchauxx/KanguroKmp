import SwiftUI

struct HeadlineTertiaryDarkestRegular: ViewModifier {

    func body(content: Content) -> some View {
        content
            .font(.museo(.museoSans, size: 21))
            .foregroundColor(.tertiaryDarkest)
    }
}
