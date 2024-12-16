import UIKit
import KanguroPetDomain

// MARK: - DTP
extension CoverageDetailsCoordinator {
    
    func navigateToDTPBeforeGetStarted(pets: [Pet]) {
        let controller = BeforeGetStartedViewController(
            viewModel: BeforeGetStartedViewModel(),
            nextAction: { [weak self] claimAmount in
                guard let self else { return }
                self.navigateToDTPGettingStarted(pets: pets, claimAmount: claimAmount)
            }, closeAction: { [weak self] in
                guard let self else { return }
                self.navigation.dismiss(animated: true)
            })
        presentNavigationController = UINavigationController(rootViewController: controller)
        guard let presentNavigationController else { return }
        presentNavigationController.setNavigationBarHidden(true, animated: true)
        navigation.present(presentNavigationController, animated: true)
    }
    
    func navigateToDTPGettingStarted(pets: [Pet],
                                     claimAmount: Double?) {
        guard let claimAmount else { return }
        let controller = GettingStartedViewController { [weak self] in
            guard let self else { return }
            self.navigateToDTPInformation(pets: pets, claimAmount: claimAmount)
        } backAction: { [weak self] in
            guard let self else { return }
            self.presentNavigationController?.popViewController(animated: true)
        } closeAction: { [weak self] in
            guard let self else { return }
            self.presentNavigationController?.dismiss(animated: true)
        }
        presentNavigationController?.pushViewController(controller, animated: true)
    }
    
    func navigateToDTPInformation(pets: [Pet],
                                  claimAmount: Double) {
        let viewModel = DirectPayUserInformationViewModel(pets: pets,
                                                          newDirectPayClaim: PetVetDirectPaymentParameters(amount: claimAmount))
        let controller = DirectPayUserInformationViewController(viewModel: viewModel)
        controller.goBackAction = { [weak self] in
            guard let self else { return }
            self.presentNavigationController?.popViewController(animated: true)
        }
        controller.goToRootAction = { [weak self] in
            guard let self else { return }
            self.presentNavigationController?.dismiss(animated: true)
        }
        controller.didTapNextAction = { [weak self] newPetVetDTPClaim in
            guard let self else { return }
            self.navigateToDTPPledgeOfHonor(dtpClaim: newPetVetDTPClaim)
        }
        controller.showSearchView = { [weak self] in
            guard let self else { return }
            self.navigateToSearchVet() { selectedDatabaseVet in
                viewModel.update(selectedVet: selectedDatabaseVet)
            } didSelectedNewVetEmail: { newVetEmail in
                viewModel.update(newVetEmail: newVetEmail)
            }
        }
        presentNavigationController?.pushViewController(controller, animated: true)
    }
    
    func navigateToDTPAlmostDone(dtpPetClaim: PetClaim) {
        let controller = AlmostDoneViewController(viewModel: AlmostDoneViewModel(dtpPetClaim: dtpPetClaim),
                                                  closeAction: { [weak self] in
            guard let self else { return }
            self.presentNavigationController?.dismiss(animated: true)
        }, okayAction: { [weak self] in
            guard let self else { return }
            self.presentNavigationController?.dismiss(animated: true)
            self.navigateToClaims()
        }, didTapSendFormYourself: { [weak self] petClaim in
            guard let self else { return }
            self.navigateToShareWithVet(dtpPetClaim: petClaim)
        })
        presentNavigationController?.pushViewController(controller, animated: true)
    }
    
    func navigateToDTPPledgeOfHonor(dtpClaim: PetVetDirectPaymentParameters) {
        let controller = PledgeOfHonorViewController(type: .directPayToVet)
        controller.viewModel = PledgeOfHonorViewModel(dtpClaimParameters: dtpClaim)
        controller.goBackAction = { [weak self] in
            guard let self else { return }
            self.presentNavigationController?.dismiss(animated: true)
        }
        controller.didDTPClaimCreationSucceedAction = { [weak self] newDTPPetClaim in
            guard let self else { return }
            self.navigateToDTPAlmostDone(dtpPetClaim: newDTPPetClaim)
        }
        presentNavigationController?.pushViewController(controller, animated: true)
    }
    
    func navigateToSearchVet(didSelectVetData: @escaping VetDataClosure,
                             didSelectedNewVetEmail: @escaping StringClosure) {
        let controller = SearchVetViewController(viewModel: SearchVetViewModel(),
                                                 didSelectVetAction: { [weak self] vetData in
            guard let self else { return }
            didSelectVetData(vetData)
            self.presentNavigationController?.dismiss(animated: true)
        }, didSelectedNewVetEmail: { [weak self] newVetEmail in
            guard let self else { return }
            didSelectedNewVetEmail(newVetEmail)
            self.presentNavigationController?.dismiss(animated: true)
        })
        presentNavigationController?.modalPresentationStyle = .overCurrentContext
        presentNavigationController?.present(controller, animated: true)
    }
    
    func navigateToShareWithVet(dtpPetClaim: PetClaim) {
        let controller = ShareWithVetViewController(viewModel: ShareWithVetViewModel(petClaim: dtpPetClaim),
                                                    goToRootAction: { [weak self] in
            guard let self else { return }
            self.presentNavigationController?.dismiss(animated: true)
        },
                                                    goToFinishDTP: { [weak self] in
            guard let self else { return }
            self.navigateToDoneDTP()
        })
        presentNavigationController?.pushViewController(controller, animated: true)
    }
    
    func navigateToDoneDTP() {
        let controller = DTPDoneViewController { [weak self] in
            guard let self else { return }
            self.presentNavigationController?.dismiss(animated: true)
        } closeAction: { [weak self] in
            guard let self else { return }
            self.presentNavigationController?.dismiss(animated: true)
        }
        presentNavigationController?.pushViewController(controller, animated: true)
    }
}
