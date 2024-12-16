import SwiftUI
import KanguroDesignSystem

public struct EditAdditionalCoverageOptionsView: View {
    
    // MARK: - Property Wrappers
    @Environment(\.appLanguageValue) var language
    
    // MARK: - Dependencies
    @ObservedObject var viewModel: EditAdditionalCoverageOptionsViewModel
    
    // MARK: - Computed Properties
    var lang: String {
        language.rawValue
    }
    
    //MARK: - Actions
    var backAction: SimpleClosure

    
    public init(viewModel: EditAdditionalCoverageOptionsViewModel,
                backAction: @escaping SimpleClosure) {
        self.viewModel = viewModel
        self.backAction = backAction
    }
    
    public var body: some View {
        VStack(spacing: InsetSpacing.xs) {
            PresenterNavigationHeaderView(closeAction: backAction)
            
            HorizontalAvatarHeader(title: "editAdditionalCoverage.header.label".localized(lang))
            
            ScrollView {
                VStack(spacing: InsetSpacing.xxs) {
                    ForEach($viewModel.cardsData, id: \.self) { data in
                        ItemSelectionCardView(data: data)
                    }
                }
            }.scrollIndicators(.hidden)
            
            if let policyValue = viewModel.pricing?.currentPolicyValue,
               let totalPrice = viewModel.pricing?.billingCycleEndorsementPolicyValue {
                PriceSubmitButtonView(buttonPrice: policyValue,
                                      bottomLabelPrice: totalPrice,
                                      action: {
                    viewModel.updateRenterPolicy()
                })
            }
        } //: Main VStack
        .padding(.horizontal, InsetSpacing.md)
        .onChange(of: viewModel.cardsData) { cardData in
            viewModel.updatePolicyPricing()
        }
    }
}
