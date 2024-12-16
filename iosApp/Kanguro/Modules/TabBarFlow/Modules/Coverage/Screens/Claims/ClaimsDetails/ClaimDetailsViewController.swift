import UIKit
import MobileCoreServices
import PhotosUI
import KanguroDesignSystem

class ClaimDetailsViewController: BaseViewController {
    
    // MARK: - Dependencies
    var viewModel: ClaimDetailsViewModel!
    
    // MARK: - IB Outlets
    @IBOutlet var claimIDTitleLabel: CustomLabel!
    @IBOutlet private var lastUpdateSubtitleLabel: CustomLabel!
    @IBOutlet var warningView: WarningView!
    @IBOutlet var summaryList: SummaryList!
    @IBOutlet private var claimDescriptionView: UIView!
    @IBOutlet var claimDescriptionTitleLabel: CustomLabel!
    @IBOutlet private var claimDescriptionLabel: CustomLabel!
    @IBOutlet var attachmentsListView: AttachmentsListView!
    @IBOutlet var submitDocumentsButton: CustomButton!
    @IBOutlet var hasPendingCommunicationStackView: UIStackView!
    @IBOutlet var documentRequiredLabel: CustomLabel!
    @IBOutlet var statusView: StatusView!
    @IBOutlet var alphaView: UIView!
    @IBOutlet var submitDocumentsStackView: UIStackView!
    @IBOutlet var submitDocumentsMenuStackView: UIStackView!

    // MARK: - Stored Properties
    var imagePicker = UIImagePickerController()
    
    // MARK: - Actions
    var backAction: SimpleClosure = {}
    var goToCommunicationChatbot: SimpleClosure = {}
    var didTapAttachmentAction: AnyClosure = { _ in }
}

// MARK: - Life Cycle
extension ClaimDetailsViewController {
    
    override func viewDidLoad() {
        super.viewDidLoad()
        observe(viewModel.$state) { [weak self] state in
            guard let self = self else { return }
            self.changed(state: state)
        }
        setupLayout()
    }
}

// MARK: - View State
extension ClaimDetailsViewController {
    
    private func changed(state: ClaimDetailsViewState) {
        
        switch state {
        case .started:
            viewModel.analyticsLogScreen()
            viewModel.getAttachments()
        case .loadingAttachment:
            submitDocumentsButton.isLoading(true)
        case .requestFailed:
            showSimpleAlert(message: viewModel.requestError)
        case .requestSucceeded:
            setupAttachments()
        case .postAttachmentSucceeded:
            submitDocumentsButton.stopLoader(showIcon: false)
            viewModel.getAttachments()
        case .downloadSucceeded(let attachment):
            submitDocumentsButton.stopLoader(showIcon: false)
            didTapAttachmentAction(attachment)
        case .imageFormatNotSupported:
            showCustomSimpleAlert(popUpData: KanguroDesignSystem.PopUpData(title: "alert.notSupportedImage.title".localized,
                                                                           description: "alert.notSupportedImage.description".localized))
        default:
            break
        }
    }
}

// MARK: - Setup
extension ClaimDetailsViewController {
    
    private func setupLayout() {
        setupLabels()
        setupSummaryList()
        setupWarningView()
        setupButtons()
        setupViews()
        setupImagePicker()
        setupSubmitDocumentsActionList()
        setupSubmitDocumentsMenuGesture()
    }
    
    private func setupLabels() {
        claimIDTitleLabel.set(text: viewModel.claim.prefixId != nil ? "claimDetails.IDtitle.label".localized + viewModel.claim.prefixId! : "claimDetails.defaultTitle.label".localized,
                              style: TextStyle(color: .secondaryDarkest, weight: .bold, size: .h24, font: .raleway, alignment: .center))
        
        if let date = viewModel.claim.updatedAt?.USADate {
            lastUpdateSubtitleLabel.setHighlightedText(text: "claimDetails.lastUpdate.label".localized + date,
                                                       style: TextStyle(color: .neutralMedium, size: .p11),
                                                       highlightedText: date)
            lastUpdateSubtitleLabel.isHidden = false
        }
        
        if let description = viewModel.claim.description {
            claimDescriptionTitleLabel.set(text: "claimDetails.claimDescription.label".localized,
                                           style: TextStyle(weight: .bold, size: .p12))
            claimDescriptionLabel.set(text: description,
                                      style: TextStyle(color: .secondaryDarkest, size: .p16))
            claimDescriptionView.isHidden = false
        }
        documentRequiredLabel.set(text: "claimDetails.document.label".localized, style: TextStyle(color: .tertiaryExtraDark, size: .p16))
    }
    
