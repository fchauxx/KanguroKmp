import UIKit
import Combine
import Resolver
import KanguroAnalyticsDomain
import KanguroSharedDomain
import KanguroSharedData
import KanguroPetDomain

enum ClaimDetailsViewState {
    case started
    case loading
    case loadingAttachment
    case requestFailed
    case requestSucceeded
    case downloadSucceeded(_ attachment: Data)
    case postAttachmentSucceeded
    case imageFormatNotSupported
}

class ClaimDetailsViewModel {
    
    // MARK: - Dependencies
    @LazyInjected var getClaimsService: GetPetClaimsUseCaseProtocol
    @LazyInjected var getClaimAttachmentsService: GetPetClaimAttachmentsUseCaseProtocol
    @LazyInjected var getClaimAttachmentService: GetPetClaimAttachmentUseCaseProtocol
    @LazyInjected var createCommunicationsService: CreatePetCommunicationsUseCaseProtocol
    @LazyInjected var analytics: KanguroAnalyticsModuleProtocol
    
    // MARK: - Published Properties
    @Published var state: ClaimDetailsViewState = .started
    
    // MARK: - Stored Properties
    var claim: PetClaim
    var requestError = ""
    var attachments: [KanguroSharedDomain.Attachment]?
    var filesQuality = 0.35
    var communicationParameter: PetCommunicationParameters
    
    // MARK: - Initializers
    init(claim: PetClaim, communicationParameter: PetCommunicationParameters) {
        self.claim = claim
        self.communicationParameter = communicationParameter
    }
    
    // MARK: - Computed Properties
    var petName: String {
        return claim.pet?.name ?? ""
    }
    var isClaimInReviewAndHasPendingCommunication: Bool {
        guard let claimStatus = claim.status else { return false }
        return claim.isPendingCommunication && claimStatus == .InReview
    }
    var shouldShowSubmitDocumentButton: Bool {
        guard let claimStatus = claim.status else { return false }
        return claimStatus == .InReview || claimStatus == .Submitted || claimStatus == .Assigned || claimStatus == .PendingMedicalHistory
    }
}

// MARK: - Analytics
extension ClaimDetailsViewModel {
    
    func analyticsLogScreen() {
        analytics.analyticsLogScreen(screen: .ClaimDetails)
    }
}

// MARK: - Public Methods
extension ClaimDetailsViewModel {
    
    func manageAttachments(images: [UIImage]) {
        state = .loadingAttachment
        images.forEach { image in
            guard let encodedImage = image.jpegData(compressionQuality: filesQuality)?.base64EncodedString() else { return }
            let base64Image = "{\"fileInBase64\": \"\(encodedImage)\", \"fileExtension\": \".jpg\"}"
            updateCommunicationParameterFiles(file: base64Image)
        }
        postCommunications(parameters: communicationParameter)
    }
    
    func manageFile(file: URL) {
        state = .loading
        do {
            let fileData = try Data.init(contentsOf: file)
            let encodedFile: String = fileData.base64EncodedString(options: NSData.Base64EncodingOptions.init(rawValue: 0))
            
            let base64File = "{\"fileInBase64\": \"\(encodedFile)\", \"fileExtension\": \".pdf\"}"
            updateCommunicationParameterFiles(file: base64File)
            postCommunications(parameters: communicationParameter)
        } catch {
            print("Error: Failed trying to initialize data")
        }
    }
}

// MARK: - Private Methods
extension ClaimDetailsViewModel {
    
    private func updateCommunicationParameterFiles(file: String) {
        communicationParameter.files.append(file)
    }
}

// MARK: - Network
extension ClaimDetailsViewModel {
    
    func getAttachments() {
        state = .loading
        getClaimAttachmentsService.execute(PetClaimParameters(id: claim.id)) { response in
            switch response {
            case .failure(let error):
                self.requestError = error.errorMessage ?? "serverError.default".localized
                self.state = .requestFailed
            case .success(let attachments):
                self.attachments = attachments
                self.state = .requestSucceeded
            }
        }
    }
    
    func getClaimAttachment(attachment: KanguroSharedDomain.Attachment) {
        state = .loadingAttachment
        getClaimAttachmentService.execute(
            PetClaimAttachmentsParameters(
                claimId: claim.id,
                attachment: attachment
            )
        ) { response in
            switch response {
            case .failure(let error):
                self.requestError = error.errorMessage ?? "serverError.default".localized
                self.state = .requestFailed
            case .success(let attachment):
                self.state = .downloadSucceeded(attachment)
            }
        }
    }
    
    func postCommunications(parameters: PetCommunicationParameters) {
        state = .loadingAttachment
        let parameter = PetCommunicationParameters(id: claim.id, message: "", files: communicationParameter.files)
        createCommunicationsService.execute(parameter) { [weak self] response in
            guard let self else { return }
            switch response {
            case .failure(let error):
                self.requestError = error.errorMessage ?? "serverError.default".localized
                self.state = .requestFailed
            case .success:
                self.communicationParameter.files.removeAll()
                self.state = .postAttachmentSucceeded
            }
        }
    }
}
