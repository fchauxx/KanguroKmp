import SwiftUI

public struct SectionContentView: Identifiable {

    public let id = UUID()
    let content: AnyView

    public init(content: AnyView) {
        self.content = content
    }
}