    func setupAttachments() {
        guard let attachments = viewModel.attachments else { return }
        attachmentsListView.clearAttachmentList()
        attachmentsListView.isHidden = false
        attachmentsListView.setup(attachments: attachments)
        attachmentsListView.didTapCardAction = { [weak self] attachment in
            guard let self else { return }
            self.viewModel.getClaimAttachment(attachment: attachment)
        }
    }
    
    private func setupWarningView() {
        warningView.setup(type: .claim(claim: viewModel.claim))
        warningView.isHidden = !(viewModel.claim.isWarningViewRequired)
        if viewModel.claim.isPendingCommunication {
            warningView.update(didTapButtonAction: goToCommunicationChatbot)
        }
    }
    
    private func setupSummaryList() {
        summaryList.update(title: "claimDetails.summary.label".localized)
        summaryList.addSummaryCard(summaryData: SummaryData(leadingTitle: "claimDetails.status.label".localized,
                                                            claim: viewModel?.claim,
                                                            summaryType: .statusView))
        summaryList.addSummaryCard(summaryData: SummaryData(leadingTitle: "claimDetails.pet.label".localized,
                                                            traillingTitle: viewModel?.petName,
                                                            claim: viewModel?.claim,
                                                            summaryType: .image))
        summaryList.addSummaryCard(summaryData: SummaryData(leadingTitle: "claimDetails.claim.label".localized,
                                                            traillingTitle: viewModel?.claim.type?.rawValue,
                                                            claim: viewModel?.claim))
        summaryList.addSummaryCard(summaryData: SummaryData(leadingTitle: "claimDetails.date.label".localized,
                                                            traillingTitle: viewModel?.claim.createdAt?.USADate,
                                                            claim: viewModel?.claim))
        summaryList.addSummaryCard(summaryData: SummaryData(leadingTitle: "claimDetails.incidentDate.label".localized,
                                                            traillingTitle: viewModel?.claim.incidentDate?.USADate_UTC,
                                                            claim: viewModel?.claim))
        if let amount = viewModel?.claim.amount {
            summaryList.addSummaryCard(summaryData: SummaryData(leadingTitle: "claimDetails.amount.label".localized,
                                                                traillingTitle: "$\(amount)",
                                                                claim: viewModel?.claim))
        }

        if viewModel?.claim.status == .Paid {
            if let paidAmount = viewModel?.claim.amountPaid {
                summaryList.addSummaryCard(summaryData: SummaryData(leadingTitle: "claimDetails.paidAmount.label".localized,
                                                                    traillingTitle: "$\(paidAmount)",
                                                                    claim: viewModel?.claim))
            }
            if let deductibleContributionAmount = viewModel?.claim.deductibleContributionAmount {
                summaryList.addSummaryCard(summaryData: SummaryData(leadingTitle: "claimDetails.transferredAmount.label".localized,
                                                                    traillingTitle: "$\(deductibleContributionAmount)",
                                                                    claim: viewModel?.claim))
            }
        }
    }
    
    private func setupButtons() {
        submitDocumentsButton.set(title: "claimDetails.submitDocuments.button".localized, style: .primary)
        submitDocumentsButton.setImage(nil, for: .normal)
        submitDocumentsButton.onTap { [weak self] in
            guard let self else { return }
            self.viewModel.claim.isPendingCommunication ? self.goToCommunicationChatbot() : self.showSubmitMoreDocumentsMenu()
        }
        if viewModel.shouldShowSubmitDocumentButton {
            submitDocumentsButton.isHidden = false
        }
    }
    
    private func setupViews() {
        if viewModel.claim.isPendingCommunication {
            statusView.update(claim: viewModel.claim, customTitle: "claimDetails.requiredStatus.label".localized)
            hasPendingCommunicationStackView.isHidden = false
        }
    }
    
    private func setupSubmitDocumentsActionList() {
        let cameraItem = StackButtonItem()
        cameraItem.data = StackButtonData(title: "addInfo.takePicture.button".localized,
                                          type: .file,
                                          image: UIImage(named: "ic-camera"),
                                          action: takePicture)
        submitDocumentsMenuStackView.addArrangedSubview(cameraItem)
        let galleryItem = StackButtonItem()
        galleryItem.data = StackButtonData(title: "addInfo.selectPicture.button".localized,
                                           type: .file,
                                           image: UIImage(named: "ic-gallery"),
                                           action: showGallery)
        submitDocumentsMenuStackView.addArrangedSubview(galleryItem)
        let fileItem = StackButtonItem()
        fileItem.data = StackButtonData(title: "addInfo.selectFile.button".localized,
                                        type: .file,
                                        image: UIImage(named: "ic-file"),
                                        action: selectFile)
        submitDocumentsMenuStackView.addArrangedSubview(fileItem)
    }
    
    private func setupImagePicker() {
        imagePicker.delegate = self
        imagePicker.allowsEditing = false
    }
    
