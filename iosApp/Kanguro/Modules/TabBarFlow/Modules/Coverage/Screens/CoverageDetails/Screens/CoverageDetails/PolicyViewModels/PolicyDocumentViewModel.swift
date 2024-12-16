import UIKit
import Resolver
import Combine
import KanguroSharedDomain

class PolicyDocumentViewModel: PDFViewModelProtocol {
    
    // MARK: - Published Properties
    var statePublisher: Published<DefaultViewState>.Publisher { $state }
    @Published var state: DefaultViewState = .started
    
    // MARK: - Dependencies
    @LazyInjected var getPolicyDocumentService: GetPolicyDocumentUseCaseProtocol
    @LazyInjected var getPolicyRenterDocumentService: GetPolicyRenterDocumentUseCaseProtocol

    // MARK: - Stored Properties
    var policyId: String
    var document: KanguroSharedDomain.PolicyDocumentData
    var data: Data?
    var requestError = ""
    var selectedCloud: SelectedCloud?
    
    // MARK: - Initializers
    init(cloudType: CloudType? = nil,policyId: String, document: KanguroSharedDomain.PolicyDocumentData) {
        self.policyId = policyId
        self.document = document
        if (cloudType == CloudType.pet){
            self.getPolicyDocument()
        }
        else if (cloudType == CloudType.renters){
            self.getRentersPolicyDocument()
        }
    }
}

// MARK: - Network
extension PolicyDocumentViewModel {
    
    private func getPolicyDocument() {
        state = .loading
        guard let documentId = document.id,
              let filename = document.filename?.escaped else { return }
        let parameters = KanguroSharedDomain.PolicyDocumentParameters(policyId: policyId,
                                                  documentId: documentId,
                                                  filename: filename)
        getPolicyDocumentService.execute(parameters) { [weak self] response in
            guard let self = self else { return }
            switch response {
            case .failure(let error):
                self.requestError = error.errorMessage ?? "serverError.default".localized
                self.state = .requestFailed
            case .success(let document):
                self.data = document
                self.state = .requestSucceeded
            }
        }
    }
    
    private func getRentersPolicyDocument(){
        state = .loading
        guard let documentId = document.id,
              let filename = document.filename?.escaped else { return }
        let parameters = KanguroSharedDomain.PolicyDocumentParameters(policyId: policyId,
                                                  documentId: documentId,
                                                  filename: filename)
        getPolicyRenterDocumentService.execute(parameters) { [weak self] response in
            guard let self = self else { return }
            switch response {
            case .failure(let error):
                self.requestError = error.errorMessage ?? "serverError.default".localized
                self.state = .requestFailed
            case .success(let document):
                self.data = document
                self.state = .requestSucceeded
            }
        }
    }
}
