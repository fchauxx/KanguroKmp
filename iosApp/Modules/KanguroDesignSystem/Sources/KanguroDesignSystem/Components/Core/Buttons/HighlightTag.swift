import Foundation
import SwiftUI

public struct HighlightTag: View {
    
    private var tag: String
    
    init(tag: String) {
        self.tag = tag
    }
    
    public var body: some View {
        HStack {
            Text(tag)
                .font(.lato(.latoBold, size: 9))
                .foregroundStyle(Color.tertiaryDarkest)
                .padding(InsetSpacing.quarck)
        }
        .background(Color.tertiaryLightest)
        .cornerRadius(CornerRadius.quarck)
    }
}

struct HighlightTag_Previews: PreviewProvider {
    static var previews: some View {
        HighlightTag(tag: "NEW")
    }
}
