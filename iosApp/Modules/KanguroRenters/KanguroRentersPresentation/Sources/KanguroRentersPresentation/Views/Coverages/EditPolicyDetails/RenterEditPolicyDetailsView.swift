import SwiftUI
import KanguroRentersDomain
import KanguroDesignSystem

public struct RenterEditPolicyDetailsView: View {
    
    // MARK: - Stored Properties
    @ObservedObject var viewModel: RenterEditPolicyDetailsViewModel
    var backAction: SimpleClosure
    
    // MARK: - Wrapped Properties
    @State private var popUpType: PlanSummaryItemCategory? = nil
    @Environment(\.appLanguageValue) var language
    
    // MARK: - Computed Properties
    var lang: String {
        language.rawValue
    }
    private var liabilityItems: [PriceItem] {
        if viewModel.liabilitiesOptions.isEmpty { return [] }
        return viewModel.liabilitiesOptions.compactMap { item in
            let item = PriceItem(value: item.value, id: Int(item.id))
            return item
        }
    }
    private var deductibleItems: [PriceItem] {
        if viewModel.deductibleOptions.isEmpty { return [] }
        return viewModel.deductibleOptions.compactMap { item in
            let item = PriceItem(value: item.value, id: Int(item.id))
            return item
        }
    }
    
    // MARK: - Initializers
    public init(viewModel: RenterEditPolicyDetailsViewModel, backAction: @escaping SimpleClosure) {
        self.viewModel = viewModel
        self.backAction = backAction
    }
    
    public var body: some View {
        if !viewModel.requestError.isEmpty {
            createErrorDataView()
        } else if viewModel.isLoading {
            LoadingView()
        } else {
            ZStack {
                //TODO: ScrollView (any type of scrolling) + sheet presentation gets a bug
                ScrollView {
                    VStack(spacing: StackSpacing.md) {
                        HorizontalAvatarHeader(title: "editPolicyDetails.header.label".localized(lang))
                        
                        makeLiabilitySection()
                        
                        makeYourBelongingsSection()
                        
                        makeDeductibleSection()
                    } //: VStack
                }
                .padding([.top, .horizontal], InsetSpacing.md)
                .scrollIndicators(.hidden)
                
                if popUpType != nil {
                    makePopUp()
                }
            } //: ZStack
        }
    }
    
    // MARK: - View Methods
    @ViewBuilder
    private func createErrorDataView() -> some View {
        DataStatusResponseView(image: Image.errorImage,
                               title: "scheduled.error1.state".localized(lang),
                               titleStyle: TextStyle(font: .museo(.museoSansBold, size: 21),
                                                     color: .secondaryDarkest),
                               subtitle: "scheduled.error2.state".localized(lang),
                               subtitleStyle: TextStyle(font: .lato(.latoRegular, size: 14),
                                                        color: .tertiaryDarkest,
                                                        underlined: true))
        .onTapGesture {
            viewModel.getData()
        }
    }
    
    @ViewBuilder
    private func makePopUp() -> some View {
        switch popUpType {
        case .liability:
            PopUpInfoView(data: PopUpData(title: "editPolicyDetails.liability.label".localized(lang),
                                          description: "editPolicyDetails.liability.popup.description.label".localized(lang)),
                          closeAction: {
                self.popUpType = nil
            }, didTapPopUpEmailAction: {_ in })
        case .deductible:
            PopUpInfoView(data: PopUpData(title: "editPolicyDetails.deductible.label".localized(lang),
                                          description: "editPolicyDetails.deductible.popup.description.label".localized(lang)),
                          closeAction: {
                self.popUpType = nil
            }, didTapPopUpEmailAction: {_ in })
        default:
            EmptyView()
        }
    }
    
    @ViewBuilder
    private func makeLiabilitySection() -> some View {
        VStack(spacing: StackSpacing.xxs) {
            InformationTitle(text: "editPolicyDetails.liability.label".localized(lang),
                             textStyle: TextStyle(font: .museo(.museoSansBold,
                                                               size: 21),
                                                  color: .secondaryDark),
                             tapAction: {
                self.popUpType = .liability
            })
            
            PriceItemsListView(items: liabilityItems) { selectedId in
                guard let currentValue = liabilityItems.first(where: {
                    $0.id == selectedId
                })?.value else { return }
                viewModel.updatePlanSummaryValue(type: .liability, newValue: currentValue)
            }
            
            Divider()
        }
    }
    
    @ViewBuilder private func makeYourBelongingsSection() -> some View {
        makeTextsSection(title: "editPolicyDetails.belongings.title.label".localized(lang),
                         description: "editPolicyDetails.belongings.description.label".localized(lang))
        
        if let personalProperty = viewModel.personalProperty {
            CustomProgressView(minValue: personalProperty.minimum,
                               maxValue: personalProperty.maximum) { value in
                viewModel.updatePlanSummaryValue(type: .personalProperty, newValue: value)
            }
                               .padding(.horizontal, InsetSpacing.quarck)
        }
        
        VStack(spacing: InsetSpacing.xxs) {
            makeTextsSection(title: "editPolicyDetails.lossOfUse.title.label".localized(lang),
                             subtitle: viewModel.planSummary?.lossOfUse?.value.getCurrencyFormatted(),
                             description: "editPolicyDetails.lossOfUse.description.label".localized(lang))
            Divider()
        }
    }
    
    @ViewBuilder
    private func makeDeductibleSection() -> some View {
        VStack(spacing: InsetSpacing.md) {
            InformationTitle(text: "editPolicyDetails.deductible.label".localized(lang),
                             textStyle: TextStyle(font: .museo(.museoSansBold,
                                                               size: 25),
                                                  color: .secondaryDark),
                             tapAction: {
                popUpType = .deductible
            })
            
            PriceItemsListView(items: deductibleItems) { selectedId in
                guard let currentValue = deductibleItems.first(where: {
                    $0.id == selectedId
                })?.value else { return }
                viewModel.updatePlanSummaryValue(type: .liability,
                                                 newValue: currentValue)
            }
            
            makeTextsSection(title: "editPolicyDetails.medicalPayments.title.label".localized(lang),
                             description: "editPolicyDetails.medicalPayments.description.label".localized(lang),
                             isLightStyle: true)
            
            makeTextsSection(title: "editPolicyDetails.hurricane.title.label".localized(lang),
                             description: "editPolicyDetails.hurricane.description.label".localized(lang),
                             isLightStyle: true)
            
            if let policyValue = viewModel.pricing?.currentPolicyValue,
               let totalPrice = viewModel.pricing?.billingCycleEndorsementPolicyValue {
                PriceSubmitButtonView(buttonPrice: policyValue,
                                      bottomLabelPrice: totalPrice,
                                      isLoading: viewModel.isUpdatingPricing,
                                      action: {
                    viewModel.updateRenterPolicy()
                })
                .padding(.bottom, 50)
            }
        }
    }
    
    // MARK: - Auxiliary Methods
    @ViewBuilder
    private func makeTextsSection(title: String,
                                  subtitle: String? = nil,
                                  description: String,
                                  isLightStyle: Bool = false) -> some View {
        VStack(spacing: StackSpacing.xxxs) {
            Text(title)
                .font(isLightStyle ? .museo(.museoSans, size: 15) : .museo(.museoSansBold, size: 20))
                .foregroundStyle(Color.secondaryDark)
            if let subtitle {
                Text(subtitle)
                    .headlineTertiaryDarkestRegular()
            }
            Text(description)
                .font(.lato(.latoRegular, size: 13))
                .foregroundStyle(isLightStyle ? Color.secondaryLight : Color.secondaryMedium)
        }
    }
}
