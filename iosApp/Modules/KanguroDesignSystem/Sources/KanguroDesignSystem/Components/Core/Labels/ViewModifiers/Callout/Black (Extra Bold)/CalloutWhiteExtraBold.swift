import SwiftUI

struct CalloutWhiteExtraBold: ViewModifier {

    func body(content: Content) -> some View {
        content
            .font(.lato(.latoBlack, size: 14))
            .foregroundColor(.white)
    }
}
