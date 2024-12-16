import SwiftUI
import KanguroRentersDomain
import KanguroSharedDomain

public class ScheduleNewItemViewModel: ObservableObject {

    // MARK: - Dependencies
    // Kept service as optional just to be more easier to make unit test.
    let updatePricingService: UpdateScheduledItemPricingUseCaseProtocol?
    let createScheduledItemService: CreateScheduledItemUseCaseProtocol?

    // MARK: - Wrapped Properties
    @Published public var temporaryScheduledItem: ScheduledItem
    @Published public var scheduledItemPricing: Pricing?
    @Published public var scheduledItemCreationSucceed: Bool
    @Published public var isPricingLoading: Bool = false
    @Environment(\.appLanguageValue) var language

    // MARK: - Stored Properties
    var policyId: String?
    var requestError: String?

    // MARK: - Computed Properties
    var lang: String {
        language.rawValue
    }
    public var newEndorsementPolicyPrice: String {
        guard let value = scheduledItemPricing?.billingCycleEndorsementPolicyValue else { return "+$0" }
        return "+$\(String(format: "%.2f",value))"
    }
    public var newDifferenceValuePrice: String {
        guard let value = scheduledItemPricing?.billingCyclePolicyPriceDifferenceValue else { return "$0" }
        return "$\(String(format: "%.2f",value))"
    }

    public init(updatePricingService: UpdateScheduledItemPricingUseCaseProtocol?,
                createScheduledItemService: CreateScheduledItemUseCaseProtocol?,
                temporaryScheduledItem: ScheduledItem = ScheduledItem(),
                scheduledItemPricing: Pricing? = nil,
                scheduledItemCreationSucceed: Bool = false,
                policyId: String? = nil,
                requestError: String? = nil) {
        self.updatePricingService = updatePricingService
        self.createScheduledItemService = createScheduledItemService
        self.temporaryScheduledItem = temporaryScheduledItem
        self.scheduledItemPricing = scheduledItemPricing
        self.scheduledItemCreationSucceed = scheduledItemCreationSucceed
        self.policyId = policyId
        self.requestError = requestError
    }
}

// MARK: - Network
extension ScheduleNewItemViewModel {

    public func updateNewItemPricing() {
        guard let updatePricingService,
              let policyId,
              let type = temporaryScheduledItem.category,
              let valuation = temporaryScheduledItem.valuation else { return }

        let scheduledItem: ScheduledItemParameters = ScheduledItemParameters(type: type,
                                                                             valuation: valuation)

        updatePricingService.execute(scheduledItem, policyId: policyId) { [weak self] response in
            guard let self else { return }
            switch response {
            case .failure(let error):
                switch error {
                default:
                    self.requestError = error.errorMessage ?? "serverError.default".localized(lang)
                }
            case .success(let newScheduledItemPricing):
                self.scheduledItemPricing = newScheduledItemPricing
            }

            withAnimation(.easeOut(duration: 1)) {
                self.isPricingLoading = false
            }
        }
    }

    public func createNewScheduledItem() {
        guard let createScheduledItemService,
              let policyId,
              let name = temporaryScheduledItem.name,
              let type = temporaryScheduledItem.category,
              let valuation = temporaryScheduledItem.valuation else { return }

        let scheduledItemParameters: ScheduledItemParameters = ScheduledItemParameters(name: name,
                                                                                       type: type,
                                                                                       valuation: valuation)

        createScheduledItemService.execute(scheduledItemParameters,
                                           policyId: policyId) { [weak self] response in
            guard let self else { return }
            switch response {
            case .failure(let error):
                self.scheduledItemCreationSucceed = false
                switch error {
                default:
                    self.requestError = error.errorMessage ?? "serverError.default".localized(lang)
                }
            case .success(_):
                self.scheduledItemCreationSucceed = true
                break
            }
        }
    }
}
