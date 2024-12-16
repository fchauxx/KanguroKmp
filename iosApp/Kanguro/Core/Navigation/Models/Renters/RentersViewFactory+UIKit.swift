import SwiftUI
import Resolver
import KanguroRentersPresentation
import KanguroRentersDomain
import KanguroRentersData
import KanguroNetworkDomain
import KanguroUserDomain
import KanguroSharedDomain
import KanguroSharedData
import KanguroDesignSystem

// MARK: - Payment Settings
extension RentersViewFactory {
    
    public static func makePaymentSettings(router: RentersRouter<RentersPage>) -> some View {
        let controller = PaymentSettingsViewController()
        //TODO: - Verify if it shouldn't have a Renters one
        controller.viewModel = PaymentSettingsViewModel(policies: [])
        controller.goBackAction = {
            router.pop()
        }
        controller.goToPaymentMethodAction = {
            router.present(page: .paymentMethods)
        }
        controller.goToReimbursementAction = {
            router.present(page: .bankingInfo)
        }
        return AnyView(UIViewControllerAsView(viewController: controller))
            .environmentObject(router)
            .ignoresSafeArea(edges: .top)
    }
    
    public static func makePaymentMethods(router: RentersRouter<RentersPage>) -> some View {
        let controller = PaymentMethodViewController()
        controller.viewModel = PaymentMethodViewModel()
        controller.backAction = {
            router.dismiss()
        }
        return AnyView(UIViewControllerAsView(viewController: controller))
            .environmentObject(router)
    }
    
    public static func makeBankingInfo(router: RentersRouter<RentersPage>) -> some View {
        let controller = BankingInfoViewController()
        controller.viewModel = BankingInfoViewModel(type: .edit)
        controller.backAction = {
            router.dismiss()
        }
        return AnyView(UIViewControllerAsView(viewController: controller))
            .environmentObject(router)
    }
}

// MARK: - Donation Cause
extension RentersViewFactory {
    
    public static func makeSupportCause(router: RentersRouter<RentersPage>) -> some View {
        let controller = SupportCauseViewController()
        controller.viewModel = SupportCauseViewModel()
        controller.goBackAction = {
            router.dismiss()
        }
        controller.didTapChooseToChangeCause = {
            router.push(RentersPage.donationTypeCause, pathType: .sheet)
        }
        return AnyView(UIViewControllerAsView(viewController: controller))
            .environmentObject(router)
    }
    
    public static func makeDonationTypeCause(router: RentersRouter<RentersPage>) -> some View {
        let controller = DonationTypeViewController()
        controller.viewModel = DonationTypeViewModel()
        controller.goBackAction = {
            router.dismiss()
        }
        controller.didTapDonationCard = { type, donationCauses in
            router.push(RentersPage.donationCause(type: type, donationCauses: donationCauses),
                        pathType: .sheet)
        }
        return AnyView(UIViewControllerAsView(viewController: controller))
            .environmentObject(router)
    }
    
    public static func makeDonationCause(type: DonationType,
                                         donationCauses: [DonationCause],
                                         router: RentersRouter<RentersPage>) -> some View  {
        let controller = DonationCauseViewController()
        controller.viewModel = DonationCauseViewModel(type: type, donationCauses: donationCauses)
        controller.goBackAction = {
            router.pop(pathType: .sheet)
        }
        controller.goToRootAction = {
            router.popToRoot(pathType: .sheet)
        }
        return AnyView(UIViewControllerAsView(viewController: controller))
            .environmentObject(router)
    }
}
