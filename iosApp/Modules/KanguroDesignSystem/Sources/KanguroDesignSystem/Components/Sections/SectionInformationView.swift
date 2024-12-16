import SwiftUI

public struct SectionInformationView: View {
    
    // MARK: - Stored Properties
    let headerView: SectionHeaderView
    let contentViewList: [SectionContentView]

    public init(headerView: SectionHeaderView,
                contentViewList: [SectionContentView]) {
        self.headerView = headerView
        self.contentViewList = contentViewList
    }
    
    public var body: some View {
        ZStack {
            VStack(alignment: .leading, spacing: StackSpacing.xxs) {
                headerView
                ForEach(contentViewList) { view in
                    view.content
                }
            }
            .padding(InsetSpacing.xxs)
        }
        .background(Color.white)
        .cornerRadius(CornerRadius.sm)
        .overlay(
            RoundedRectangle(cornerRadius: CornerRadius.sm).stroke(Color.neutralBackground, lineWidth: 1.5)
        )
    }
}

struct SectionInformationView_Previews: PreviewProvider {
    static var previews: some View {

        SectionInformationView(
            headerView: SectionHeaderView(
                icon: Image.cameraIcon,
                title: "Additional Coverage"),
            contentViewList: {
                [
                SectionContentView(content: AnyView(HighlightedText(text: "Your policy is active. \nWith Kanguro you're \nalways safe!",
                                                                    highlightedText: "active",
                                                                    baseStyle: TextStyle(font: .museo(.museoSansThin, size: 24), color: .secondaryDark),
                                                                    highlightedStyle: TextStyle(font: .museo(.museoSansBold, size: 24), color: .tertiaryDarkest))
                )),
                SectionContentView(content: AnyView(ActionCardListView(cardButtons: [
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
                ])))
            ]}()
        )
        .previewDevice(.init(rawValue: "iPhone 14Pro"))
        .previewDisplayName("iPhone 14")
    }
}
