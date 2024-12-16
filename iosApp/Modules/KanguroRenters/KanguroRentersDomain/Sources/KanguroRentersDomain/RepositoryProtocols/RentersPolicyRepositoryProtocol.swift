import Foundation
import KanguroSharedDomain

public protocol RentersPolicyRepositoryProtocol {
    
    func createScheduledItem(item: ScheduledItemParameters,
                             policyId: String,
                             completion: @escaping ((Result<ScheduledItem, RequestError>) -> Void)
    )
    
    func getScheduledItems(policyId: String,
                           completion: @escaping ((Result<[ScheduledItem], RequestError>) -> Void)
    )
    
    func getScheduledItemsCategories(completion: @escaping ((Result<[ScheduledItemCategory],
                                                             RequestError>) -> Void)
    )
    
    func updateScheduledItemPricing(item: ScheduledItemParameters,
                                    policyId: String,
                                    completion: @escaping ((Result<Pricing, RequestError>) -> Void)
    )
    
    func deleteScheduledItem(policyId: String,
                             scheduledItemId: String,
                             completion: @escaping ((Result<Void, RequestError>) -> Void))
    
    func updateScheduledItemImages(images: [ScheduledItemDefinitiveImageParameter],
                                   scheduledItemId: String,
                                   completion: @escaping ((Result<Void, RequestError>) -> Void))
    
    func getAdditionalParties(policyId: String,
                              completion: @escaping ((Result<[AdditionalPartie],
                                                      RequestError>) -> Void)
    )
    
    func getRenterPolicy(id: String,
                         completion: @escaping ((Result<RenterPolicy, RequestError>) -> Void))
    
    func getRenterPolicies(completion: @escaping ((Result<[RenterPolicy], RequestError>) -> Void))
    
    func getRenterPlanSummaryPricingItems(planSummaryEndpointType: String,
                                          queries: [String : String],
                                          completion: @escaping ((Result<[PlanSummaryItemData],
                                                                  RequestError>) -> Void))
    
    func updateRenterPolicy(item: PlanSummary,
                            policyId: String,
                            completion: @escaping ((Result<Void, RequestError>) -> Void)
    )
    
    func updateRenterPolicyPricing(item: PlanSummary,
                                   policyId: String,
                                   completion: @escaping ((Result<Pricing, RequestError>) -> Void)
    )
    
    func getRenterPersonalPropertyLimits(completion: @escaping ((Result<PersonalProperty, RequestError>) -> Void))
    
    func getCloudDocument(
        by policyId: PolicyParameters,
        completion: @escaping ((Result<CloudDocumentPolicy, RequestError>) -> Void)
    )
}
