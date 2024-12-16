import SwiftUI
import KanguroDesignSystem
import KanguroRentersDomain
import KanguroSharedDomain

public struct RentersDashboardView: View {
    
    // MARK: - Wrapped Properties
    @ObservedObject var viewModel: RentersDashboardViewModel
    @Environment(\.appLanguageValue) var language
    @State var popUpData: PopUpData?

    // MARK: - Computed Properties
    var lang: String {
        language.rawValue
    }
    
    // MARK: - Stored Properties
    //temporary until we have edits enabled
    var fileClaimPopUpData: PopUpData {
        PopUpData(title: "moreActions.fileClaim.card".localized(lang),
                  description: "dashboard.fileClaim.popup.label".localized(lang),
                  highlightedData: (text: "rentersclaims@kanguroinsurance.com",
                                    style: TextStyle(font: .lato(.latoRegular, size: 13),
                                                     color: .tertiaryDarkest,
                                                     underlined: true)))
    }

    // MARK: - Computed Properties
    var horizontalCardViewDataList: [HorizontalListCardViewData] {
        var list: [HorizontalListCardViewData] = []
        for policy in viewModel.policies {
            guard let dwellingType = policy.dwellingType else { return [] }
            list.append(HorizontalListCardViewData(icon: dwellingType.icon,
                                                   title: getDwellingTypeText(type: dwellingType),
                                                   subtitle: policy.address?.state ?? "",
                                                   statusText: policy.status?.title ?? "",
                                                   status: policy.status == .ACTIVE,
                                                   linkText: "dashboard.seeDetails.label".localized(lang),
                                                   tapAction: {
                self.didTapRenterPolicyCard(policy)
            }))
        }
        return list
    }
    
    var didTapRenterPolicyCard: (RenterPolicy) -> Void
    var didTapGetQuoteAction: SimpleClosure
    var didTapDonationBannerCardAction: BoolClosure
    var didTapFileAClaimAction: SimpleClosure
    var didTapTrackClaimAction: SimpleClosure
    var didTapAddResidenceAction: SimpleClosure
    var didTapPaymentSettingsAction: SimpleClosure
    var didTapFAQAction: SimpleClosure
    var didTapEmailAction: SimpleClosure
    
    public init(viewModel: RentersDashboardViewModel,
                didTapRenterPolicyCard: @escaping (RenterPolicy) -> Void,
                didTapGetQuoteAction: @escaping SimpleClosure,
                didTapDonationBannerCardAction: @escaping BoolClosure,
                didTapFileAClaimAction: @escaping SimpleClosure,
                didTapTrackClaimAction: @escaping SimpleClosure,
                didTapAddResidenceAction: @escaping SimpleClosure,
                didTapPaymentSettingsAction: @escaping SimpleClosure,
                didTapFAQAction: @escaping SimpleClosure,
                didTapEmailAction: @escaping SimpleClosure) {
        self.viewModel = viewModel
        self.didTapRenterPolicyCard = didTapRenterPolicyCard
        self.didTapGetQuoteAction = didTapGetQuoteAction
        self.didTapDonationBannerCardAction = didTapDonationBannerCardAction
        self.didTapFileAClaimAction = didTapFileAClaimAction
        self.didTapTrackClaimAction = didTapTrackClaimAction
        self.didTapAddResidenceAction = didTapAddResidenceAction
        self.didTapPaymentSettingsAction = didTapPaymentSettingsAction
        self.didTapFAQAction = didTapFAQAction
        self.didTapEmailAction = didTapEmailAction
    }
    
