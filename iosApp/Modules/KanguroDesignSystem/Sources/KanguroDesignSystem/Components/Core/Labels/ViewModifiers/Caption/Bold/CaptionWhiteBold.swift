import SwiftUI

struct CaptionWhiteBold: ViewModifier {

    func body(content: Content) -> some View {
        content
            .font(.lato(.latoBold, size: 12))
            .foregroundColor(.white)
    }
}
