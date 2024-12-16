import SwiftUI
import KanguroDesignSystem
import KanguroRentersDomain
import KanguroSharedDomain

public class EditAdditionalCoverageOptionsViewModel: ObservableObject {
    
    // MARK: - Stored Properties
    var policyId: String
    var planSummary: PlanSummary?
    
    // MARK: - Dependencies
    let updatePlanSummaryItemsService: UpdateRenterPlanSummaryItemsUseCaseProtocol
    let updatePolicyPricingService: UpdatePolicyPricingUseCaseProtocol
    
    // MARK: - Wrapped Properties
    @Published var cardsData: [ItemSelectionCardViewData]
    @Published var pricing: Pricing?
    @Published var requestError: String = ""
    @Environment(\.appLanguageValue) var language
    
    // MARK: - Computed Properties
    var lang: String {
        language.rawValue
    }
    
    // MARK: - Initializers
    public init(policyId: String,
                planSummary: PlanSummary? = nil,
                updatePlanSummaryItemsService: UpdateRenterPlanSummaryItemsUseCaseProtocol,
                updatePolicyPricingService: UpdatePolicyPricingUseCaseProtocol,
                cardsData: [ItemSelectionCardViewData],
                pricing: Pricing? = nil) {
        self.policyId = policyId
        self.planSummary = planSummary
        self.updatePlanSummaryItemsService = updatePlanSummaryItemsService
        self.updatePolicyPricingService = updatePolicyPricingService
        self.cardsData = cardsData
        self.pricing = pricing
    }
}

// MARK: - Network
extension EditAdditionalCoverageOptionsViewModel {
    
    func updateRenterPolicy() {
        guard let planSummary else { return }
        updatePlanSummaryItemsService.execute(policyId: policyId,
                                              item: planSummary) { [weak self] response in
            guard let self else { return }
            switch response {
            case .failure(let error):
                self.requestError = error.errorMessage ?? "serverError.default".localized(lang)
            case .success:
                break
            }
        }
    }
    
    func updatePolicyPricing() {
        guard let planSummary else { return }
        updatePolicyPricingService.execute(policyId: policyId,
                                           item: planSummary) { [weak self] response in
            guard let self else { return }
            switch response {
            case .failure(let error):
                self.requestError = error.errorMessage ?? "serverError.default".localized(lang)
            case .success(let pricing):
                self.pricing = pricing
            }
        }
    }
}
