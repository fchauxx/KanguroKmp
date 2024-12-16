import SwiftUI
import KanguroRentersDomain
import KanguroDesignSystem
import KanguroSharedDomain

public struct ScheduleNewItemView: View, PresenterNavigationProtocol {

    // MARK: - Dependencies
    @ObservedObject var viewModel: ScheduleNewItemViewModel
    
    // MARK: - Property Wrappers
    @State var itemName: String
    @State var itemValue: String
    @State private var typingTimer: Timer? = nil
    @Environment(\.appLanguageValue) var language
    
    // MARK: - Stored Properties
    var categoryImage: Image
    var selectedCategory: String
    
    // MARK: - Computed Properties
    var lang: String {
        language.rawValue
    }
    private var isTextfieldsEmpty: Bool {
        return itemValue.isEmpty || itemName.isEmpty
    }
    private var buttonText: String {
        "\("scheduled.submit.newItem.label".localized(lang)) (\(viewModel.newEndorsementPolicyPrice)\("scheduled.submit.newItem.petMonth.label".localized(lang).uppercased()))"
    }
    private var shouldSubmitItemBeDisabled: Bool {
        return isTextfieldsEmpty || !itemValue.isNumber || viewModel.isPricingLoading
    }
    
    // MARK: - Actions
    var backAction: SimpleClosure?
    var closeAction: SimpleClosure?
    var didCreatedNewItem: SimpleClosure
    
    public init(viewModel: ScheduleNewItemViewModel,
                categoryImage: Image,
                selectedCategory: String,
                itemName: String = "",
                itemValue: String = "",
                backAction: SimpleClosure? = nil,
                closeAction: SimpleClosure? = nil,
                didCreatedNewItem: @escaping SimpleClosure) {
        self.viewModel = viewModel
        self.categoryImage = categoryImage
        self.selectedCategory = selectedCategory
        self.itemName = itemName
        self.itemValue = itemValue
        self.backAction = backAction
        self.closeAction = closeAction
        self.didCreatedNewItem = didCreatedNewItem
    }
    
    public var body: some View {
        VStack {
            PresenterNavigationHeaderView(backAction: backAction)
                .padding(.horizontal, InsetSpacing.md)
                .padding(.top, InsetSpacing.xs)
                .padding(.bottom, InsetSpacing.xxs)
            VStack {
                HStack(spacing: InlineSpacing.xxs) {
                    categoryImage
                        .resizable()
                        .frame(width: IconSize.md_2, height: IconSize.md_2)
                        .padding(.leading)
                    Text(selectedCategory)
                        .headlineSecondaryDarkestBold()
                    Spacer()
                }
                .frame(maxWidth: .infinity)
                .frame(height: 73)
                .background(Color.neutralBackground)
                .cornerRadius(CornerRadius.sm)
                .padding(.bottom, InsetSpacing.xs)
                
                CustomTextfieldView(fieldTitle: "textfield1.title.label".localized(lang),
                                    placeholder: "textfield1.placeholder.label".localized(lang),
                                    value: $itemName
                )
                .onChange(of: itemName) { newValue in
                    viewModel.temporaryScheduledItem.name = newValue
                }
                CustomTextfieldView(fieldTitle: "textfield2.title.label".localized(lang),
                                    placeholder: "textfield2.placeholder.label".localized(lang),
                                    keyboardType: .numberPad,
                                    value: $itemValue
                )
                .onChange(of: itemValue) { newValue in
                    viewModel.isPricingLoading = (newValue.isEmpty || !newValue.isNumber) ? false : true
                    typingTimer?.invalidate()
                    typingTimer = Timer.scheduledTimer(withTimeInterval: 1.0, repeats: false) { _ in
                        viewModel.updateNewItemPricing()
                    }
                    viewModel.temporaryScheduledItem.valuation = Double(newValue)
                }
                Spacer()
                PrimaryButtonView(buttonText,
                                  height: HeightSize.lg,
                                  isDisabled: shouldSubmitItemBeDisabled,
                                  isLoading: viewModel.isPricingLoading) {
                    viewModel.createNewScheduledItem()
                }
                if !shouldSubmitItemBeDisabled {
                    Text("\("scheduled.submit.newItem.footer.label".localized(lang)) \(viewModel.newDifferenceValuePrice)")
                        .captionSecondaryDarkRegular()
                }
            }
            .padding([.leading, .trailing], InsetSpacing.md)
        }
        .onChange(of: viewModel.scheduledItemCreationSucceed) { _ in
            if viewModel.scheduledItemCreationSucceed {
                didCreatedNewItem()
            }
        }
        .navigationBarBackButtonHidden(true)
    }
}

struct ScheduleNewItemView_Previews: PreviewProvider {
    
    static var previews: some View {
        ScheduleNewItemView(viewModel: ScheduleNewItemViewModel(
            updatePricingService: nil,
            createScheduledItemService: nil,
            scheduledItemPricing: Pricing(),
            policyId: "",
            requestError: ""
        ),
                            categoryImage: Image.addCircleIcon,
                            selectedCategory: "Jewelry",
                            backAction: {}, didCreatedNewItem: {})
    }
}
