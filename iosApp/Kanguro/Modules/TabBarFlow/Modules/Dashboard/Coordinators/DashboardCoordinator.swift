import UIKit
import KanguroNetworkDomain
import KanguroRentersDomain
import Resolver
import KanguroSharedDomain
import KanguroPetDomain
import KanguroDesignSystem
import SwiftUI

public enum DashboardType {
    
    case old
    case mixed(petPolicies: [PetPolicy], renterPolicies: [RenterPolicy])
    case pet(policies: [PetPolicy])
}

class DashboardCoordinator: Coordinator {

    // MARK: - Dependencies
    @LazyInjected var network: NetworkProtocol

    // MARK: - Stored Properties
    var coverageDetailsCoordinator: CoverageDetailsCoordinator?
    var presentNavigationController: UINavigationController?
    var oldPetPolicies: [PetPolicy] = []
    var type: DashboardType
    var navController: UINavigationController?

    // MARK: - Computed Properties
    var appDelegate: AppDelegate? {
        UIApplication.shared.delegate as? AppDelegate
    }

    // MARK: - Actions
    var didTapFileClaimAction: SimpleClosure = {}
    var didTapUpdatePetPictureAction: SimpleClosure = {}
    var didTapActivePolicyAction: UserPolicyTypeClosure = { _ in }
    var logoutAction: SimpleClosure
    var blockedAction: SimpleClosure
    
    // MARK: - Coordinator
    override func start() {
        switch type {
        case .old:
            navigateToDashboard(policies: oldPetPolicies)
        case .mixed(let petPolicies, let renterPolicies):
            navigateToMixedDashboard(petPolicies: petPolicies, 
                                     renterPolicies: renterPolicies)
        case .pet(let policies):
            navigateToPetDashboard(policies: policies)
        }
    }
    
    // MARK: - Initializers
    init(
        type: DashboardType = .old,
        navigation: UINavigationController,
        logoutAction: @escaping SimpleClosure = {},
        blockedAction: @escaping SimpleClosure = {},
        localChatbot: LocalChatbotModule? = nil
    ) {
        self.type = type
        self.logoutAction = logoutAction
        self.blockedAction = blockedAction
        super.init(navigation: navigation)
    }
}

// MARK: - NavigateToAdditionalInfo
extension DashboardCoordinator {
    
    private func navigateToPetDashboard(policies: [PetPolicy]) {
        let controller = PetDashboardViewController()
        controller.viewModel = PetDashboardViewModel(policies: policies)
        controller.didTapTalkToVetAction = { [weak self] in
            guard let self = self else { return }
            self.navigateToAirVet()
        }
        controller.didTapFileClaimAction = { [weak self] in
            guard let self = self else { return }
            self.navigateToNewClaim()
        }
        controller.blockedAction = blockedAction
        controller.didTapClaimsAction = { [weak self] in
            guard let self else { return }
            self.navigateToClaims()
        }
        controller.didTapCardAction = { [weak self] policy in
            guard let self else { return }
            self.navigateToCoverageDetails(policy: policy)
        }
        controller.didTapGetAQuoteAction = { [weak self] in
            guard let self,
                  let url = controller.viewModel.getQuoteUrl else { return }
            self.navigateToGetAQuote(url: url)
        }
        controller.goToPartnerWebsite = { [weak self] url in
            guard let self else { return }
            self.navigateToPartnerWebpage(url)
        }
        controller.didTapFAQAction = { [weak self] in
            guard let self else { return }
            self.navigateToFAQ(types: [.FAQ])
        }
        controller.didTapDirectPayAction = { [weak self] pets in
            guard let self else { return }
            self.navigateToDTPBeforeGetStarted(pets: pets)
        }
        controller.didTapMapCellAction = { [weak self] in
            guard let self else { return }
            self.navigateToMapDetail()
        }
        controller.restartAppAction = { [weak self] in
            guard let self else { return }
            self.restartApp()
        }
        controller.didTapPaymentSettingsAction = { [weak self] in
            guard let self else { return }
            self.navigateToPaymentSettings(policies: controller.viewModel.policies)
        }
        navigation.pushViewController(controller, animated: true)
    }
    
