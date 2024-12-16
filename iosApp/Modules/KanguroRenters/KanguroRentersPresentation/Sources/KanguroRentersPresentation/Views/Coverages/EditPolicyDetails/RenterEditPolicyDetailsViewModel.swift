import SwiftUI
import KanguroRentersDomain
import KanguroSharedDomain

public enum PlanSummaryItemCategory {
    
    case deductible
    case liability
    case personalProperty
}

public class RenterEditPolicyDetailsViewModel: ObservableObject {
    
    // MARK: - Stored Properties
    var policy: RenterPolicy
    
    // MARK: - Wrapped Properties
    @Published var planSummary: PlanSummary?
    @Published var requestError: String = ""
    @Published var succeededRequestsCount: Int = 0
    @Published var isUpdatingPricing: Bool = false
    
    @Published var deductibleOptions: [PlanSummaryItemData] = []
    @Published var liabilitiesOptions: [PlanSummaryItemData] = []
    @Published var pricing: Pricing?
    @Published var personalProperty: PersonalProperty?
    
    @Environment(\.appLanguageValue) var language
    
    var isLoading: Bool {
        succeededRequestsCount != 3
    }
    
    // MARK: - Dependencies
    let getPlanSummaryItemsService: GetRenterPlanSummaryItemsUseCaseProtocol
    let updatePlanSummaryItemsService: UpdateRenterPlanSummaryItemsUseCaseProtocol
    let updatePolicyPricingService: UpdatePolicyPricingUseCaseProtocol
    let getPersonalPropertiesService: GetPersonalPropertiesUseCaseProtocol
    
    var lang: String {
        language.rawValue
    }
    
    // MARK: - Initializers
    public init(policy: RenterPolicy,
                planSummary: PlanSummary? = nil,
                getPlanSummaryItemsService: GetRenterPlanSummaryItemsUseCaseProtocol,
                updatePlanSummaryItemsService: UpdateRenterPlanSummaryItemsUseCaseProtocol,
                updatePolicyPricingService: UpdatePolicyPricingUseCaseProtocol,
                getPersonalPropertiesService: GetPersonalPropertiesUseCaseProtocol) {
        self.policy = policy
        self.planSummary = planSummary
        self.getPlanSummaryItemsService = getPlanSummaryItemsService
        self.updatePlanSummaryItemsService = updatePlanSummaryItemsService
        self.updatePolicyPricingService = updatePolicyPricingService
        self.getPersonalPropertiesService = getPersonalPropertiesService
        getData()
    }
}

// MARK: - Public Methods
extension RenterEditPolicyDetailsViewModel {
    
    func getData() {
        getDeductibles()
        getLiabilities()
        updatePolicyPricing()
        getPersonalProperty()
    }
    
    func updatePlanSummaryValue(type: PlanSummaryItemCategory, newValue: Double) {
        switch type {
        case .deductible:
            planSummary?.deductible?.value = newValue
        case .liability:
            planSummary?.liability?.value = newValue
        case .personalProperty:
            planSummary?.personalProperty?.value = newValue
        }
        
        updatePolicyPricing()
    }
}
