import SwiftUI

struct CalloutSecondaryDarkestUnderlinedBold: ViewModifier {

    func body(content: Content) -> some View {
        if #available(iOS 16.0, *) {
            content
                .font(.lato(.latoBold, size: 14))
                .foregroundColor(.secondaryDarkest)
                .underline()
        } else {
            content
                .font(.lato(.latoBold, size: 14))
                .foregroundColor(.secondaryDarkest)
        }
    }
}
