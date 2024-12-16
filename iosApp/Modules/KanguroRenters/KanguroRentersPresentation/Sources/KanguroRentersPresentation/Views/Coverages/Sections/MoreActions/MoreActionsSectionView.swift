import SwiftUI
import KanguroDesignSystem
import KanguroSharedDomain

public struct MoreActionsSectionView: View {
    
    // MARK: - Property Wrapper
    @Environment(\.appLanguageValue) var language
    
    // MARK: - Actions
    var didTapFileAClaimAction: SimpleClosure
    var didTrackClaimAction: SimpleClosure
    var didEditPolicyDetailsAction: SimpleClosure
    var didChangeAddressAction: SimpleClosure
    var didFAQAction: SimpleClosure
    
    var didTapPhoneNumberAction: SimpleClosure
    var didTapEmailAction: SimpleClosure
    
    // MARK: - Computed Properties
    var lang: String {
        language.rawValue
    }
    
    public var body: some View {
        VStack(alignment: .leading) {
            Text(
                "moreActions.titleLabel.label".localized(lang).uppercased()
            ).captionSecondaryDarkBold()
            
            ActionCardListView(
                cardButtons: [
                    CardButton(
                        content: AnyView(
                            ActionCardButton(
                                title: "moreActions.fileClaim.card".localized(lang),
                                icon: Image.addSquareIcon,
                                style: .primary,
                                didTapAction: didTapFileAClaimAction
                            )
                        )
                    ),
                    CardButton(
                        content: AnyView(
                            AccordionButtonView(
                                title: "moreActions.trackClaim.card".localized(lang),
                                icon: Image.coveredIcon,
                                insideView: AnyView(
                                    makeTrackClaimTextsView()
                                )
                            )
                        )
                    ),
                    CardButton(
                        content: AnyView(
                            ActionCardButton(
                                title: "moreActions.policyDetails.card".localized(lang),
                                icon: Image.editPolicyIcon,
                                style: .primary,
                                didTapAction: didEditPolicyDetailsAction
                            )
                        )
                    ),
                    CardButton(
                        content: AnyView(
                            ActionCardButton(
                                title: "moreActions.changeAddress.card".localized(lang),
                                icon: Image.addressIcon,
                                style: .primary,
                                didTapAction: didChangeAddressAction
                            )
                        )
                    ),
                    CardButton(
                        content: AnyView(
                            ActionCardButton(
                                title: "moreActions.FAQ.card".localized(lang),
                                icon: Image.faqIcon,
                                style: .primary,
                                didTapAction: didFAQAction
                            )
                        )
                    ),
                ]
            )
        }
    }
    
    @ViewBuilder
    private func makeCancelPolicyTextsView() -> some View {
        VStack(alignment: .leading, spacing: InsetSpacing.nano) {
            Text("moreActions.cancelPolicy.text1".localized(lang))
                .paragraphSecondaryMediumRegular()
            
            HighlightedText(text: "moreActions.cancelPolicy.text2".localized(lang),
                            highlightedText: "moreActions.cancelPolicy.highlightedText2".localized(lang),
                            baseStyle: TextStyle(font: .lato(.latoRegular, size: 13),
                                                 color: .secondaryMedium),
                            highlightedStyle: TextStyle(font: .lato(.latoRegular, size: 13),
                                                        color: .tertiaryDarkest,
                                                        underlined: true))
            .onTapGesture {
                didTapPhoneNumberAction()
            }
            
            HighlightedText(text: "moreActions.cancelPolicy.text3".localized(lang),
                            highlightedText: "moreActions.cancelPolicy.highlightedText3".localized(lang),
                            baseStyle: TextStyle(font: .lato(.latoRegular, size: 13),
                                                 color: .secondaryMedium),
                            highlightedStyle: TextStyle(font: .lato(.latoRegular, size: 13),
                                                        color: .tertiaryDarkest,
                                                        underlined: true))
            .onTapGesture {
                didTapEmailAction()
            }
        }
        .padding(.horizontal, InsetSpacing.nano)
        .padding(.bottom, InsetSpacing.xs)
    }
    
    @ViewBuilder
    private func makeTrackClaimTextsView() -> some View {
        VStack {
            HighlightedText(text: "moreActions.trackClaim.contact.text".localized(lang),
                            highlightedText: "rentersclaims@kanguroinsurance.com",
                            baseStyle: TextStyle(font: .lato(.latoRegular, size: 13),
                                                 color: .secondaryMedium),
                            highlightedStyle: TextStyle(font: .lato(.latoRegular, size: 13),
                                                        color: .tertiaryDarkest,
                                                        underlined: true))
            .onTapGesture {
                didTapEmailAction()
            }
        }
        .padding(.horizontal, InsetSpacing.xxs)
        .padding(.bottom, InsetSpacing.xs)
    }
}

struct MoreActionsSectionView_Previews: PreviewProvider {
    static var previews: some View {
        MoreActionsSectionView(didTapFileAClaimAction: {},
                               didTrackClaimAction: {},
                               didEditPolicyDetailsAction: {},
                               didChangeAddressAction: {},
                               didFAQAction: {},
                               didTapPhoneNumberAction: {},
                               didTapEmailAction: {})
    }
}
