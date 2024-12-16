//
//  CloudPolicyOptionsViewController.swift
//  Kanguro
//
//  Created by Rodrigo Okido on 25/05/23.
//

import UIKit

class CloudPolicyDocumentsOptionsViewController: BaseViewController {

    // MARK: - Dependencies
    var viewModel: CloudPolicyDocumentsOptionsViewModel!

    // MARK: - IB Outlets
    @IBOutlet private var scrollView: UIScrollView!
    @IBOutlet var titleLabel: CustomLabel!
    @IBOutlet var navigationBackButton: NavigationBackButton!
    @IBOutlet var breadcrumb: CustomLabel!
    @IBOutlet var cloudActionList: ActionCardsList!
    @IBOutlet var noClaimsFoundPlaceholderLabel: CustomLabel!

    // MARK: - Stored Properties
    var viewType: CloudViewType
    let breadcrumbStyle: TextStyle = TextStyle(color: .secondaryDark, weight: .bold, size: .p12, font: .raleway)

    // MARK: - Actions
    var goBackAction: SimpleClosure = {}
    var getPolicyAttachments: CloudPolicyAttachmentsClosure = { (_,_,_) in }
    var getPolicyDocuments: CloudPolicyDocumentsClosure = { (_, _, _) in }
    var getCloudClaimAndInvoiceList: CloudClaimsAndInvoicesListClosure = { _, _, _ in }
    var getFiles: CloudFilesClosure = { (_, _, _) in }

    // MARK: - Initializers
    init(type: CloudViewType) {
        self.viewType = type
        super.init(nibName: nil, bundle: nil)
    }

    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
}

// MARK: - Life Cycle
extension CloudPolicyDocumentsOptionsViewController {

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
extension CloudPolicyDocumentsOptionsViewController {

    private func changed(state: CloudViewState) {

        switch state {
        case .started:
            viewModel.analyticsLogScreen()
            viewType == .petPolicyDocumentsOptions ? viewModel.getCloudDocumentsByPolicyId() : setupCloudActionCardLists()
        case .loading:
            showLoadingView(shouldAnimate: false)
        case .requestFailed:
            hideLoadingView()
            showSimpleAlert(message: viewModel.requestError)
        case .requestSucceeded:
            hideLoadingView()
            setupCloudActionCardLists()
        }
    }
}

// MARK: - Setup
extension CloudPolicyDocumentsOptionsViewController {

    private func setupLayout() {
        setupViews()
        setupLabels()
    }

    private func setupViews() {
        breadcrumb.isHidden = false
        noClaimsFoundPlaceholderLabel.isHidden = true
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
        noClaimsFoundPlaceholderLabel.set(text: "cloud.noClaims.placeholder.label".localized,
                                          style: TextStyle(color: .secondaryMedium, size: .p16))
    }

    private func setupCloudActionCardLists() {
        cloudActionList.clearActionCards()
        switch viewType {
        case .petPolicyDocumentsOptions:
            cloudActionList.addActionCards(actionCardDataList: setupPetPolicyDocumentsOptionsActionCards())
        case .petClaimAndInvoicesList:
            cloudActionList.addActionCards(actionCardDataList: setupCloudPetClaimAndInvoicesListActionCards())
        default: break
        }
    }

    private func setupNavigationBackButton(title: String) {
        navigationBackButton.update(title: title)
        navigationBackButton.update(action: goBackAction)
    }
}

// MARK: - Setup Cloud Action Cards
extension CloudPolicyDocumentsOptionsViewController {

    private func setupPetPolicyDocumentsOptionsActionCards() -> [ActionCardData] {
        guard let selectedCloud = viewModel.selectedCloud,
              let policyId = viewModel.policyId,
              let icon = UIImage(named: "ic-folder") else { return [] }

        var policyOptions: [ActionCardData] = []
        for type in CloudPolicyOptions.allCases {
            switch type {
            case .claimDocumentsAndInvoices:
                if let claimDocuments = viewModel.claimDocuments, !claimDocuments.isEmpty {
                    policyOptions.append(
                        ActionCardData(
                            leadingImage: icon,
                            leadingTitle: CloudPolicyOptions.claimDocumentsAndInvoices.text,
                            didTapClaimAndInvoicesOption: getCloudClaimAndInvoiceList,
                            dataType: .cloudClaimList(
                                policyId,
                                claimDocuments,
                                selectedCloud
                            )
                        )
                    )
                }

            case .medicalHistory:
                if let policyAttachment = viewModel.policyAttachments, !policyAttachment.isEmpty {
                    policyOptions.append(
                        ActionCardData(
                            leadingImage: icon,
                            leadingTitle: CloudPolicyOptions.medicalHistory.text,
                            didTapCloudPolicyAttachmentsFilesActionCard: getPolicyAttachments,
                            dataType: .cloudPolicyAttachmentsFiles(
                                policyId,
                                policyAttachment,
                                selectedCloud
                            )

                        )
                    )
                }
            case .digitalVaccineCard:
                break
            case .policyDocuments:
                if let policyDocuments = viewModel.policyDocuments, !policyDocuments.isEmpty {
                    policyOptions.append(
                        ActionCardData(
                            leadingImage: icon,
                            leadingTitle: CloudPolicyOptions.policyDocuments.text,
                            didTapCloudPolicyDocumentsFilesActionCard: getPolicyDocuments,
                            dataType: .cloudPolicyDocumentsFiles(
                                policyId,
                                policyDocuments,
                                selectedCloud
                            )
                        )
                    )
                }
            }
        }
        return policyOptions
    }

    private func setupCloudPetClaimAndInvoicesListActionCards() -> [ActionCardData] {
        guard let selectedCloud = viewModel.selectedCloud,
              let policyId = viewModel.policyId,
              let claimDocuments = viewModel.claimDocuments,
              let icon = UIImage(named: "ic-folder") else { return [] }

        var claims: [ActionCardData] = []
        for (index, claim) in claimDocuments.enumerated() {
            if let claimId = claim.claimPrefixId {
                claims.append(
                    ActionCardData(
                        leadingImage: icon,
                        leadingTitle: "\("cloud.claimCard.label".localized) \(claimId)",
                        didTapCloudClaimFilesActionCard: getFiles,
                        dataType: .cloudClaimFiles(policyId, claimDocuments[index], selectedCloud)
                    )
                )
            }
        }
        if claims.isEmpty {
            showNoFilesFoundPlaceholder()
        }
        return claims
    }
}

// MARK: - Private Methods
extension CloudPolicyDocumentsOptionsViewController {

    private func showNoFilesFoundPlaceholder() {
        noClaimsFoundPlaceholderLabel.isHidden = false
    }

    private func setNavigationBackButtonVisibility(show: Bool) {
        titleLabel.isHidden = show
        navigationBackButton.isHidden = !show
    }
}
