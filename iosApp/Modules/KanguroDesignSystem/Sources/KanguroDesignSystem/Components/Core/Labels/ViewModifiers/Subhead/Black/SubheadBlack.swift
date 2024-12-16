import SwiftUI

struct SubheadBlack: ViewModifier {

    func body(content: Content) -> some View {
        content
            .font(.lato(.latoBlack, size: 14))
            .foregroundColor(.primaryDarkest)
    }
}
