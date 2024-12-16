extension RenterEditPolicyDetailsViewModel {
    
    func getDeductibles() {
        guard let state = policy.address?.state else { return }
        getPlanSummaryItemsService.execute(planSummaryEndpointType: .Deductibles,
                                           queries: ["State" : state]) { [weak self] response in
            guard let self else { return }
            switch response {
            case .failure(let error):
                self.requestError = error.errorMessage ?? "serverError.default".localized(lang)
            case .success(let deductibles):
                self.deductibleOptions = deductibles
            }
            succeededRequestsCount += 1
        }
    }
    
    func getLiabilities() {
        guard let state = policy.address?.state else { return }
        getPlanSummaryItemsService.execute(planSummaryEndpointType: .Liabilities,
                                           queries: ["State" : state]) { [weak self] response in
            guard let self else { return }
            switch response {
            case .failure(let error):
                self.requestError = error.errorMessage ?? "serverError.default".localized(lang)
            case .success(let liabilitiesOptions):
                self.liabilitiesOptions = liabilitiesOptions
            }
            succeededRequestsCount += 1
        }
    }
    
    func getAdditionalCoverages() {
        #warning("not being used yet")
        guard let dwellingType = policy.dwellingType,
              let planSummary = policy.planSummary,
              let deductible = planSummary.deductible,
              let liability = planSummary.liability,
              let personalProperty = planSummary.personalProperty,
              let address = policy.address,
              let state = address.state,
              let zipCode = address.zipCode,
              let isInsuranceRequired = policy.isInsuranceRequired else {
            return
        }
        let queries: [String : String] = [
            "DwellingType" : dwellingType.rawValue,
            "DeductibleId" : String(deductible.id),
            "LiabilityId" : String(liability.id),
            "PersonalProperty" : String(personalProperty.value),
            "State" : state,
            "ZipCode" : String(zipCode),
            "IsInsuranceRequired" : String(isInsuranceRequired)
        ]
        getPlanSummaryItemsService.execute(planSummaryEndpointType: .AdditionalCoverages,
                                           queries: queries) { [weak self] response in
            guard let self else { return }
            switch response {
            case .failure(let error):
                self.requestError = error.errorMessage ?? "serverError.default".localized(lang)
            case .success(let liabilitiesOptions):
                self.liabilitiesOptions = liabilitiesOptions
            }
//            succeededRequestsCount += 1
        }
    }
    
    func updateRenterPolicy() {
        guard let planSummary else { return }
        updatePlanSummaryItemsService.execute(policyId: policy.id,
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
        self.isUpdatingPricing = true
        updatePolicyPricingService.execute(policyId: policy.id,
                                           item: planSummary) { [weak self] response in
            guard let self else { return }
            switch response {
            case .failure(let error):
                self.requestError = error.errorMessage ?? "serverError.default".localized(lang)
            case .success(let pricing):
                self.pricing = pricing
            }
            self.isUpdatingPricing = false
        }
    }
    
    func getPersonalProperty() {
        getPersonalPropertiesService.execute { [weak self] response in
            guard let self else { return }
            switch response {
            case .failure(let error):
                self.requestError = error.errorMessage ?? "serverError.default".localized(lang)
            case .success(let personalProperty):
                self.personalProperty = personalProperty
            }
            succeededRequestsCount += 1
        }
    }
}
