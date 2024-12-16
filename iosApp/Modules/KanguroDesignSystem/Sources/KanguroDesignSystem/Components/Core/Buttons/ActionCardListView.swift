import SwiftUI

public struct CardButton: Identifiable {
    
    public let id = UUID()
    let content: AnyView

    public init(content: AnyView) {
        self.content = content
    }
}

public struct ActionCardListView: View {
    
    // MARK: - Stored Properties
    let cardButtons: [CardButton]
    let spacing: CGFloat

    public init(cardButtons: [CardButton], 
                spacing: CGFloat = StackSpacing.nano) {
        self.cardButtons = cardButtons
        self.spacing = spacing
    }
    
    public var body: some View {
        VStack(spacing: spacing) {
            ForEach(cardButtons) { cardButton in
                cardButton.content
            }
        }
    }
}

// MARK: - Previews
struct ActionCardListView_Previews: PreviewProvider {
    
    static var previews: some View {
        ActionCardListView(cardButtons: [
            CardButton(content: AnyView(AccordionButtonView(title: "Plan summary"))),
            CardButton(content: AnyView(ActionCardButton(title: "What is covered",
                                                         icon: Image.cameraIcon,
                                                         style: .primary,
                                                         didTapAction: {}))),
            CardButton(content: AnyView(AccordionButtonView(title: "Documentation",
                                                            icon: Image.fileIcon))),
            CardButton(content: AnyView(ActionCardButton(title: "Edit policy details",
                                                         icon: Image.cameraIcon,
                                                         style: .primary,
                                                         didTapAction: {})))
        ])
    }
}
