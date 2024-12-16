import SwiftUI

struct HeadlineTitle1SecondaryDarkestBold: ViewModifier {

    func body(content: Content) -> some View {
        content
            .font(.museo(.museoSans, size: 21))
            .foregroundColor(.secondaryDarkest)
    }
}
