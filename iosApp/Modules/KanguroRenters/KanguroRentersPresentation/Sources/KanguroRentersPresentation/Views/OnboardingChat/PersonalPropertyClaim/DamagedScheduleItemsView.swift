import SwiftUI
import KanguroDesignSystem
import KanguroChatbotDomain

public struct DamagedScheduleItemsView: View {
    
    // MARK: - Wrapped Properties
    #warning("criar viewModel quando tivermos integraçao")
    //@ObservedObject var viewModel: EditScheduleItemsViewModel
    @Environment(\.appLanguageValue) var language
    @State var items: [KeyValueLabelData] = []
    @State private var selectedItems: [KeyValueLabelData] = []
    
    // MARK: - Actions
    var closeAction: SimpleClosure
    var doneAction: SimpleClosure
    
    // MARK: - Computed Properties
    var lang: String {
        language.rawValue
    }
    
    // MARK: - Initializers
    public init(
        //viewModel: OnboardingChatViewModel
        items: [KeyValueLabelData],
        closeAction: @escaping SimpleClosure,
        doneAction: @escaping SimpleClosure
    ) {
        //self.viewModel = viewModel
        self.items = items
        self.closeAction = closeAction
        self.doneAction = doneAction
    }
    
    
    public var body: some View {
        VStack(spacing: InsetSpacing.xs) {
            PresenterNavigationHeaderView(closeAction: closeAction)
                .padding(.horizontal, InsetSpacing.xxs)
            
            makeContent()
                .padding(.horizontal, InsetSpacing.md)
        }
    }
    
    @ViewBuilder
    private func makeContent() -> some View {
        VStack(alignment: .leading, spacing: InsetSpacing.md) {
            VStack(alignment: .leading, spacing: InsetSpacing.xxs) {
                Text("renters.chat.propertyClaims.title".localized(lang).uppercased())
                    .bodySecondaryDarkBold()
                
                Text("renters.chat.propertyClaims.scheduledItems".localized(lang))
                    .title3SecondaryDarkestBold()
            }
            
            Text("renters.chat.propertyClaims.subtitle".localized(lang))
                .footnoteNeutralMediumRegular()
            
            ForEach(items) { itemData in
                makeCheckboxItem(data: itemData)
            }
            
            Spacer()
            
            PrimaryButtonView("common.done.label".localized(lang),
                              height: HeightSize.lg,
                              isDisabled: false,
                              {
                doneAction()
            })
            .padding(.bottom, InsetSpacing.md)
        }
    }
    
    @ViewBuilder
    private func makeCheckboxItem(data: KeyValueLabelData) -> some View {
        HStack(spacing: InsetSpacing.xxs) {
            CheckboxView(size: IconSize.sm,
                         didSelectAction: {
                selectedItems.append(data)
            }, didDiselectAction: {
                if let index = selectedItems.firstIndex(of: data) {
                    selectedItems.remove(at: index)
                }
            })
            
            VStack(alignment: .leading, spacing: 0) {
                Text(data.key)
                    .bodySecondaryDarkestBold()
                Text(data.value)
                    .bodySecondaryDarkBold()
            }
        }
    }
}

#Preview {
    DamagedScheduleItemsView(items: [
        KeyValueLabelData(key: "Painting",
                          value: 1500.getCurrencyFormatted()),
        KeyValueLabelData(key: "Bike",
                          value: 1500.getCurrencyFormatted()),
        KeyValueLabelData(key: "Macbook",
                          value: 1500.getCurrencyFormatted()),
        KeyValueLabelData(key: "Guitar",
                          value: 1500.getCurrencyFormatted()),
        KeyValueLabelData(key: "Eletric Scooter",
                          value: 1500.getCurrencyFormatted())
    ], closeAction: {}, doneAction: {})
}
