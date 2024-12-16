import Foundation
import UIKit

// MARK: - Setup
extension MixedDashboardViewController {
    
    func setupLayout() {
        setupLabels()
        setupHealthPlanList()
        setupActionCardsList()
        setupActions()
        setupRefreshControl()
        setupBanners()
        setupRentersList()
        hideNotificationButton()
    }
    
    func setupBanners() {
        setupReferFriendBanner()
        setupNonActivePolicyBanner(policyType: viewModel.activePolicyType)
        setupDonationBanner()
    }
    
    func setupReferFriendBanner() {
        bannerTitleLabel.set(text: "dashboard.bannerTitle.likingKanguro".localized,
                             style: TextStyle(color: .secondaryDarkest, weight: .bold, size: .p21, font: .raleway))
        bannerSubtitleLabel.set(text: "dashboard.bannerSubtitle.likingKanguro".localized,
                                style: TextStyle(color: .secondaryDarkest))
    }
    
    func setupHealthPlanList() {
        coverageList.setup(policies: viewModel.petPolicies,
                           title: "dashboard.petHealthPlanTitle.label".localized,
                           shouldShowAirVetCard: viewModel.shouldShowLiveVet
        )
        coverageList.didTapItemAction = { [weak self] index in
            guard let self else { return }
            self.cardPosition = self.coverageList.cardPosition
            self.didTapCardAction(self.viewModel.petPolicies[index])
        }
        coverageList.didTapAddButtonAction = { [weak self] in
            guard let self else { return }
            self.didTapGetAQuotePetAction()
        }
        coverageList.didTapAirVetCardAction = { [weak self] in
            guard let self else { return }
            self.viewModel.checkIfUserIsBlocked()
            self.didTapTalkToVetAction()
        }
        coverageList.isHidden = viewModel.petPolicies.isEmpty
    }
    
    func setupReminderList() {
        reminderList.setup(reminders: viewModel.reminders)
        reminderList.update(seeAllAction: didTapSeeAllRemindersAction)
        reminderList.update(medicalHistoryAction: didTapMedicalHistoryCardAction)
        reminderList.update(communicationAction: didTapCommunicationCardAction)
        reminderList.isHidden = viewModel.reminders.isEmpty
        
        if viewModel.isMedicalRemindersPopUpNeeded {
            showMedicalHistoryReminderPopUp()
        }
    }
    
    private func setupActionCardsList() {
        guard let fileClaimImage = UIImage(named: "ic-tab-more-default"),
              let cloudImage = UIImage(named: "ic-tab-cloud-default"),
              let faqImage = UIImage(named: "ic-faq") else { return }

        var actionCardDataList = [
            ActionCardData(
                leadingImage: fileClaimImage,
                leadingTitle: "moreActions.fileClaim.card".localized,
                didTapAction: { [weak self] in
                    guard let self else { return }
                    self.viewModel.checkIfUserIsBlocked()
                    switch viewModel.activePolicyType {
                    case .all:
                        showFileClaimPopUp()
                    case .pet:
                        self.didTapFileClaimAction()
                    case .renters:
                        self.showRentersFileClaimPopUp()
                    case .none:
                        return
                    }
                }
            ),
            ActionCardData(
                leadingImage: cloudImage,
                leadingTitle: "moreActions.cloud.card".localized,
                didTapAction: didTapCloudAction
            ),
            ActionCardData(
                leadingImage: faqImage,
                leadingTitle: "moreActions.FAQ.card".localized,
                didTapAction: didTapFAQAction
            )
        ]

        if !viewModel.petPolicies.isEmpty && viewModel.shouldShowLiveVet {
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
        updateUsernameLabel()
        helloLabel.set(text: "dashboard.hello.label".localized + ",",
                       style: TextStyle(color: .secondaryDarkest, weight: .bold, size: .h32, font: .raleway))
        if viewModel.pets.count > 1 {
            petNamesLabel.setChangingLabels(list: viewModel.petNames, duration: 3,
                                            style: TextStyle(color: .primaryDarkest, weight: .bold, size: .h32, font: .raleway))
        } else {
            petNamesLabel.set(text: viewModel.petNames.first ?? "",
                              style: TextStyle(color: .primaryDarkest, weight: .bold, size: .h32, font: .raleway))
        }
        petNamesLabel.setupToFitWidth()
    }
    
    private func setupRefreshControl() {
        refreshControl.addTarget(self, action: #selector(reloadData(_:)), for: UIControl.Event.valueChanged)
        scrollView.refreshControl = refreshControl
    }
    
    @objc func reloadData(_ : AnyObject) {
        viewModel.checkIfUserLanguageChanged()
        viewModel.getReminders()
    }
    
    private func setupActions() {
        didTapBannerButtonAction = { [weak self] in
            guard let self = self else { return }
            self.showReferFriendsPopUpMenu()
        }
    }

    // The notification button should stay hidden while there is no logic implemented 
    // to show notifications to the user. Once that is done, make sure to remove this
    // function from the code.

    private func hideNotificationButton() {
        notificationButton.isHidden = true
        notificationIcon.isHidden = true
    }
}