    // MARK: - Mixed Dashboard
    //It's going to become the new "Dashboard" on Renters product
    private func navigateToMixedDashboard(petPolicies: [PetPolicy], 
                                          renterPolicies: [RenterPolicy]) {
        let controller = MixedDashboardViewController()
        
        controller.viewModel = MixedDashboardViewModel(
            petPolicies: petPolicies,
            renterPolicies: renterPolicies
        )

        controller.didTapTalkToVetAction = { [weak self] in
            guard let self else { return }
            self.navigateToAirVet()
        }

        controller.didTapFileClaimAction = { [weak self] in
            guard let self else { return }
            self.navigateToNewClaim()
        }

        controller.goToRentersOnboarding = {
            [weak self] policy,
            isDonating in
            guard let self else { return }
            let view = RentersNavigationViewFactory.make(
                page: .onboardingChat(policy: policy),
                network: network,
                navigation: navigation
            )
                .onDisappear(
                    perform: {
                        #warning("atualmente essa navegaçao ocorre mesmo só fechando o chat")
                        isDonating ? self.navigateToSupportCause() : self.navigateToDonationTypeCause()
                    }
                )

            let hostingController = UIHostingController(rootView: view)
            navigation.pushViewController(hostingController, animated: true)
        }
        controller.blockedAction = blockedAction
        controller.didTapClaimsAction = { [weak self] in
            guard let self else { return }
            self.navigateToClaims()
        }
        controller.didTapCloudAction = { [weak self] in
            guard let self else { return }
            self.navigateToCloud()
        }

        controller.didTapRenterPolicyCard = { [weak self] policy in
            guard let self else { return }
            self.navigateToRentersCoverageDetails(policy: policy)
        }
        controller.didTapNonActivePolicyAction = { [weak self] nonActivePolicyType in
            guard let self else { return }
            self.didTapActivePolicyAction(nonActivePolicyType)
        }
        controller.didTapCardAction = { [weak self] policy in
            guard let self else { return }
            self.navigateToCoverageDetails(policy: policy)
        }
        controller.didTapGetAQuoteRentersAction = { [weak self] in
            guard let self,
                  let url = controller.viewModel.getQuoteUrlRenters else { return }
            self.navigateToGetAQuote(url: url)
        }
        controller.didTapGetAQuotePetAction = { [weak self] in
            guard let self,
                  let url = controller.viewModel.getQuoteUrlPet else { return }
            self.navigateToGetAQuote(url: url)
        }
        controller.goToPartnerWebsite = { [weak self] url in
            guard let self else { return }
            self.navigateToPartnerWebpage(url)
        }
        controller.didTapSeeAllRemindersAction = { [weak self] in
            guard let self else { return }
            self.navigateToReminders()
        }
        controller.didTapReferFriendButtonAction = { [weak self] in
            guard let self else { return }
            self.navigateToReferFriends()
        }
        controller.didTapMedicalHistoryReminderAction = { [weak self] reminders in
            guard let self,
                  let petId = reminders.first?.petId else { return }
            self.navigation.dismiss(animated: true)
            reminders.count > 1 ? self.navigateToReminders() : self.navigateToMedicalHistoryChatbot(petId: petId)
        }
        controller.didTapMedicalHistoryCardAction = { [weak self] pet in
            guard let self else { return }
            self.navigateToMedicalHistoryChatbot(petId: pet.id)
        }
        controller.didTapCommunicationCardAction = { [weak self] claimId in
            guard let self else { return }
            self.navigateToCommunicationChatbot(claimId: claimId)
        }
        controller.didTapFAQAction = { [weak self] in
            guard let self else { return }
            self.navigateToHomeFAQ()
            
        }
        controller.didTapBlogAction = { [weak self] in
            guard let self,
                  let url = controller.viewModel.blogURL else { return }
            self.navigateToBlog(url: url)
        }
        controller.didTapDonationBannerAction = { [weak self] isDonating in
            guard let self else { return }
            if !isDonating {
                self.navigateToDonationTypeCause()
            } else {
                self.navigateToSupportCause()
            }
        }
        controller.didTapNotificationAction = { [weak self] in
            guard let self else { return }
            self.navigateToNotificationScreen()
        }
        controller.restartAppAction = { [weak self] in
            guard let self else { return }
            self.restartApp()
        }

        navigation.pushViewController(controller, animated: true)
    }
    private func navigateToDashboard(policies: [PetPolicy]) {
        let controller = DashboardViewController()
        controller.viewModel = DashboardViewModel(policies: policies)
        controller.didTapFileClaimAction = { [weak self] in
            guard let self = self else { return }
            self.navigateToNewClaim()
        }
        controller.blockedAction = blockedAction
        controller.didTapClaimsAction = { [weak self] in
            guard let self else { return }
            self.navigateToClaims()
        }
        controller.didTapCardAction = { [weak self] policy in
            guard let self else { return }
            self.navigateToCoverageDetails(policy: policy)
        }
        controller.didTapGetAQuoteAction = { [weak self] in
            guard let self,
                  let url = controller.viewModel.getQuoteUrl else { return }
            self.navigateToGetAQuote(url: url)
        }
        controller.goToPartnerWebsite = { [weak self] url in
            guard let self else { return }
            self.navigateToPartnerWebpage(url)
        }
        controller.didTapSeeAllRemindersAction = { [weak self] in
            guard let self else { return }
            self.navigateToReminders()
        }
        controller.didTapReferFriendButtonAction = { [weak self] in
            guard let self else { return }
            self.navigateToReferFriends()
        }
        controller.didTapMedicalHistoryReminderAction = { [weak self] reminders in
            guard let self,
                  let petId = reminders.first?.petId else { return }
            self.navigation.dismiss(animated: true)
            reminders.count > 1 ? self.navigateToReminders() : self.navigateToMedicalHistoryChatbot(petId: petId)
        }
        controller.didTapProfileAction = { [weak self] in
            guard let self else { return }
            self.navigateToProfile()
        }
        controller.didTapMedicalHistoryCardAction = { [weak self] pet in
            guard let self else { return }
            self.navigateToMedicalHistoryChatbot(petId: pet.id)
        }
        controller.didTapCommunicationCardAction = { [weak self] claimId in
            guard let self else { return }
            self.navigateToCommunicationChatbot(claimId: claimId)
        }
        controller.didTapVetAdviceAction = { [weak self] in
            guard let self else { return }
            self.navigateToBaseVetAdvice()
        }
        controller.didTapFAQAction = { [weak self] in
            guard let self else { return }
            self.navigateToHomeFAQ()
        }
        controller.didTapPetParentsAction = { [weak self] in
            guard let self else { return }
            self.navigateToPetParents()
        }
        controller.didTapDirectPayAction = { [weak self] pets in
            guard let self else { return }
            self.navigateToDTPBeforeGetStarted(pets: pets)
        }
        controller.didTapMapCellAction = { [weak self] in
            guard let self else { return }
            self.navigateToMapDetail()
        }
        controller.restartAppAction = { [weak self] in
            guard let self else { return }
            self.restartApp()
        }
        navigation.pushViewController(controller, animated: true)
    }
    // MARK: - Notification Screen
    private func navigateToNotificationScreen() {
        /*The above snippet has been commented out and added to the reminder screen navigation because on one occasion the user was able to see the notification icon (which should be kept hidden until the notification logic is implemented). So if that happens in the future, the user will not see a blank page, instead they will see this. Once the notification logic is created, the correct navigation should happen here.*/
//        let controller = UIHostingController(rootView: EmptyView())
//        navigation.present(controller, animated: true)
        navigateToReminders()
    }
}
