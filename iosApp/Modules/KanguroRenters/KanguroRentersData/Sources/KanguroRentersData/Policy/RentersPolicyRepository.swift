import Foundation
import KanguroSharedDomain
import KanguroNetworkDomain
import KanguroRentersDomain
import KanguroSharedData

public class RentersPolicyRepository: RentersPolicyRepositoryProtocol {

    private let network: NetworkProtocol

    public init(network: NetworkProtocol) {
        self.network = network
    }

    public func createScheduledItem(item: ScheduledItemParameters,
                                    policyId: String,
                                    completion: @escaping ((Result<ScheduledItem, RequestError>) -> Void)) {
        guard let type = item.type?.category.rawValue else { return }
        network.request(endpoint: RentersPolicyModuleEndpoint.createScheduledItem(policyId: policyId),
                        method: .POST,
                        parameters: RemoteScheduledItemParameters(name: item.name,
                                                                  type: RemoteScheduledItemCategoryType(rawValue: type),
                                                                  valuation: item.valuation),
                        responseType: RemoteScheduledItem.self,
                        errorType: NetworkRequestError.self) { response in
            ResponseHandler.handle(mapper: RentersScheduledItemMapper(),
                                   response: response,
                                   completion: completion)
        }
    }

    public func getScheduledItems(policyId: String,
                                  completion: @escaping ((Result<[ScheduledItem], RequestError>) -> Void)) {
        network.request(endpoint: RentersPolicyModuleEndpoint.getScheduledItems(policyId: policyId),
                        method: .GET,
                        responseType: [RemoteScheduledItem].self,
                        errorType: NetworkRequestError.self) { response in
            ResponseHandler.handle(mapper: RentersScheduledItemsMapper(),
                                   response: response,
                                   completion: completion)
        }
    }

    public func getScheduledItemsCategories(completion: @escaping ((Result<[ScheduledItemCategory], RequestError>) -> Void)) {
        network.request(endpoint: RentersPolicyModuleEndpoint.getCategories,
                        method: .GET,
                        responseType: [RemoteScheduledItemCategory].self,
                        errorType: NetworkRequestError.self) { response in
            ResponseHandler.handle(mapper: RentersScheduledItemsCategoryMapper(),
                                   response: response,
                                   completion: completion)
        }
    }

    public func updateScheduledItemPricing(item: ScheduledItemParameters,
                                           policyId: String,
                                           completion: @escaping ((Result<Pricing, RequestError>) -> Void)) {
        guard let type = item.type?.category.rawValue else { return }
        network.request(endpoint: RentersPolicyModuleEndpoint.updateScheduledItemPricing(policyId: policyId),
                        method: .POST,
                        parameters: RemoteScheduledItemParameters(type: RemoteScheduledItemCategoryType(rawValue: type),
                                                                  valuation: item.valuation),
                        responseType: RemotePricing.self,
                        errorType: NetworkRequestError.self) { response in
            ResponseHandler.handle(mapper: RentersPricingMapper(),
                                   response: response,
                                   completion: completion)
        }
    }


    public func deleteScheduledItem(policyId: String,
                                    scheduledItemId: String,
                                    completion: @escaping ((Result<Void, RequestError>) -> Void)) {
        network.request(endpoint: RentersPolicyModuleEndpoint.deleteScheduleItem(policyId: policyId,
                                                                                 scheduledItemId: scheduledItemId),
                        method: .DELETE,
                        errorType: NetworkRequestError.self) { response in
            switch response {
            case .success:
                completion(.success(()))
            case .failure(let error), .customError(let error):
                completion(.failure(RequestErrorMapper.map(error)))
            }
        }
    }

    public func updateScheduledItemImages(images: [ScheduledItemDefinitiveImageParameter],
                                          scheduledItemId: String,
                                          completion: @escaping ((Result<Void, RequestError>) -> Void)) {

        var remoteImagesParameters: [RemoteScheduledItemDefinitiveImageParameters] = []
        for image in images {
            if let type = image.type {
                let remoteType = RemoteScheduledItemImageType(rawValue: type.rawValue)
                remoteImagesParameters.append(RemoteScheduledItemDefinitiveImageParameters(fileId: image.fileId, type: remoteType))
            }
        }

        network.request(endpoint: RentersPolicyModuleEndpoint.updateTemporaryFiles(scheduledItemId: scheduledItemId),
                        method: .PUT,
                        parameters: remoteImagesParameters,
                        errorType: NetworkRequestError.self) { response in
            switch response {
            case .success:
                completion(.success(()))
            case .failure(let error), .customError(let error):
                completion(.failure(RequestErrorMapper.map(error)))
            }
        }
    }


