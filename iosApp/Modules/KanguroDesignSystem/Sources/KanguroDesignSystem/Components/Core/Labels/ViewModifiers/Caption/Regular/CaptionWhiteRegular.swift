import SwiftUI

struct CaptionWhiteRegular: ViewModifier {

    func body(content: Content) -> some View {
        content
            .font(.lato(.latoRegular, size: 12))
            .foregroundColor(.white)
    }
}
