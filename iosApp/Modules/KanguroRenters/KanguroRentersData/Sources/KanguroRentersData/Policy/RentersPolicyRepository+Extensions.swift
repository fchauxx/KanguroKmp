import KanguroRentersDomain

extension RentersPolicyRepository {
    
    func convertToRemoteResumedPlanSummary(planSummary: PlanSummary) -> RemoteResumedPlanSummaryParameters? {
        var remotePlanSummary: RemoteResumedPlanSummaryParameters?
        
        if let liability = planSummary.liability,
           let deductible = planSummary.deductible,
           let personalProperty = planSummary.personalProperty {
            remotePlanSummary = RemoteResumedPlanSummaryParameters(
                deductibleId: deductible.value,
                liabilityId: liability.value,
                personalProperty: personalProperty.value)
        }
        
        return remotePlanSummary
    }
}
