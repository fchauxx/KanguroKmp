import Foundation
import Resolver
import KanguroSharedDomain
import KanguroSharedData
import KanguroNetworkDomain
import KanguroPetData
import KanguroPetDomain

class PetClaimsModuleDependencies {
    
    // MARK: - Stored Properties
    var createPetClaimService: CreatePetClaimUseCaseProtocol?
    var createPetCommunicationService: CreatePetCommunicationsUseCaseProtocol?
    var createPetDocumentsService: CreatePetDocumentsUseCaseProtocol?
    var createPetVetDirectPayClaimService: CreatePetVetDirectPaymentClaimUseCaseProtocol?
    var createDTPVeterinarianSignatureService: CreateDirectPaymentVeterinarianSignatureUseCaseProtocol?
    var getDTPPreSignedFileURLService: GetPetVetDirectPaymentPreSignedFileURLUseCaseProtocol?
    var getPetClaimService: GetPetClaimUseCaseProtocol?
    var getPetClaimAttachmentService: GetPetClaimAttachmentUseCaseProtocol?
    var getPetClaimAttachmentsService: GetPetClaimAttachmentsUseCaseProtocol?
    var getPetClaimsService: GetPetClaimsUseCaseProtocol?
    var getPetCommunicationsService: GetPetCommunicationsUseCaseProtocol?
    var updatePetFeedbackService: PetUpdateFeedbackUseCaseProtocol?

    
    // MARK: - Initializers
    init(createPetClaimService: CreatePetClaimUseCaseProtocol? = nil,
         createPetCommunicationService: CreatePetCommunicationsUseCaseProtocol? = nil,
         createPetDocumentsService: CreatePetDocumentsUseCaseProtocol? = nil,
         createPetVetDirectPayClaimService: CreatePetVetDirectPaymentClaimUseCaseProtocol? = nil,
         createDTPVeterinarianSignatureService: CreateDirectPaymentVeterinarianSignatureUseCaseProtocol? = nil,
         getDTPPreSignedFileURLService: GetPetVetDirectPaymentPreSignedFileURLUseCaseProtocol? = nil,
         getPetClaimService: GetPetClaimUseCaseProtocol? = nil,
         getPetClaimAttachmentService: GetPetClaimAttachmentUseCaseProtocol? = nil,
         getPetClaimAttachmentsService: GetPetClaimAttachmentsUseCaseProtocol? = nil,
         getPetClaimsService: GetPetClaimsUseCaseProtocol? = nil,
         getPetCommunicationsService: GetPetCommunicationsUseCaseProtocol? = nil,
         updatePetFeedbackService: PetUpdateFeedbackUseCaseProtocol? = nil) {
        
        self.createPetClaimService = createPetClaimService
        self.createPetCommunicationService = createPetCommunicationService
        self.createPetDocumentsService = createPetDocumentsService
        self.createPetVetDirectPayClaimService = createPetVetDirectPayClaimService
        self.createDTPVeterinarianSignatureService = createDTPVeterinarianSignatureService
        self.getDTPPreSignedFileURLService = getDTPPreSignedFileURLService
        self.getPetClaimService = getPetClaimService
        self.getPetClaimAttachmentService = getPetClaimAttachmentService
        self.getPetClaimAttachmentsService = getPetClaimAttachmentsService
        self.getPetClaimsService = getPetClaimsService
        self.getPetCommunicationsService = getPetCommunicationsService
        self.updatePetFeedbackService = updatePetFeedbackService
    }
}

// MARK: - ModuleDependencies
extension PetClaimsModuleDependencies: ModuleDependencies {
    
    func setupDependencies() {

        let network: NetworkProtocol = Resolver.resolve()
        let petClaimRepository: PetClaimRepository = PetClaimRepository(network: network)
        Resolver.register { petClaimRepository }

        let createPetClaimService = self.createPetClaimService ?? CreatePetClaim(claimRepo: petClaimRepository)
        Resolver.register { createPetClaimService }

        let createPetCommunicationService = self.createPetCommunicationService ?? CreatePetCommunications(claimRepo: petClaimRepository)
        Resolver.register { createPetCommunicationService }

        let createPetDocumentsService = self.createPetDocumentsService ?? CreatePetDocuments(claimRepo: petClaimRepository)
        Resolver.register { createPetDocumentsService }

        let createPetVetDirectPayClaimService = self.createPetVetDirectPayClaimService ?? CreatePetVetDirectPaymentClaim(claimRepo: petClaimRepository)
        Resolver.register { createPetVetDirectPayClaimService }

        let createDTPVeterinarianSignatureService = self.createDTPVeterinarianSignatureService ?? CreateDirectPaymentVeterinarianSignature(claimRepo: petClaimRepository)
        Resolver.register { createDTPVeterinarianSignatureService }

        let getDTPPreSignedFileURLService = self.getDTPPreSignedFileURLService ?? GetPetVetDirectPaymentPreSignedFileURL(claimRepo: petClaimRepository)
        Resolver.register { getDTPPreSignedFileURLService }

        let getPetClaimService = self.getPetClaimService ?? GetPetClaim(claimRepo: petClaimRepository)
        Resolver.register { getPetClaimService }

        let getPetClaimAttachmentService = self.getPetClaimAttachmentService ?? GetPetClaimAttachment(claimRepo: petClaimRepository)
        Resolver.register { getPetClaimAttachmentService }

        let getPetClaimAttachmentsService = self.getPetClaimAttachmentsService ?? GetPetClaimAttachments(claimRepo: petClaimRepository)
        Resolver.register { getPetClaimAttachmentsService }

        let getPetClaimsService = self.getPetClaimsService ?? GetPetClaims(claimRepo: petClaimRepository)
        Resolver.register { getPetClaimsService }

        let getPetCommunicationsService = self.getPetCommunicationsService ?? GetPetCommunications(claimRepo: petClaimRepository)
        Resolver.register { getPetCommunicationsService }

        let updatePetFeedbackService = self.updatePetFeedbackService ?? PetUpdateFeeback(claimRepo: petClaimRepository)
        Resolver.register { updatePetFeedbackService }
    }
}
