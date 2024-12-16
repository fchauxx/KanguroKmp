import UIKit
import Combine
import Resolver
import KanguroAnalyticsDomain
import KanguroSharedDomain
import KanguroSharedData
import KanguroPetDomain

enum CloudPolicyOptions: String, Codable, CaseIterable {
    
    case claimDocumentsAndInvoices
    case medicalHistory
    case digitalVaccineCard
    case policyDocuments
    
    var text: String {
        switch self {
            
        case .claimDocumentsAndInvoices:
            return "cloud.policyOption.claimDocumentsInvoices.label".localized
        case .medicalHistory:
            return "cloud.policyOption.medicalHistory.label".localized
        case .digitalVaccineCard:
            return "cloud.policyOption.digitalVaccine.label".localized
        case .policyDocuments:
            return "cloud.policyOption.policyDocuments.label".localized
        }
    }
}

class CloudPolicyDocumentsOptionsViewModel {
    
    // MARK: - Published Properties
    @Published var state: CloudViewState = .started
    
    // MARK: - Dependencies
    @LazyInjected var analytics: KanguroAnalyticsModuleProtocol
    @LazyInjected var getCloudDocumentByPolicy: GetCloudDocumentsByPolicyUseCaseProtocol

    // MARK: - Stored Properties
    var selectedCloud: SelectedCloud?
    var policyId: String?
    var claimDocument: ClaimDocument?
    var policyAttachments: [KanguroSharedDomain.PolicyAttachment]?
    var policyDocuments: [KanguroSharedDomain.PolicyDocumentData]?
    var claimDocuments: [KanguroSharedDomain.ClaimDocument]?
    var requestError = ""
    
    // MARK: - Computed Properties
    var cloudName: String {
        guard let name = selectedCloud?.name else { return "cloud.breadcrumb.label".localized.uppercased() }
        return name.uppercased()
    }
    
    init(selectedCloud: SelectedCloud?, policyId: String?) {
        self.selectedCloud = selectedCloud
        self.policyId = policyId
    }
}

// MARK: - Analytics
extension CloudPolicyDocumentsOptionsViewModel {
    
    func analyticsLogScreen() {
        analytics.analyticsLogScreen(screen: .Cloud)
    }
}

// MARK: - Public Methods
extension CloudPolicyDocumentsOptionsViewModel {
    
    func getBreadcrumbPathUppercased(viewType: CloudViewType) -> String {
        switch viewType {
        
        case .petPolicyDocumentsOptions, .petClaimAndInvoicesList:
            return "\(cloudName) \(viewType.breadcrumb)"
        default:
            return ""
        }
    }
}

// MARK: - Network
extension CloudPolicyDocumentsOptionsViewModel {

    func getCloudDocumentsByPolicyId() {
        guard let policyId else { return }
        state = .loading

        let parameters = PolicyParameters(id: policyId)
        getCloudDocumentByPolicy.execute(parameters) { [weak self] response in
            guard let self else { return }
            switch response {
            case .success(let data):
                self.policyAttachments = data.policyAttachments
                self.policyDocuments = data.policyDocuments
                self.claimDocuments = data.claimDocuments
                self.state = .requestSucceeded
                break
            case .failure(let error):
                self.requestError = error.errorMessage ?? "serverError.default".localized
                self.state = .requestFailed
            }
        }
    }
}
