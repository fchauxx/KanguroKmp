//
//  CloudPolicyFilesViewController.swift
//  Kanguro
//
//  Created by Rodrigo Okido on 25/05/23.
//

import UIKit

class CloudPolicyFilesViewController: BaseViewController {
    
    // MARK: - Dependencies
    var viewModel: CloudPolicyFilesViewModel!
    
    // MARK: - IB Outlets
    @IBOutlet private var scrollView: UIScrollView!
    @IBOutlet var titleLabel: CustomLabel!
    @IBOutlet var navigationBackButton: NavigationBackButton!
    @IBOutlet var breadcrumb: CustomLabel!
    @IBOutlet var noFilesFoundPlaceholderLabel: CustomLabel!
    @IBOutlet var cloudActionList: ActionCardsList!
    
    // MARK: - Stored Properties
    let viewType: CloudViewType
    let breadcrumbStyle: TextStyle = TextStyle(color: .secondaryDark, weight: .bold, size: .p12, font: .raleway)
    
    // MARK: - Actions
    var goBackAction: SimpleClosure = {}
    var getClaimAttachment: AttachmentClosure = { _ in }
    var getPolicyAttachment: PolicyAttachmentClosure = { _ in }
    var getDocument: AnyClosure = { _ in }
    var didTapAttachmentAction: AnyClosure = { _ in }
    
    // MARK: - Initializers
    init(type: CloudViewType) {
        viewType = type
        super.init(nibName: nil, bundle: nil)
    }
    
    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
}

// MARK: - Life Cycle
extension CloudPolicyFilesViewController {
    
    override func viewDidLoad() {
        super.viewDidLoad()
        setupLayout()
        observe(viewModel.$state) { [weak self] state in
            guard let self = self else { return }
            self.changed(state: state)
        }
    }
}

// MARK: - View State
extension CloudPolicyFilesViewController {
    
    private func changed(state: CloudClaimFilesViewState) {
        
        switch state {
        case .started:
            viewModel.analyticsLogScreen()
            viewModel.petPolicyOption == .claimDocumentsAndInvoices ? viewModel.getClaimDocumentsByPolicyIdAndClaimId() : setupCloudActionCardLists()
        case .loading:
            showLoadingView(shouldAnimate: false)
        case .requestFailed:
            hideLoadingView()
            showSimpleAlert(message: viewModel.requestError)
        case .requestSucceeded:
            hideLoadingView()
            setupCloudActionCardLists()
        case .downloadSucceeded(let attachment):
            hideLoadingView()
            didTapAttachmentAction(attachment)
        }
    }
}

// MARK: - Setup
extension CloudPolicyFilesViewController {
    
    private func setupLayout() {
        setupViews()
        setupLabels()
        setupActions()
    }
    
    private func setupViews() {
        breadcrumb.isHidden = false
        noFilesFoundPlaceholderLabel.isHidden = true
        setNavigationBackButtonVisibility(show: true)
    }
    
    private func setupLabels() {
        setupTitleLabel()
        setupBreadcrumbLabel()
        setupPlaceholder()
    }
    
    private func setupTitleLabel() {
        setupNavigationBackButton(title: viewType.title)
    }
    
    private func setupBreadcrumbLabel() {
        let path = viewModel.getBreadcrumbPathUppercased(viewType: viewType)
        breadcrumb.set(text: path, style: breadcrumbStyle)
    }

    private func setupPlaceholder() {
        noFilesFoundPlaceholderLabel.set(text: "cloud.noFiles.placeholder.label".localized,
                                         style: TextStyle(color: .secondaryMedium, size: .p16))
    }

    private func setupCloudActionCardLists() {
        cloudActionList.clearActionCards()
        cloudActionList.addActionCards(actionCardDataList: setupCloudPetFilesActionCards())
    }
    
    private func setupActions() {
        getClaimAttachment = { [weak self] attachment in
            guard let self else { return }
            self.viewModel.getClaimAttachment(attachment: attachment)
        }
        getPolicyAttachment = { [weak self] policyAttachment in
            guard let self else { return }
            self.viewModel.getPolicyAttachment(policyAttachment: policyAttachment)
        }
    }
    
    private func setupNavigationBackButton(title: String) {
        navigationBackButton.update(title: title)
        navigationBackButton.update(action: goBackAction)
    }
}

// MARK: - Setup Cloud Action Cards
extension CloudPolicyFilesViewController {
    
    private func setupCloudPetFilesActionCards() -> [ActionCardData] {
        guard let fileIcon = UIImage(named: "ic-document"),
              let imageIcon = UIImage(named: "ic-gallery") else { return [] }
        
        var attachments: [ActionCardData] = []
        switch viewModel.petPolicyOption {
        case .claimDocumentsAndInvoices:
            guard let claimAttachments = viewModel.claimDocument?.claimDocuments else { return [] }
            
            for attachment in claimAttachments {
                let fileExtension = attachment.fileName.getFileExtension
                attachments.append(
                    ActionCardData(leadingImage: fileExtension == "pdf" ? fileIcon : imageIcon,
                                   leadingTitle: attachment.fileName,
                                   leadingSubtitle: attachment.fileSize.getBytesToKBytesFormatted,
                                   didTapAttachmentFile: getClaimAttachment,
                                   viewType: .file,
                                   dataType: .attachmentFile(attachment))
                )
            }
        case .medicalHistory:
            guard let policyAttachments = viewModel.policyAttachments else { return [] }
            for policyAttachment in policyAttachments {
                let fileExtension = policyAttachment.name.getFileExtension
                attachments.append(
                    ActionCardData(leadingImage: fileExtension == "pdf" ? fileIcon : imageIcon,
                                   leadingTitle: policyAttachment.name,
                                   leadingSubtitle: policyAttachment.fileSize.getBytesToKBytesFormatted,
                                   didTapPolicyAttachmentFile: getPolicyAttachment,
                                   viewType: .file,
                                   dataType: .policyAttachmentFile(policyAttachment))
                )
            }
        case .digitalVaccineCard:
            break
        case .policyDocuments:
            guard let policyDocuments = viewModel.policyDocuments else { return [] }
            for attachment in policyDocuments {
                guard let fileName = attachment.filename,
                      let fileExtension = fileName.getFileExtension else { return [] }
                attachments.append(
                    ActionCardData(leadingImage: fileExtension == "pdf" ? fileIcon : imageIcon,
                                   leadingTitle: fileName.truncateLongString,
                                   didTapDataAction: getDocument,
                                   dataType: .data(attachment))
                )
            }
        default: break
        }

        if attachments.isEmpty {
            showNoFilesFoundPlaceholder()
        }
        return attachments
    }
}

// MARK: - Private Methods
extension CloudPolicyFilesViewController {
    
    private func showNoFilesFoundPlaceholder() {
        noFilesFoundPlaceholderLabel.isHidden = false
    }

    private func setNavigationBackButtonVisibility(show: Bool) {
        titleLabel.isHidden = show
        navigationBackButton.isHidden = !show
    }
    
    private func showAttachmentOptionsView(_ attachment: Data) {
        DispatchQueue.main.async() { [weak self] in
            guard let self else { return }
            let activityViewController = UIActivityViewController(activityItems: [attachment], applicationActivities: nil)
            self.present(activityViewController, animated: true, completion: nil)
        }
    }
}

