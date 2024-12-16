import Foundation
import KanguroDesignSystem
import UIKit
import SwiftUI

// MARK: - Setup
extension PetDashboardViewController {
    
    func setupLayout() {
        setupLabels()
        setupCoverageList()
        setupActionCardsList()
        setupRefreshControl()
        setupAdvertisingCarousel()
    }
    
    func setupCoverageList() {
        coverageList.setup(policies: viewModel.policies, shouldShowAirVetCard: viewModel.shouldShowLiveVet)
        coverageList.didTapItemAction = { [weak self] index in
            guard let self else { return }
            self.cardPosition = self.coverageList.cardPosition
            self.didTapCardAction(self.viewModel.policies[index])
        }
        coverageList.didTapAddButtonAction = { [weak self] in
            guard let self else { return }
            self.didTapGetAQuoteAction()
        }
        coverageList.didTapAirVetCardAction = { [weak self] in
            guard let self else { return }
            self.viewModel.checkIfUserIsBlocked()
            self.didTapTalkToVetAction()
        }
        coverageList.isHidden = viewModel.policies.isEmpty
    }
    
    private func setupActionCardsList() {
        guard let fileClaimImage = UIImage(named: "ic-tab-more-default"),
              let trackClaimImage = UIImage(named: "ic-coverage-list-secondaryLight"),
              let directPayImage = UIImage(named: "ic-direct-pay"),
              let addPetImage = UIImage(named: "ic-paw-card"),
              let paymentImage = UIImage(named: "ic-payment"),
              let mapImage = UIImage(named: "ic-map"),
              let faqImage = UIImage(named: "ic-faq"),
              let videoCallImage = UIImage(named: "ic-video-call") else { return }

        var actionCardDataList = [
            ActionCardData(
                leadingImage: fileClaimImage,
                leadingTitle: "moreActions.fileClaim.card".localized,
                didTapAction: {
                    [weak self] in
                    guard let self else { return }
                    self.viewModel.checkIfUserIsBlocked()
                    self.didTapFileClaimAction()
                }),
            ActionCardData(
                leadingImage: trackClaimImage,
                leadingTitle: "moreActions.trackClaim.card".localized,
                didTapAction: {
                    [weak self] in
                    guard let self else { return }
                    self.viewModel.checkIfUserIsBlocked()
                    self.didTapClaimsAction()
                }
            ),
            ActionCardData(
                leadingImage: directPayImage,
                leadingTitle: "moreActions.directPayVet.label".localized,
                didTapDTPActionCard: didTapDirectPayAction,
                viewType: .normal(
                    showTag: true,
                    tagType: .new
                ),
                dataType: .directPayToVets(
                    viewModel.pets
                )
            ),
            ActionCardData(
                leadingImage: addPetImage,
                leadingTitle: "more.getQuote.actionCard".localized,
                didTapAction: didTapGetAQuoteAction
            ),
            ActionCardData(
                leadingImage: paymentImage,
                leadingTitle: "moreActions.payment.card".localized,
                didTapAction: didTapPaymentSettingsAction
            ),
            ActionCardData(
                leadingImage: mapImage,
                leadingTitle: "moreActions.vetsLocation.card".localized,
                didTapAction: didTapMapCellAction
            ),
            ActionCardData(
                leadingImage: faqImage,
                leadingTitle: "moreActions.FAQ.card".localized,
                didTapAction: didTapFAQAction
            )
        ]

        if viewModel.shouldShowLiveVet {
            if let talkToVetAction = talkToVetActionCard() {
                actionCardDataList.insert(talkToVetAction, at: 0)
            }
        }

        actionCardsList.addActionCards(
            actionCardDataList: actionCardDataList
        )

        actionCardsList.update(title: "moreActions.titleLabel.label".localized)
    }

    private func talkToVetActionCard() ->  ActionCardData? {
        guard let videoCallImage = UIImage(named: "ic-video-call") else { return nil }

        return ActionCardData(
            leadingImage: videoCallImage,
            leadingTitle:"moreActions.talkToVet.card".localized,
            didTapAction: {
                [weak self] in
                guard let self else { return }
                self.viewModel.checkIfUserIsBlocked()
                self.didTapTalkToVetAction()
            },
            viewType: .normal(
                showTag: true,
                tagType: .new
            )
        )
    }

    private func setupLabels() {
        headerTitleLabel.set(text: "pet.healthPlan.title.label".localized,
                             style: TextStyle(color: .secondaryDarkest, weight: .bold, size: .h32, font: .raleway))
    }
    
    private func setupRefreshControl() {
        refreshControl.addTarget(self, action: #selector(reloadData(_:)), for: UIControl.Event.valueChanged)
        scrollView.refreshControl = refreshControl
    }

    private func setupAdvertisingCarousel() {
        // Card sizes
        let cardWidth: CGFloat = self.carouselHostingView.bounds.width * 0.75 // 75% screen width
        let cardHeight: CGFloat = cardWidth / 2.4

        // Page Control sizes
        let pageControlMaxHeight = 12.0
        let pageControlMinHeight = 7.0

        carouselHostingView.setupSwiftUIIntoUIKitView(
            swiftUIView: AdvertisingCarouselView(
                cards: viewModel.advertisingCards,
                cardWidth: cardWidth,
                cardHeight: cardHeight,
                pageControlMaxHeight: pageControlMaxHeight,
                pageControlMinHeight: pageControlMinHeight, 
                redirectToPartnerWebpageAction: viewModel.redirectToPartnerWebpage(partnerName:)
            ),
            basedViewController: self,
            attachConstraints: true
        )

        carouselHostingView.heightAnchor
            .constraint(equalToConstant: cardHeight + pageControlMaxHeight + InsetSpacing.xs)
            .isActive = true
    }

    @objc func reloadData(_ : AnyObject) {
        viewModel.checkIfUserLanguageChanged()
    }
}
