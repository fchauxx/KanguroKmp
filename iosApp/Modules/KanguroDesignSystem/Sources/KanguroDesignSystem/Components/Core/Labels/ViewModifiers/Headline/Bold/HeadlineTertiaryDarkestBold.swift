import SwiftUI

struct HeadlineTertiaryDarkestBold: ViewModifier {

    func body(content: Content) -> some View {
        content
            .font(.museo(.museoSansBold, size: 21))
            .foregroundColor(.tertiaryDarkest)
    }
}
