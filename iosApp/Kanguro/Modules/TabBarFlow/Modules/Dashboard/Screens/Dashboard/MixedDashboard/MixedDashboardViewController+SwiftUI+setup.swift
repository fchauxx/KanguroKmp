import SwiftUI
import KanguroDesignSystem
import KanguroSharedDomain

extension MixedDashboardViewController {
    
    func setupRentersList() {
        if !viewModel.renterPolicies.isEmpty {
            let view = RenterPoliciesView(
                renterPolicies: viewModel.renterPolicies,
                language: viewModel.user?.language ?? .English,
                didTapRenterPolicyCard: didTapRenterPolicyCard,
                didTapGetAQuoteAction: didTapGetAQuoteRentersAction
            )
            
            rentersListView.setupSwiftUIIntoUIKitView(
                swiftUIView: view,
                basedViewController: self
            )
            rentersListView.isHidden = false
        }
    }
    
    func setupDonationBanner() {
        let banner = BannerCardView(
            data: BannerCardViewData(
                style: .horizontalLeft,
                backgroundColor: .tertiaryDark,
                leadingImage: Image.donationImage,
                title: "dashboard.bannerTitle.worldBetter".localized(viewModel.appLanguage),
                subtitle: "dashboard.bannerSubtitle.worldBetter".localized(viewModel.appLanguage),
                tapAction: { [weak self] in
                    guard let self else { return }
                    self.didTapDonationBannerAction(viewModel.user?.donation != nil)
                }
            )
        )
        
        donationBannerView.setupSwiftUIIntoUIKitView(
            swiftUIView: banner,
            basedViewController: self
        )
    }
    
    func setupNonActivePolicyBanner(policyType: UserPolicyType) {
        var nonActivePolicyType: UserPolicyType? {
            switch policyType {
            case .pet:
                return .renters
            case .renters:
                return .pet
            default:
                return nil
            }
        }
        guard let bannerTexts = nonActivePolicyType?.bannerTexts,
              let bannerColor = nonActivePolicyType?.bannerColor,
              let bannerImage = nonActivePolicyType?.bannerImage else { return }
        let banner = BannerCardView(
            data: BannerCardViewData(
                style: .horizontalRight,
                backgroundColor: bannerColor,
                leadingImage: bannerImage,
                title: bannerTexts.title,
                subtitle: bannerTexts.subtitle,
                tapAction: { [weak self] in
                    guard let self else { return }
                    self.didTapNonActivePolicyAction(policyType)
                }
            )
        )
        
        insuranceBannerView.isHidden = false
        insuranceBannerView.setupSwiftUIIntoUIKitView(
            swiftUIView: banner,
            basedViewController: self
        )
    }
}