    public func getAdditionalParties(policyId: String,
                                     completion: @escaping ((Result<[AdditionalPartie], RequestError>) -> Void)) {
        network.request(endpoint: RentersPolicyModuleEndpoint.additionalParties(policyId: policyId),
                        method: .GET,
                        responseType: [RemoteAdditionalPartie].self,
                        errorType: NetworkRequestError.self) { response in
            ResponseHandler.handle(mapper: RentersAdditionalPartiesMapper(),
                                   response: response,
                                   completion: completion)
        }
    }

    public func getRenterPolicy(id: String,
                                completion: @escaping ((Result<RenterPolicy, RequestError>) -> Void)) {
        network.request(endpoint: RentersPolicyModuleEndpoint.getRenterPolicy(id: id),
                        method: .GET,
                        responseType: RemoteRenterPolicy.self,
                        errorType: NetworkRequestError.self) { response in
            ResponseHandler.handle(mapper: RenterPolicyMapper(),
                                   response: response,
                                   completion: completion)
        }
    }

    public func getRenterPlanSummaryPricingItems(planSummaryEndpointType: String,
                                                 queries: [String : String],
                                                 completion: @escaping ((Result<[PlanSummaryItemData],
                                                                         RequestError>) -> Void)) {

        let endpoint = RentersPolicyModuleEndpoint.policyPricing(
            type: planSummaryEndpointType,
            queries: queries
        )

        network.request(endpoint: endpoint,
                        method: .GET,
                        responseType: [RemotePlanSummaryItemData].self,
                        errorType: NetworkRequestError.self) { response in
            ResponseHandler.handle(mapper: PoliciesPlanSummaryItemsMapper(),
                                   response: response,
                                   completion: completion)
        }
    }

    public func getRenterPolicies(completion: @escaping ((Result<[RenterPolicy], RequestError>) -> Void)) {
        network.request(endpoint: RentersPolicyModuleEndpoint.getRenterPolicies,
                        method: .GET,
                        responseType: [RemoteRenterPolicy].self,
                        errorType: NetworkRequestError.self) { response in
            ResponseHandler.handle(mapper: RenterPoliciesMapper(),
                                   response: response,
                                   completion: completion)
        }
    }

    public func updateRenterPolicy(item: PlanSummary,
                                   policyId: String,
                                   completion: @escaping ((Result<Void, RequestError>) -> Void)) {

        network.request(endpoint: RentersPolicyModuleEndpoint.updateRenterPolicy(policyId: policyId),
                        method: .POST,
                        parameters: convertToRemoteResumedPlanSummary(planSummary: item),
                        errorType: NetworkRequestError.self,
                        completion: { response in
            switch response {
            case .success:
                completion(.success(()))
            case .failure(let error), .customError(let error):
                completion(.failure(RequestErrorMapper.map(error)))
            }
        })
    }

    public func updateRenterPolicyPricing(item: PlanSummary,
                                          policyId: String,
                                          completion: @escaping ((Result<Pricing, RequestError>) -> Void)) {

        network.request(endpoint: RentersPolicyModuleEndpoint.updatePolicyPricing(policyId: policyId),
                        method: .POST,
                        parameters: convertToRemoteResumedPlanSummary(planSummary: item),
                        responseType: RemotePricing.self,
                        errorType: NetworkRequestError.self,
                        completion: { response in
            ResponseHandler.handle(mapper: RentersPricingMapper(),
                                   response: response,
                                   completion: completion)
        })
    }

    public func getRenterPersonalPropertyLimits(completion: @escaping ((Result<PersonalProperty,
                                                                        RequestError>) -> Void)) {
        network.request(endpoint: RentersPolicyModuleEndpoint.getRenterPersonalProperties,
                        method: .GET,
                        responseType: RemotePersonalProperty.self,
                        errorType: NetworkRequestError.self) { response in
            ResponseHandler.handle(mapper: RentersPersonalPropertyMapper(),
                                   response: response,
                                   completion: completion)
        }
    }
    
    public func getCloudDocument(by policyId: PolicyParameters, completion: @escaping ((Result<CloudDocumentPolicy, RequestError>) -> Void)) {
        network.request(
            endpoint: CloudDocumentModuleEndpoint.CloudDocumentsByPolicyId(
                policyId: policyId.id
            ),
            method: .GET,
            responseType: RemoteCloudDocumentPolicy.self,
            errorType: NetworkRequestError.self
        ) { response in
            ResponseHandler.handle(mapper: CloudDocumentPolicyMapper(),
                                   response: response,
                                   completion: completion)
        }
    }
}
