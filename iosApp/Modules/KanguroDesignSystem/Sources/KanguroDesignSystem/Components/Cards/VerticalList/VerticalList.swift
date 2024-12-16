import SwiftUI

public struct VerticalListItem: Identifiable {
    
    public let id = UUID()
    let content: AnyView

    public init(content: AnyView) {
        self.content = content
    }
}

public struct VerticalList: View {
    
    // MARK: - Stored Properties
    let verticalItems: [VerticalListItem]
    let spacing: CGFloat

    public init(verticalItems: [VerticalListItem],
                spacing: CGFloat = StackSpacing.nano) {
        self.verticalItems = verticalItems
        self.spacing = spacing
    }
    
    public var body: some View {
        VStack(spacing: spacing) {
            ForEach(verticalItems) { verticalItem in
                verticalItem.content
            }
        }
    }
}

// MARK: - Previews
struct VerticalList_Previews: PreviewProvider {
    
    static var previews: some View {
        VerticalList(verticalItems: [
            VerticalListItem(content: AnyView(VerticalItem(title: "Theft", icon: Image.theftIcon, style: .primary))),
            VerticalListItem(content: AnyView(VerticalItem(title: "Fire", icon: Image.fireIcon, style: .primary))),
            VerticalListItem(content: AnyView(VerticalItem(title: "Smoke", icon: Image.smokeIcon, style: .primary))),
        ])
    }
}
