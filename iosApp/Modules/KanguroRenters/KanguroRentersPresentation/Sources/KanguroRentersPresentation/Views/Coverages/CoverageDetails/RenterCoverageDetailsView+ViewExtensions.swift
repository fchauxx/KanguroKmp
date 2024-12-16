import SwiftUI
import KanguroRentersDomain
import KanguroSharedDomain
import KanguroDesignSystem

// MARK: - Content Views
public extension RenterCoverageDetailsView {
    
    @ViewBuilder
    func createContentView() -> some View {
        VStack(spacing: StackSpacing.xs) {
            createNameHeaderView()
            
            if let policyStatus = viewModel.policy.status,
               let planSummary = viewModel.policy.planSummary,
               let startDate = viewModel.policy.startAt,
               let renewDate = viewModel.policy.endAt,
               let documents = viewModel.documents {
                RentersInsuranceSectionView(
                    renewDate: renewDate,
                    startDate: startDate,
                    policyStatus: policyStatus,
                    planSummary: planSummary,
                    documents: documents,
                    didTapEditPolicyDetailsAction: { self.popUpData = editPolicyPopUpData },
                    didTapWhatisCoveredAction: didTapWhatisCoveredAction,
                    didTapDocumentAction: didTapDocumentAction
                )
            }
            
            if let additionalCoveragesViewDataList {
                AdditionalCoverageSection(
                    additionalCoveragesDataList: additionalCoveragesViewDataList,
                    didTapEditAdditionalCoverageAction: {
                        self.popUpData = editPolicyPopUpData
                    },
                    didChangePopUpContent: { popUpData in
                        self.popUpData = popUpData
                    }
                )
            }
            
            if let totalValue = viewModel.scheduledItemsValue {
                ScheduledItemsSectionView(totalValue: totalValue,
                                          didTapEditScheduledItemsAction: didTapMyScheduledItemsAction)
            }
            
            if let additionalParties = viewModel.additionalParties {
                AdditionalPartiesSection(parties: additionalParties,
                                         didTapEditAdditionalPartiesAction: {
                    self.popUpData = editPolicyPopUpData
                })
            }
            
            if let payment = viewModel.policy.payment {
                PaymentSectionView(payment: payment,
                                   didTapBillingPreferencesAction: didTapBillingPreferencesAction)
            }
            
            MoreActionsSectionView(
                didTapFileAClaimAction: {
                    self.popUpData = fileClaimUpUpData
                },
                didTrackClaimAction: didTapTrackYourClaimAction,
                didEditPolicyDetailsAction: {
                    self.popUpData = editPolicyPopUpData
                },
                didChangeAddressAction: {
                    self.popUpData = editPolicyPopUpData
                },
                didFAQAction: didTapFaqAction,
                didTapPhoneNumberAction: didTapPhoneNumberAction,
                didTapEmailAction: didTapClaimEmailAction
            )
        }
        .padding(.horizontal, InsetSpacing.xs)
    }
    
    @ViewBuilder
    func createNameHeaderView() -> some View {
        VStack(spacing: 8) {
            HighlightedText(text: "coverageDetails.hello.header".localized(lang).replacingOccurrences(of: "username", with: viewModel.userName ?? ""),
                            highlightedText: viewModel.userName,
                            baseStyle: TextStyle(font: .museo(.museoSansBold, size: 32),
                                                 color: .secondaryDarkest),
                            highlightedStyle: TextStyle(font: .museo(.museoSansBold, size: 32),
                                                        color: .primaryDarkest))
            
            VStack {
                Text(dwellingTypeText).calloutSecondaryDarkRegular()
                
                if let addressText {
                    Text(addressText).calloutSecondaryDarkRegular()
                }
                
                if let policyNumber = viewModel.policy.policyExternalId {
                    Text("sectionHeader.label.policyNumber".localized(lang) + "\(policyNumber)")
                        .padding([.top], StackSpacing.xxxs)
                        .font(.lato(.latoRegular,size: 12))
                        .foregroundColor(.secondaryMedium)
                }
            }
        }
    }
}
