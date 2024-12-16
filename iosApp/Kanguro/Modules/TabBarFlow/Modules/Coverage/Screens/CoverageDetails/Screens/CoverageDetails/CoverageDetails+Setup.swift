import Foundation
import UIKit

extension CoverageDetailsViewController {

    func setupLayout() {
        setupConstraints()
        setupLabels()
        setupImages()
        setupActions()
        setupActionCardsList()
        setupImagePicker()
        setupEditPetPictureActionList()
        setupEditClosePetPictureMenuGesture()
    }

    private func setupImagePicker() {
        imagePicker.delegate = self
        imagePicker.allowsEditing = false
    }

    private func setupEditPetPictureActionList() {
        let cameraItem = StackButtonItem()
        cameraItem.data = StackButtonData(title: "addInfo.takePicture.button".localized,
                                          type: .file,
                                          image: UIImage(named: "ic-camera"),
                                          action: takePicture)
        petPictureMenuStackView.addArrangedSubview(cameraItem)
        let galleryItem = StackButtonItem()
        galleryItem.data = StackButtonData(title: "addInfo.selectPicture.button".localized,
                                          type: .file,
                                          image: UIImage(named: "ic-gallery"),
                                          action: selectPicture)
        petPictureMenuStackView.addArrangedSubview(galleryItem)
    }

    private func setupEditClosePetPictureMenuGesture() {
        let gesture = UITapGestureRecognizer(target: self, action: #selector(self.hideEditClosePetPictureMenu))
        gesture.numberOfTapsRequired = 1
        gesture.cancelsTouchesInView = false
        alphaView.addGestureRecognizer(gesture)
    }

    @objc func hideEditClosePetPictureMenu() {
        petPictureStackView.isHidden = true
        self.showNavigationTabBar()
    }

   func setEditClosePetPictureMenuConstraints() {
        editPetPictureStackViewHeightConstraint.constant = 80
    }

    private func setupConstraints() {
        minimumBottomScrollableConstraint.constant = -72
        maximumTopScrollableConstraint.constant = minHeaderHeight
    }

    private func setupLabels() {
        guard let name = viewModel.policy.pet.name,
              let policyNumber = viewModel.policy.policyExternalId?.description,
              let breed = viewModel.policy.pet.breed else { return }

        helloLabel.set(text: "coverageDetails.hello.label".localized,
                       style: TextStyle(color: .secondaryDarkest, weight: .bold, size: .h32, font: .raleway))
        petNameLabel.set(text: name,
                         style: TextStyle(color: .primaryDarkest, weight: .bold, size: .h32, font: .raleway))
        petNameLabel.setupToFitWidth()

        policyNumberLabel.set(text: "\("coverageDetails.policyNumber.label".localized)\(policyNumber)",
                              style: TextStyle(color: .secondaryLight, weight: .bold))

        let age = viewModel.policy.pet.birthDate?.ageText ?? ""
        let localizedLabel = "coverageDetails.yearsOld.label".localized
        petDataLabel.set(text: "\(breed), \(age) \(localizedLabel)",
                         style: TextStyle(color: .secondaryDark))
    }

    private func setupImages() {
        petImageView.kf.setImage(with: petPictureResource, placeholder: placeholder)
    }

    private func setupActionCardsList() {
        guard let fileClaimImage = UIImage(named: "ic-tab-more-default"),
              let claimsImage = UIImage(named: "ic-claims"),
              let directPayImage = UIImage(named: "ic-direct-pay"),
              let faqImage = UIImage(named: "ic-faq") else { return }
        actionCardsList.update(cardBackgroundColor: .neutralBackground)
        actionCardsList.update(title: "moreActions.titleLabel.label".localized)
        actionCardsList.addActionCards(actionCardDataList: [
            ActionCardData(leadingImage: fileClaimImage,
                           leadingTitle: "moreActions.fileClaim.card".localized,
                           didTapAction: didTapFileClaimAction),
            ActionCardData(leadingImage: claimsImage,
                           leadingTitle: "moreActions.claims.card".localized,
                           didTapAction: didTapClaimsAction),
            ActionCardData(leadingImage: directPayImage,
                           leadingTitle: "moreActions.directPayVet.label".localized,
                           didTapDTPActionCard: didTapDirectPayAction,
                           viewType: .normal(showTag: true, tagType: .new),
                           dataType: .directPayToVets([viewModel.policy.pet])),
            ActionCardData(leadingImage: faqImage,
                           leadingTitle: "moreActions.FAQ.card".localized,
                           didTapAction: didTapFAQAction)
        ])
    }

    func setScrollPosition() {
        self.scrollView.contentOffset = CGPoint(x: 0, y: 0)
    }

    func setupCoverageDetailCards() {
        let preventive = viewModel.policy.preventive ?? false
        if preventive {
            preventiveCoverageDetailCard.setup(policy: viewModel.policy,
                                               type: .preventiveAndWellness)
        } else {
            preventiveCoverageDetailCard.isHidden = true
        }
        perroBuenoCoverageDetailCard.setup(policy: viewModel.policy,
                                           type: viewModel.policy.pet.type == .Dog ? .perroBueno : .gatoSano)
        paymentCoverageDetailCard.setup(policy: viewModel.policy,
                                        type: .coveragePayment)
    }

    private func setupActions() {
        perroBuenoCoverageDetailCard.didTapPlanCoveredAction = didTapPlanCoveredAction
        preventiveCoverageDetailCard.didTapPreventiveCoveredAction = didTapPreventiveCoveredAction
        paymentCoverageDetailCard.didTapBillingPreferencesAction = goToBillingPreferencesAction
    }

    func setupDocumentsCards() {
        guard let documents = viewModel.documents else { return }
        perroBuenoCoverageDetailCard.update(documents: documents, documentAction: didTapDocumentAction)
    }
}
