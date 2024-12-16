import UIKit
import Combine
import Resolver
import KanguroAnalyticsDomain
import KanguroSharedDomain
import KanguroPetDomain

enum CoverageDetailsViewState {
    
    case started
    case dataChanged
    case loading
    case requestFailed
    case requestSucceeded
    case requestPolicySucceeded
    case updatedPetPicture(_ image: UIImage)
}

class CoverageDetailsViewModel {
    
    // MARK: - Dependencies
    @LazyInjected var getPolicyDocumentsService: GetPolicyDocumentsUseCaseProtocol
    @LazyInjected var getPolicyService: GetPolicyUseCaseProtocol
    @LazyInjected var analytics: KanguroAnalyticsModuleProtocol
    @LazyInjected var updatePetPictureService: UpdatePetPictureUseCaseProtocol
    
    // MARK: - Published Properties
    @Published var state: CoverageDetailsViewState = .started
    
    // MARK: - Stored Properties
    var policy: PetPolicy
    var documents: [KanguroSharedDomain.PolicyDocumentData]?
    var requestError = ""
    
    // MARK: - Initializers
    init(policy: PetPolicy) {
        self.policy = policy
    }
}

// MARK: - Analytics
extension CoverageDetailsViewModel {
    
    func analyticsLogScreen() {
        analytics.analyticsLogScreen(screen: .CoverageDetails)
    }
}

// MARK: - Private Methods
extension CoverageDetailsViewModel {
 
    private func updatePolicy(policy: KanguroSharedDomain.Policy) {
        guard let deductable = policy.deductable,
              let reimbursment = policy.reimbursment,
              let sumInsured = policy.sumInsured,
              let status = policy.status,
              let payment = policy.payment else { return }
        self.policy.deductable = deductable
        self.policy.reimbursment = reimbursment
        self.policy.sumInsured = sumInsured
        self.policy.payment = payment
        self.policy.status = status
    }
}

// MARK: - Network
extension CoverageDetailsViewModel {
    
    private func manageSingleImage(_ image: UIImage) -> PetPictureBase64? {
        let targetSize = CGSize(width: 600, height: 600)
        let filesQuality = 0.35
        let scaledImage = image.scalePreservingAspectRatio(targetSize: targetSize)
        guard let encodedImage = scaledImage.jpegData(compressionQuality: filesQuality)?.base64EncodedString() else { return nil }
        return PetPictureBase64(fileInBase64: encodedImage, fileExtension: ".jpg")
    }
    
    func getPolicyDocuments() {
        state = .loading
        guard let id = policy.id else { return }
        let parameters = KanguroSharedDomain.PolicyParameters(id: id)
        getPolicyDocumentsService.execute(parameters) { [weak self] response in
            guard let self = self else { return }
            switch response {
            case .failure(let error):
                self.requestError = error.errorMessage ?? "serverError.default".localized
                self.state = .requestFailed
            case .success(let documents):
                self.documents = documents
                self.state = .requestSucceeded
            }

        }
    }
    
    func updatePetPicture(image: UIImage) {
        state = .loading
        let pet = policy.pet
        if let imageBase64 = manageSingleImage(image) {
            let parameters = UpdatePetPictureParameters(petId: pet.id, petPictureBase64: imageBase64)
            updatePetPictureService.execute(parameters: parameters) { [weak self] response in
                guard let self = self else { return }
                switch response {
                case .failure(let error):
                    self.requestError = error.errorMessage ?? "serverError.default".localized
                    self.state = .requestFailed
                case .success:
                    self.state = .updatedPetPicture(image)
                }
            }
        }
        else {
            self.requestError = "serverError.default".localized
            self.state = .requestFailed
        }
    }
    
    func getPolicy() {
        state = .loading
        guard let policyId = policy.id else { return }
        let parameters = KanguroSharedDomain.PolicyParameters(id: policyId)
        getPolicyService.execute(
            parameters,
            completion: { [weak self] response in
                guard let self = self else { return }
                switch response {
                case .failure(let error):
                    self.requestError = error.errorMessage ?? "serverError.default".localized
                    self.state = .requestFailed
                case .success(let policy):
                    self.updatePolicy(policy: policy)
                    self.state = .requestPolicySucceeded
                }
            }
        )
    }
}
