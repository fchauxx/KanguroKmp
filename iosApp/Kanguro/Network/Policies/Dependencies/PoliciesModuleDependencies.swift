import Foundation
import KanguroRentersData
import KanguroSharedDomain
import KanguroRentersDomain
import Resolver
import KanguroSharedData
import KanguroNetworkDomain

class PoliciesModuleDependencies {
    
    // MARK: - Stored Properties
    var policyRepository: PolicyRepositoryProtocol?
    var rentersRepository: RentersPolicyRepositoryProtocol?
    
    var getPolicyDocumentsService: GetPolicyDocumentsUseCaseProtocol?
    var getPolicyService: GetPolicyUseCaseProtocol?
    var getPoliciesService: GetPoliciesUseCaseProtocol?
    var getPolicyDocumentService: GetPolicyDocumentUseCaseProtocol?
    var getPolicyRenterDocumentService: GetPolicyRenterDocumentUseCaseProtocol?
    var getPolicyAttachmentService: GetPolicyAttachmentUseCaseProtocol?
    var getCoveragesService: GetCoveragesUseCaseProtocol?
    var getRenterPoliciesService: GetRenterPoliciesUseCaseProtocol?
    
    // MARK: - Initializers
    init(
        policyRepository: PolicyRepositoryProtocol? = nil,
        rentersRepository: RentersPolicyRepositoryProtocol? = nil,
        getPolicyDocumentsService: GetPolicyDocumentsUseCaseProtocol? = nil,
        getPolicyService: GetPolicyUseCaseProtocol? = nil,
        getPoliciesService: GetPoliciesUseCaseProtocol? = nil,
        getPolicyDocumentService: GetPolicyDocumentUseCaseProtocol? = nil,
        getPolicyRenterDocumentService: GetPolicyRenterDocumentUseCaseProtocol? = nil,
        getPolicyAttachmentService: GetPolicyAttachmentUseCaseProtocol? = nil,
        getCoverages: GetCoveragesUseCaseProtocol? = nil,
        getRenterPoliciesService: GetRenterPoliciesUseCaseProtocol? = nil
    ) {
        self.policyRepository = policyRepository
        self.rentersRepository = rentersRepository
        self.getPolicyDocumentsService = getPolicyDocumentsService
        self.getPolicyService = getPolicyService
        self.getPoliciesService = getPoliciesService
        self.getPolicyDocumentService = getPolicyDocumentService
        self.getPolicyRenterDocumentService = getPolicyRenterDocumentService
        self.getPolicyAttachmentService = getPolicyAttachmentService
        self.getCoveragesService = getCoverages
        self.getRenterPoliciesService = getRenterPoliciesService
    }
}

// MARK: - ModuleDependencies
extension PoliciesModuleDependencies: ModuleDependencies {
    
    func setupDependencies() {
        
        let network: NetworkProtocol = Resolver.resolve()
        let policyRepository = self.policyRepository ?? PolicyRepository(network: network)
        let rentersRepository = self.rentersRepository ?? RentersPolicyRepository(network: network)
        
        Resolver.register { policyRepository }
        
        let getPolicyDocumentsService = self.getPolicyDocumentsService ?? GetPolicyDocuments(policyRepo: policyRepository)
        Resolver.register { getPolicyDocumentsService }
        
        let getPolicyService = self.getPolicyService ?? GetPolicy(policyRepo: policyRepository)
        Resolver.register { getPolicyService }
        
        let getPoliciesService = self.getPoliciesService ?? GetPolicies(policyRepo: policyRepository)
        Resolver.register { getPoliciesService }
        
        let getPolicyDocumentService = self.getPolicyDocumentService ?? GetPolicyDocument(policyRepo: policyRepository)
        Resolver.register { getPolicyDocumentService }
        
        let getPolicyRenterDocumentService = self.getPolicyRenterDocumentService ?? GetPolicyRenterDocument(policyRepo: policyRepository)
        Resolver.register { getPolicyRenterDocumentService }
        
        let getPolicyAttachmentService = self.getPolicyAttachmentService ?? GetPolicyAttachment(policyRepo: policyRepository)
        Resolver.register { getPolicyAttachmentService }
        
        let getCoverages = self.getCoveragesService ?? GetCoverages(policyRepo: policyRepository)
        Resolver.register { getCoverages }
        
        let getRenterPoliciesService = self.getRenterPoliciesService ?? GetRenterPolicies(rentersPolicyRepo: rentersRepository)
        Resolver.register { getRenterPoliciesService }
    }
}
