import Foundation
import UIKit
import MessageUI

// MARK: - Setup
extension MoreViewController {

    func setupLayout() {
        setupActions()
        setupLabels()
        setupActionCardLists()
        setupPreferencesActionCardsList()
        setupSupportActionCardsList()
        setupLegalActionCardsList()
        setupLogoutActionCardsList()
        setupEmergencyActionCardsList()
        bannerView.isHidden = shouldShowRenters ?? false
        setupLoader()
    }
    
    private func setupActions() {
        refreshActionCards = { [weak self] in
            guard let self else { return }
            self.setupPreferencesActionCardsList()
        }
    }
    
    private func setupLabels() {
        titleLabel.set(text: "more.moreActions.label".localized,
                       style: TextStyle(color: .secondaryDarkest, weight: .bold, size: .h32))
        bannerTitleLabel.set(text: "moreActions.bannerTitle.label".localized,
                             style: TextStyle(color: .secondaryDarkest, weight: .bold, size: .p21, font: .raleway))
        bannerSubtitleLabel.set(text: "moreActions.bannerSubtitle.label".localized,
                                style: TextStyle(color: .secondaryDarkest))
        if let version = version {
            versionLabel.set(text: version, style: TextStyle(color: .secondaryDarkest, weight: .bold, size: .p12))
        } else {
            versionLabel.isHidden = true
        }
    }
    
    private func setupActionCardLists() {
        actionCardLists.forEach { $0.update(cardBackgroundColor: .white) }
    }
    
    private func setupPreferencesActionCardsList() {
        guard let profileImage = UIImage(named: "ic-user-square"),
              let paymentImage = UIImage(named: "ic-payment"),
              let notificationsImage = UIImage(named: "ic-notification"),
              let supportCauseImage = UIImage(named: "ic-heart"),
              let appSettingsImage = UIImage(named: "ic-settings"),
              let referFriendImage = UIImage(named: "ic-star") else { return }
        
        preferencesActionCardsList.clearActionCards()
        preferencesActionCardsList.update(title: "more.preferences.actionCardsList".localized)
        preferencesActionCardsList.addActionCards(actionCardDataList: [
            ActionCardData(leadingImage: profileImage,
                           leadingTitle: "more.profile.actionCard".localized,
                           didTapAction: didTapProfileAction),
            ActionCardData(leadingImage: notificationsImage,
                           leadingTitle: "more.reminders.actionCard".localized,
                           didTapAction: didTapRemindersAction),
            ActionCardData(leadingImage: paymentImage,
                           leadingTitle: "moreActions.payment.card".localized,
                           didTapAction: didTapPaymentSettingsAction),
            ActionCardData(leadingImage: referFriendImage,
                           leadingTitle: "more.referFriend.actionCard".localized,
                           didTapAction: showPopUpMenu),
            ActionCardData(leadingImage: supportCauseImage,
                           leadingTitle: "more.supportCause.actionCard".localized,
                           didTapDonatingAction: didTapSupportCauseAction,
                           dataType: .donation(viewModel.user?.donation != nil)),
            ActionCardData(leadingImage: appSettingsImage,
                           leadingTitle: "more.appSettings.actionCard".localized,
                           didTapAction: didTapAppSettingsAction)])
    }

    private func setupEmergencyActionCardsList() {
        if viewModel.shouldShowEmergencySection {
            guard let videoCallImage = UIImage(named: "ic-video-call") else { return }

            emergencyActionCardsList.update(title: "more.emergency.label".localized)
            emergencyActionCardsList.addActionCards(
                actionCardDataList: [
                    ActionCardData(
                        leadingImage: videoCallImage,
                        leadingTitle:"moreActions.talkToVet.card".localized,
                        didTapAction: {
                            [weak self] in
                            guard let self else { return }
                            self.didTapTalkToVet()
                        },
                        viewType: .normal(showTag: true, tagType: .new)
                    )
                ]
            )
        } else {
            emergencyActionCardsList.isHidden = true
        }
    }


    private func setupSupportActionCardsList() {
        guard let comunicationImage = UIImage(named: "ic-comunication") else { return }

        supportActionCardsList.update(title: "more.support.actionCardsList".localized)
        supportActionCardsList.addActionCards(actionCardDataList: [
            ActionCardData(leadingImage: comunicationImage,
                           leadingTitle: "more.contactUs.actionCard".localized,
                           didTapAction: didTapContactUsAction)
        ])
    }

    private func setupLegalActionCardsList() {
        guard let documentImage = UIImage(named: "ic-document") else { return }
        legalActionCardsList.update(title: "more.legal.actionCardsList".localized)
        legalActionCardsList.addActionCards(actionCardDataList: [
            ActionCardData(leadingImage: documentImage,
                           leadingTitle: "more.termsOfUse.actionCard".localized,
                           didTapAction: goToTermsOfServiceAction),
            ActionCardData(leadingImage: documentImage,
                           leadingTitle: "more.privacyPolicy.actionCard".localized,
                           didTapAction: didTapPrivacyPolicyAction)
        ])
    }
    
    private func setupLogoutActionCardsList() {
        guard let logoutImage = UIImage(named: "ic-logout") else { return }
        logoutActionCardsList.addActionCards(actionCardDataList: [
            ActionCardData(leadingImage: logoutImage,
                           leadingTitle: "more.logout.actionCard".localized,
                           didTapAction: viewModel.logout)
        ])
    }

    private func setupLoader() {
        loader.setScaleSize(1.5)
        loader.color = .lightGray
        loaderBackgroundView.backgroundColor = .neutralLightest.withAlphaComponent(0.4)
    }
}

// MARK: - MessageUI setup

extension MoreViewController: MFMessageComposeViewControllerDelegate {
    func messageComposeViewController(_ controller: MFMessageComposeViewController, didFinishWith result: MessageComposeResult) {
        self.dismiss(animated: true, completion: nil)
    }
}
