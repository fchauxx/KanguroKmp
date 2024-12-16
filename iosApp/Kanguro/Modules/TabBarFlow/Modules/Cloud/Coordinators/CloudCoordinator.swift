import Foundation
import KanguroSharedDomain
import KanguroPetDomain

class CloudCoordinator: Coordinator {
    
    // MARK: - Stored Properties
    private lazy var attachmentAction: AnyClosure = { [weak self] data in
        guard let self,
              let fileData = data as? Data else { return }
        self.navigation.dismiss(animated: true)
        self.navigateToFilePreview(data: fileData)
    }
    
    // MARK: - Coordinator
    override func start() {
        navigateToCloud()
    }
}

// MARK: - NavigateToCloud
extension CloudCoordinator {
    
    func navigateToCloud() {
        let controller = CloudViewController(type: .base)
        controller.viewModel = CloudViewModel()
        controller.goBackAction = self.back
        
        controller.getCloudPolicies = { [weak self] selectedCloud in
            guard let self else { return }
            self.navigateToCloudPolicies(selectedCloud)
        }
        navigation.pushViewController(controller, animated: true)
    }
    
    func navigateToCloudPolicies(_ selectedCloud: SelectedCloud) {
        let controller = CloudViewController(type: .petPolicies)
        controller.viewModel = CloudViewModel(selectedCloud: selectedCloud)

        controller.goBackAction = self.back
        controller.getCloudPolicyOptionsDocuments = { [weak self] policy, selectedCloud in
            guard let self else { return }
            self.navigateToCloudPolicyDocumentsOptions(policy, selectedCloud)
        }
        self.navigation.pushViewController(controller, animated: true)
    }
    
    func navigateToCloudPolicyDocumentsOptions(_ policy: KanguroSharedDomain.CloudDocumentPolicy, _ selectedCloud: SelectedCloud) {
        let controller = CloudPolicyDocumentsOptionsViewController(type: .petPolicyDocumentsOptions)
        controller.viewModel = CloudPolicyDocumentsOptionsViewModel(selectedCloud: selectedCloud, policyId: policy.id)

        controller.goBackAction = self.back
        controller.getCloudClaimAndInvoiceList = { [weak self] id, claims, selectedCloud in
            guard let self else { return }
            self.navigateToCloudPetClaimAndInvoicesList(id, claims, selectedCloud)
        }
        controller.getPolicyAttachments = { [weak self] id, policyAttachments, selectedCloud in
            guard let self else { return }
            self.navigateToCloudPolicyAttachmentsFileList(id, policyAttachments, selectedCloud)
        }
        controller.getPolicyDocuments = { [weak self] id, policyDocuments, selectedCloud in
            guard let self else { return }
            self.navigateToCloudPetPolicyDocumentsFileList(id, policyDocuments, selectedCloud)
        }
        self.navigation.pushViewController(controller, animated: true)
    }
    
    func navigateToCloudPetClaimAndInvoicesList(_ policyId: String, _ claims: [KanguroSharedDomain.ClaimDocument], _ selectedCloud: SelectedCloud) {
        let controller = CloudPolicyDocumentsOptionsViewController(type: .petClaimAndInvoicesList)
        controller.viewModel = CloudPolicyDocumentsOptionsViewModel(selectedCloud: selectedCloud, policyId: policyId)
        controller.viewModel.claimDocuments = claims
        
        controller.goBackAction = self.back
        controller.getFiles = { [weak self] policyId, claimDocuments, selectedCloud in
            guard let self else { return }
            self.navigateToPetClaimFileList(policyId, claimDocuments, selectedCloud)
        }
        self.navigation.pushViewController(controller, animated: true)
    }
    
    func navigateToPetClaimFileList(_ policyId: String, _ document: KanguroSharedDomain.ClaimDocument, _ selectedCloud: SelectedCloud) {
        let controller = CloudPolicyFilesViewController(type: .petFiles)
        controller.viewModel = CloudPolicyFilesViewModel(
            selectedCloud: selectedCloud,
            policyId: policyId,
            option: .claimDocumentsAndInvoices
        )
        controller.viewModel.claimDocument = document
        controller.didTapAttachmentAction = attachmentAction
        
        controller.goBackAction = self.back
        self.navigation.pushViewController(controller, animated: true)
    }
    
    func navigateToCloudPolicyAttachmentsFileList(_ policyId: String, _ files: [KanguroSharedDomain.PolicyAttachment], _ selectedCloud: SelectedCloud) {
        let controller = CloudPolicyFilesViewController(type: .petFiles)
        controller.viewModel = CloudPolicyFilesViewModel(
            selectedCloud: selectedCloud,
            policyId: policyId,
            option: .medicalHistory
        )
        controller.viewModel.policyAttachments = files
        
        controller.didTapAttachmentAction = attachmentAction
        controller.goBackAction = self.back
        self.navigation.pushViewController(controller, animated: true)
    }
    
    func navigateToCloudPetPolicyDocumentsFileList(_ policyId: String, _ files: [KanguroSharedDomain.PolicyDocumentData], _ selectedCloud: SelectedCloud) {
        let controller = CloudPolicyFilesViewController(type: .petFiles)
        controller.viewModel = CloudPolicyFilesViewModel(
            selectedCloud: selectedCloud,
            policyId: policyId,
            option: .policyDocuments
        )
        controller.viewModel.policyDocuments = files

        controller.goBackAction = self.back
        controller.getDocument = { [weak self] document in
            guard let self,
                  let id = controller.viewModel.policyId else { return }
            if let document = document as?KanguroSharedDomain.PolicyDocumentData {
                  self.navigateToPolicyDocument(document: document, policyId: id, selectedCloud)
              }
        }
        self.navigation.pushViewController(controller, animated: true)
    }
    
    private func navigateToPolicyDocument(document: KanguroSharedDomain.PolicyDocumentData, policyId: String, _ selectedCloud: SelectedCloud) {
        let controller = PDFViewController()
        controller.viewModel = PolicyDocumentViewModel(cloudType: selectedCloud.type, policyId: policyId, document: document)
        controller.backAction = { [weak self] in
            guard let self = self else { return }
            self.navigation.dismiss(animated: true)
        }
        navigation.present(controller, animated: true)
    }
    
    func navigateToFilePreview(data: Data) {
        let controller = FilePreviewViewController()
        controller.viewModel = FilePreviewViewModel(data: data)
        navigation.present(controller, animated: true)
    }
}
