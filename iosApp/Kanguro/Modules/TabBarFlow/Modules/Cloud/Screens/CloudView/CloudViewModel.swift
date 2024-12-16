import UIKit
import Combine
import Resolver
import KanguroAnalyticsDomain
import KanguroSharedDomain
import KanguroSharedData
import KanguroPetDomain

enum CloudViewState {
    
    case started
    case loading
    case requestFailed
    case requestSucceeded
}

class CloudViewModel {
    
    // MARK: - Published Properties
    @Published var state: CloudViewState = .started
    
    // MARK: - Dependencies
    @LazyInjected var analytics: KanguroAnalyticsModuleProtocol
    @LazyInjected var petCloudDocumentService: GetPetCloudDocument

    // MARK: - Stored Properties
    var selectedCloud: SelectedCloud?
    var petList: [PetCloud] = []
    var rentersList: [RentersCloud] = []
    var cloudDocumentPolicies: [KanguroSharedDomain.CloudDocumentPolicy]?
    var requestError = ""
    
    // MARK: - Computed Properties
    var cloudName: String {
        guard let name = selectedCloud?.name else { return "cloud.breadcrumb.label".localized.uppercased() }
        return name.uppercased()
    }
    
    var policiesSortedByNewest: [KanguroSharedDomain.CloudDocumentPolicy]? {
        guard let policies = cloudDocumentPolicies else { return nil }
        let sortedList: [KanguroSharedDomain.CloudDocumentPolicy]? = policies.sorted(by: {
            guard let date1 = $0.policyStartDate,
                  let date2 = $1.policyStartDate else { return false }
            return (date1) > (date2)
        })
        return sortedList ?? policies
    }
    
    init(selectedCloud: SelectedCloud? = nil) {
        self.selectedCloud = selectedCloud
        cloudDocumentPolicies = selectedCloud?.cloudDocumentPolicies
    }
}

// MARK: - Analytics
extension CloudViewModel {
    
    func analyticsLogScreen() {
        analytics.analyticsLogScreen(screen: .Cloud)
    }
}

// MARK: - Public Methods
extension CloudViewModel {
    
    
    func shouldHiddenOrderByButton(viewType: CloudViewType) -> Bool {
        viewType != .petPolicies
    }
    
    func getBreadcrumbPathUppercased(viewType: CloudViewType) -> String {
        switch viewType {
            
        case .base:
            return "cloud.breadcrumb.label".localized.uppercased()
        case .petPolicies:
            return cloudName.uppercased()
        default:
            return ""
        }
    }
}

// MARK: - Network
extension CloudViewModel {
    
    func getCloudDocuments() {
        state = .loading
        petCloudDocumentService.execute { [weak self] (response: Result<CloudDocument, RequestError>) in
            guard let self else { return }
            switch response {
            case .success(let data):
                self.petList = data.pets ?? []
                self.rentersList = data.renters ?? []
                self.state = .requestSucceeded
            case .failure(let error):
                self.requestError = error.errorMessage ?? "serverError.default".localized
                self.state = .requestFailed
            }
        }
    }
}
