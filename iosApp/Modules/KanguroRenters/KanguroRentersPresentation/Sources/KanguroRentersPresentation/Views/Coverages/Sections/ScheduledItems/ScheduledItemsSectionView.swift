import SwiftUI
import KanguroRentersDomain
import KanguroDesignSystem

public struct ScheduledItemsSectionView: View {
    
    // MARK: - Property Wrappers
    @Environment(\.appLanguageValue) var language
    
    // MARK: - Stored Properties
    public let totalValue: Double
    
    // MARK: - Computed Properties
    var totalValueText: String {
        totalValue.getCurrencyFormatted()
    }
    var lang: String {
        language.rawValue
    }

    // MARK: - Actions
    var didTapEditScheduledItemsAction: SimpleClosure

    public init(totalValue: Double,
                didTapEditScheduledItemsAction: @escaping SimpleClosure) {
        self.totalValue = totalValue
        self.didTapEditScheduledItemsAction = didTapEditScheduledItemsAction
    }
    
    public var body: some View {
        ZStack {
            SectionInformationView(
                headerView: SectionHeaderView(
                    icon: Image.additionalPartiesSectionIcon,
                    title: "scheduledItems.title.section".localized(lang)),
                contentViewList: [
                    SectionContentView(content: AnyView(
                        VStack(alignment: .leading, spacing: StackSpacing.xxs) {
                            HighlightedText(
                                text: "scheduledItems.valueDescription.label".localized(lang).replacingOccurrences(of: "totalValueText", with: totalValueText),
                                highlightedText: totalValueText,
                                baseStyle: TextStyle(font: .museo(.museoSansThin, size: 24),
                                                     color: .secondaryDark),
                                highlightedStyle: TextStyle(font: .museo(.museoSansBold, size: 24),
                                                            color: .tertiaryDarkest))
                            .padding(.trailing, InsetSpacing.lg)
                        }
                    )),
                    SectionContentView(
                       content: AnyView(ActionCardButton(title: "renters.policy.myScheduledItems.label".localized(lang),
                                                         icon: Image.fileIcon,
                                                         style: .primary, 
                                                         didTapAction: {
                                                             didTapEditScheduledItemsAction()
                                                         }))
                    )
                ])
        }
    }
}

struct ScheduledItemsSectionView_Previews: PreviewProvider {
    static var previews: some View {
        ScheduledItemsSectionView(totalValue: 10203,
                                  didTapEditScheduledItemsAction: {})
    }
}
