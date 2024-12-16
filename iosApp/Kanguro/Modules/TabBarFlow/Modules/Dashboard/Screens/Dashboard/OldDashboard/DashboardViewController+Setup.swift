import UIKit
import SwiftUI

// MARK: - Setup
extension DashboardViewController {

    func setupLayout() {
        setupLabels()
        setupCoverageList()
        setupActionCardsList()
        setupActions()
        setupRefreshControl()
    }

    func setupCoverageList() {
        coverageList.setup(policies: viewModel.policies)
        coverageList.didTapItemAction = { [weak self] index in
            guard let self else { return }
            self.cardPosition = self.coverageList.cardPosition
            self.didTapCardAction(self.viewModel.policies[index])
        }
        coverageList.didTapAddButtonAction = { [weak self] in
            guard let self else { return }
            self.didTapGetAQuoteAction()
        }
        coverageList.isHidden = viewModel.policies.isEmpty
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
              let trackClaimImage = UIImage(named: "ic-coverage-list-secondaryLight"),
              let getQuoteImage = UIImage(named: "ic-tab-dashboard-default"),
              let vetAdviceImage = UIImage(named: "ic-vet-advice"),
              let faqImage = UIImage(named: "ic-faq"),
              let petParentsImage = UIImage(named: "ic-pet-parent"),
              let directPayImage = UIImage(named: "ic-direct-pay"),
              let mapImage = UIImage(named: "ic-map")else { return }
        actionCardsList.addActionCards(actionCardDataList: [
            ActionCardData(leadingImage: fileClaimImage,
                           leadingTitle: "moreActions.fileClaim.card".localized,
                           didTapAction: { [weak self] in
                               guard let self else { return }
                               self.viewModel.checkIfUserIsBlocked()
                               self.didTapFileClaimAction()
                           }),
            ActionCardData(leadingImage: trackClaimImage,
                           leadingTitle: "moreActions.trackClaim.card".localized,
                           didTapAction: { [weak self] in
                               guard let self else { return }
                               self.viewModel.checkIfUserIsBlocked()
                               self.didTapClaimsAction()
                           }),
            ActionCardData(leadingImage: directPayImage,
                           leadingTitle: "moreActions.directPayVet.label".localized,
                           didTapDTPActionCard: didTapDirectPayAction,
                           viewType: .normal(showTag: true, tagType: .new),
                           dataType: .directPayToVets(viewModel.pets)),
            ActionCardData(leadingImage: getQuoteImage,
                           leadingTitle: "more.getQuote.actionCard".localized,
                           didTapAction: didTapGetAQuoteAction),
            ActionCardData(leadingImage: vetAdviceImage,
                           leadingTitle: "moreActions.vetAdvice.card".localized,
                           didTapAction: didTapVetAdviceAction),
            ActionCardData(leadingImage: mapImage,
                           leadingTitle: "moreActions.vetsLocation.card".localized,
                           didTapAction: didTapMapCellAction),
            ActionCardData(leadingImage: faqImage,
                           leadingTitle: "moreActions.FAQ.card".localized,
                           didTapAction: didTapFAQAction),
            ActionCardData(leadingImage: petParentsImage,
                           leadingTitle: "moreActions.petParents.card".localized,
                           didTapAction: didTapPetParentsAction)
        ])
        actionCardsList.update(title: "moreActions.titleLabel.label".localized)
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
        bannerTitleLabel.set(text: "dashboard.bannerTitle.likingKanguro".localized,
                             style: TextStyle(color: .secondaryDarkest, weight: .bold, size: .p21, font: .raleway))
        bannerSubtitleLabel.set(text: "dashboard.bannerSubtitle.likingKanguro".localized,
                                style: TextStyle(color: .secondaryDarkest))
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
            guard let self else { return }
            self.showReferFriendsPopUpMenu()
        }
    }
}