    public var body: some View {

        ZStack(alignment: Alignment.top) {
            Color.neutralBackground
            ScrollView {
                VStack(alignment: .leading, spacing: InsetSpacing.md) {
                    Text("upselling.renters.title.label".localized(lang))
                        .titleSecondaryDarkestBold()
                        .padding(.horizontal, InsetSpacing.xs)
                    
                    if !horizontalCardViewDataList.isEmpty {
                        HoriontalCardListView(
                            cardsData: horizontalCardViewDataList,
                            didTapAddItemButton: didTapGetQuoteAction)
                    }
                    
                    BannerCardView(
                        data: BannerCardViewData(
                            style: .horizontalLeft,
                            backgroundColor: .tertiaryDark,
                            leadingImage: Image.donationImage,
                            title: "dashboard.bannerTitle.worldBetter".localized(lang),
                            subtitle: "dashboard.bannerSubtitle.worldBetter".localized(lang),
                            tapAction: { didTapDonationBannerCardAction(viewModel.user?.donation != nil) }
                        )
                    )
                    .padding(.horizontal, InsetSpacing.xs)
                    
                    VStack(alignment: .leading, spacing: InsetSpacing.xxxs) {
                        Text("moreActions.titleLabel.label".localized(lang).uppercased())
                            .captionSecondaryDarkBold()
                        
                        ActionCardListView(cardButtons: [
                            CardButton(content: AnyView(ActionCardButton(
                                title: "moreActions.fileClaim.card".localized(lang),
                                icon: Image.addSquareIcon,
                                style: .secondary,
                                didTapAction: {
                                    self.popUpData = fileClaimPopUpData
                                }))),
                            CardButton(content: AnyView(AccordionButtonView(
                                title: "moreActions.trackClaim.card".localized(lang),
                                icon: Image.coveredIcon,
                                backgroundColor: .white,
                                insideView: AnyView(makeTrackYourClaimInfo())))),
                            CardButton(content: AnyView(ActionCardButton(
                                title: "moreActions.residence.card".localized(lang),
                                icon: Image.houseIcon,
                                style: .secondary,
                                didTapAction: didTapAddResidenceAction))),
                            CardButton(content: AnyView(ActionCardButton(
                                title: "moreActions.payment.card".localized(lang),
                                icon: Image.paymentIcon,
                                style: .secondary,
                                didTapAction: didTapPaymentSettingsAction))),
                            CardButton(content: AnyView(ActionCardButton(
                                title: "moreActions.FAQ.card".localized(lang),
                                icon: Image.faqIcon,
                                style: .secondary,
                                didTapAction: didTapFAQAction)))
                        ], spacing: StackSpacing.semiquarck)
                    }
                    .padding(.horizontal, InsetSpacing.xs)
                    
                } //: VStack
                .padding(.top, InsetSpacing.xs)
                .padding(.bottom, InsetSpacing.tabBar + InsetSpacing.lg)
                
            } //: ScrollView
            .scrollIndicators(.hidden)
            .offset(y: InsetSpacing.xl)
            
            if let popUpData {
                PopUpInfoView(
                    data: popUpData,
                    closeAction: {
                        self.popUpData = nil
                    }, didTapPopUpEmailAction: { email in
                        guard let url = URL(string: "mailto:\(email)"),
                              UIApplication.shared.canOpenURL(url) else { return }
                        UIApplication.shared.open(url)
                    }
                )
            }
                        
        } //: ZStack
        .ignoresSafeArea(edges: [.top])
        .padding(.bottom, InsetSpacing.tabBar)
    }
    
    @ViewBuilder
    private func makeTrackYourClaimInfo() -> some View {
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
        .padding(.horizontal, InsetSpacing.nano)
        .padding(.bottom, InsetSpacing.xs)
    }
    
    func getDwellingTypeText(type: DwellingType) -> String {
        switch type {
        case .SingleFamily:
            return "coverageDetails.dwellingType.singleFamily".localized(lang)
        case .MultiFamily:
            return "coverageDetails.dwellingType.MultiFamily".localized(lang)
        case .Apartment:
            return "coverageDetails.dwellingType.Apartment".localized(lang)
        case .Townhouse:
            return "coverageDetails.dwellingType.Townhouse".localized(lang)
        case .StudentHousing:
            return "coverageDetails.dwellingType.StudentHousing".localized(lang)
        }
    }
}
