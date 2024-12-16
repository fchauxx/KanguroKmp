import SwiftUI
import KanguroRentersDomain
import KanguroSharedDomain
import KanguroUserDomain

public class RenterCoverageDetailsViewModel: ObservableObject {
    
    // MARK: - Dependencies
    let getRenterPolicyService: GetRenterPolicyUseCaseProtocol
    let getScheduledItemsService: GetScheduledItemsUseCaseProtocol
    let getAdditionalPartiesService: GetAdditionalPartiesUseCaseProtocol
    let getPolicyDocumentService: GetCloudDocumentsByPolicyUseCaseProtocol

    // MARK: - Stored Properties
    var policy: RenterPolicy
    var user: User?
    
    // MARK: - Wrapped Properties
    @Environment(\.appLanguageValue) var language
    @Published public var isLoading = false
    @Published public var additionalParties: [AdditionalPartie]?
    @Published public var scheduledItems: [ScheduledItem]?
    @Published public var documents: [PolicyDocumentData]?

    
    @Published var requestError: String = ""
    
    // MARK: - Computed Properties
    var lang: String {
        language.rawValue
    }
    var userName: String? {
        guard let user else { return nil }
        return user.givenName
    }
    var scheduledItemsValue: Double? {
        guard let scheduledItems else { return nil }
        var value: Double = 0
        scheduledItems.forEach { item in
            if let itemValue = item.valuation {
                value += itemValue
            }
        }
        return value
    }
    var policyId: String {
        return policy.id
    }
    
    public init(policy: RenterPolicy,
                user: User? = nil,
                getRenterPolicyService: GetRenterPolicyUseCaseProtocol,
                getScheduledItemsService: GetScheduledItemsUseCaseProtocol,
                getAdditionalPartiesService: GetAdditionalPartiesUseCaseProtocol,
                getPolicyDocumentsService: GetCloudDocumentsByPolicyUseCaseProtocol
    ) {
        self.policy = policy
        self.user = user
        self.getRenterPolicyService = getRenterPolicyService
        self.getScheduledItemsService = getScheduledItemsService
        self.getAdditionalPartiesService = getAdditionalPartiesService
        self.getPolicyDocumentService = getPolicyDocumentsService
    }
}

// MARK: - Network
extension RenterCoverageDetailsViewModel {
    
    public func getData() {
        isLoading = true
        getCloudDocumentsByPolicyId()
        getScheduledItems()
        getAdditionalParties()
    }
    
    
    func getCloudDocumentsByPolicyId() {
        let parameters = PolicyParameters(id: policyId)
        getPolicyDocumentService.execute(parameters) { [weak self] response in
            guard let self else { return }
            switch response {
            case .success(let data):
                self.documents = data.policyDocuments
                break
            case .failure(let error):
                switch error {
                default:
                    self.requestError = error.errorMessage ?? "serverError.default".localized(lang)
                }
            }
        }
    }
    
    
    private func getScheduledItems() {
        getScheduledItemsService.execute(policyId: policyId) { [weak self] response in
            guard let self else { return }
            switch response {
            case .failure(let error):
                switch error {
                default:
                    self.requestError = error.errorMessage ?? "serverError.default".localized(lang)
                }
            case .success(let scheduledItems):
                self.scheduledItems = scheduledItems
            }
            isLoading = false
        }
    }
    
    private func getAdditionalParties() {
        getAdditionalPartiesService.execute(policyId: policyId) { [weak self] response in
            guard let self else { return }
            switch response {
            case .failure(let error):
                switch error {
                default:
                    self.requestError = error.errorMessage ?? "serverError.default".localized(lang)
                }
            case .success(let additionalParties):
                self.additionalParties = additionalParties
            }
            isLoading = false
        }
    }
}
