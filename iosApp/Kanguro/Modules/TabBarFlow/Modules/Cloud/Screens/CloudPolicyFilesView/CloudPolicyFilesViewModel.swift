//
//  CloudPolicyFilesViewModel.swift
//  Kanguro
//
//  Created by Rodrigo Okido on 25/05/23.
//

import UIKit
import Combine
import Resolver
import KanguroAnalyticsDomain
import KanguroSharedDomain
import KanguroSharedData
import KanguroPetDomain

enum CloudClaimFilesViewState: Equatable {
    
    case started
    case loading
    case requestFailed
    case requestSucceeded
    case downloadSucceeded(_ attachment: Data)
}

class CloudPolicyFilesViewModel {
    
    // MARK: - Published Properties
    @Published var state: CloudClaimFilesViewState = .started
    
    // MARK: - Dependencies
    @LazyInjected var analytics: KanguroAnalyticsModuleProtocol
    @LazyInjected var getClaimDoc: GetClaimDocumentByPolicyAndClaimUseCaseProtocol
    @LazyInjected var getPetClaimAttachment: GetPetClaimAttachmentUseCaseProtocol
    @LazyInjected var getPolicyAttachmentService: GetPolicyAttachmentUseCaseProtocol

    // MARK: - Stored Properties
    var selectedCloud: SelectedCloud?
    var policyId: String?
    var petPolicyOption: CloudPolicyOptions?
    var claimDocument: KanguroSharedDomain.ClaimDocument?
    var policyAttachments: [KanguroSharedDomain.PolicyAttachment]?
    var policyDocuments: [KanguroSharedDomain.PolicyDocumentData]?
    var requestError = ""
    
    // MARK: - Computed Properties
    var cloudName: String {
        guard let name = selectedCloud?.name else { return "cloud.breadcrumb.label".localized.uppercased() }
        return name.uppercased()
    }
    
    init(selectedCloud: SelectedCloud?, policyId: String?, option: CloudPolicyOptions?) {
        self.selectedCloud = selectedCloud
        self.policyId = policyId
        self.petPolicyOption = option
    }
}

// MARK: - Analytics
extension CloudPolicyFilesViewModel {
    
    func analyticsLogScreen() {
        analytics.analyticsLogScreen(screen: .Cloud)
    }
}

// MARK: - Public Methods
extension CloudPolicyFilesViewModel {
    
    func getBreadcrumbPathUppercased(viewType: CloudViewType) -> String {
        guard let policyOption = petPolicyOption?.text.uppercased() else { return "" }
        var path = ""
        
        if viewType == .petFiles {
            path = "\(cloudName) \(viewType.breadcrumb)"
            if let claimId = claimDocument?.claimPrefixId {
                path.append(" / ... / \("cloud.claimCard.label".localized.uppercased()) \(claimId.uppercased())")
            } else {
                path.append(" / \(policyOption)")
            }
        }
        return path
    }
}

// MARK: - Network
extension CloudPolicyFilesViewModel {
    
    func getClaimDocumentsByPolicyIdAndClaimId() {
        guard let policyId,
              let claimId = claimDocument?.claimId else { return }
        state = .loading

        let parameters = ClaimDocumentsParameters(
            policyId: policyId,
            claimId: claimId
        )

        getClaimDoc.execute(parameters) { [weak self] result in
            guard let self else { return }
            switch result {
            case .success(let doc):
                self.claimDocument = doc
                self.state = .requestSucceeded
            case .failure(let error):
                self.requestError = error.errorMessage ?? "serverError.default".localized
                self.state = .requestFailed
            }
        }
    }
    
    func getClaimAttachment(attachment: KanguroSharedDomain.Attachment) {
        guard let claimId = claimDocument?.claimId else { return }
        state = .loading

        let parameters =  PetClaimAttachmentsParameters(claimId: claimId, attachment: attachment)
        getPetClaimAttachment.execute(parameters) { response in
            switch response {
            case .failure(let error):
                self.requestError = error.errorMessage ?? "serverError.default".localized
                self.state = .requestFailed
            case .success(let attachment):
                self.state = .downloadSucceeded(attachment)
            }
        }
    }
    
    func getPolicyAttachment(policyAttachment: KanguroSharedDomain.PolicyAttachment) {
        guard let policyId else { return }
        state = .loading

        let parameter = KanguroSharedDomain.PolicyAttachmentParameters(
            policyId: policyId,
            attachment: policyAttachment
        )
        getPolicyAttachmentService.execute(parameter) { response in
            switch response {
            case .failure(let error):
                self.requestError = error.errorMessage ?? "serverError.default".localized
                self.state = .requestFailed
            case .success(let attachment):
                self.state = .downloadSucceeded(attachment)
            }
        }
    }
}

