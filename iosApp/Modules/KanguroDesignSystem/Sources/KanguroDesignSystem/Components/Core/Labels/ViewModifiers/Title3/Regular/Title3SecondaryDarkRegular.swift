import SwiftUI

struct Title3SecondaryDarkRegular: ViewModifier {

    func body(content: Content) -> some View {
        content
            .font(.museo(.museoSans, size: 24))
            .foregroundColor(.secondaryDark)
    }
}