    private func setupSubmitDocumentsMenuGesture() {
        let gesture = UITapGestureRecognizer(target: self, action: #selector(self.hideSubmitMoreDocumentsMenu))
        gesture.numberOfTapsRequired = 1
        gesture.cancelsTouchesInView = false
        alphaView.addGestureRecognizer(gesture)
    }
    
    @objc private func hideSubmitMoreDocumentsMenu() {
        submitDocumentsStackView.isHidden = true
    }
}

// MARK: - Private Methods
extension ClaimDetailsViewController {
    
    @objc private func selectFile() {
        let importMenu = UIDocumentPickerViewController(forOpeningContentTypes: [UTType.pdf],
                                                        asCopy: true)
        importMenu.delegate = self
        importMenu.modalPresentationStyle = .formSheet
        self.present(importMenu, animated: true, completion: nil)
    }
    
    @objc private func selectPicture() {
        imagePicker.sourceType = .photoLibrary
        self.present(imagePicker, animated: true)
    }
    
    @objc private func takePicture() {
        imagePicker.sourceType = .camera
        self.present(imagePicker, animated: true)
    }
    
    private func showGallery() {
        if #available(iOS 14, *) {
            setupMultipleFilesSelectionGallery()
        } else {
            selectPicture()
        }
    }
    
    @available(iOS 14.0, *)
    private func setupMultipleFilesSelectionGallery() {
        var config = PHPickerConfiguration(photoLibrary: PHPhotoLibrary.shared())
        config.selectionLimit = 5
        config.filter = .images
        let pickerController = PHPickerViewController(configuration: config)
        pickerController.delegate = self
        self.present(pickerController, animated: true)
    }
    
    private func showSubmitMoreDocumentsMenu() {
        alphaView.alpha = 0.4
        submitDocumentsStackView.isHidden = false
    }
}

// MARK: - UIImagePickerControllerDelegate
extension ClaimDetailsViewController: UIImagePickerControllerDelegate, UINavigationControllerDelegate {
    
    func imagePickerController(_ picker: UIImagePickerController, didFinishPickingMediaWithInfo info: [UIImagePickerController.InfoKey : Any]) {
        hideSubmitMoreDocumentsMenu()

        guard let image = info[.originalImage] as? UIImage ?? info[.editedImage] as? UIImage else {
            viewModel.state = .imageFormatNotSupported
            picker.dismiss(animated: true)
            return
        }

        if picker.sourceType == .camera || (info[.imageURL] as? NSURL).map(image.isImageFormatSupported) == true {
            viewModel.manageAttachments(images: [image])
        } else {
            viewModel.state = .imageFormatNotSupported
        }

        picker.dismiss(animated: true)
    }
}

// MARK: - PHPickerViewControllerDelegate
extension ClaimDetailsViewController: PHPickerViewControllerDelegate {
    
    @available(iOS 14.0, *)
    func picker(_ picker: PHPickerViewController, didFinishPicking results: [PHPickerResult]) {
        hideSubmitMoreDocumentsMenu()
        var chosenImages: [UIImage] = []
        results.forEach { result in
            DispatchQueue.main.async { [weak self] in
                guard let self else { return }
                result.itemProvider.loadObject(ofClass: UIImage.self) { imageObject, error in
                    guard let image = imageObject as? UIImage, error == nil else {
                        self.viewModel.state = .imageFormatNotSupported
                        return
                    }
                    result.itemProvider.loadFileRepresentation(forTypeIdentifier: UTType.image.identifier) { url, error in
                        if let path = url as? NSURL, image.isImageFormatSupported(imagePath: path) {
                            chosenImages.append(image)
                            if chosenImages.count == results.count {
                                self.viewModel.manageAttachments(images: chosenImages)
                            }
                        } else {
                            self.viewModel.state = .imageFormatNotSupported
                        }
                    }
                }
            }
        }
        picker.dismiss(animated: true, completion: .none)
    }
}

// MARK: - UIDocumentPickerViewController
extension ClaimDetailsViewController: UIDocumentPickerDelegate {
    
    func documentPicker(_ controller: UIDocumentPickerViewController, didPickDocumentsAt urls: [URL]) {
        guard let myURL = urls.first else { return }
        hideSubmitMoreDocumentsMenu()
        viewModel.manageFile(file: myURL)
    }
    
    func documentMenu(_ documentMenu: UIDocumentPickerViewController, didPickDocumentPicker documentPicker: UIDocumentPickerViewController) {
        documentPicker.delegate = self
        documentMenu.present(documentPicker, animated: true, completion: nil)
    }
    
    func documentPickerWasCancelled(_ controller: UIDocumentPickerViewController) {
        controller.dismiss(animated: true, completion: nil)
    }
}

// MARK: - IBActions
extension ClaimDetailsViewController {
    
    @IBAction private func closeTouchUpInside(_ sender: UIButton) {
        backAction()
    }
}
