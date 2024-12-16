import SwiftUI

struct BodyWhiteExtraBold: ViewModifier {

    func body(content: Content) -> some View {
        content
            .font(.lato(.latoBlack, size: 16))
            .foregroundColor(.white)
    }
}
