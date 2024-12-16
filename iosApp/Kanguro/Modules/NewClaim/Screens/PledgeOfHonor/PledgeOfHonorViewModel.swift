import UIKit
import Combine
import Resolver
import KanguroAnalyticsDomain
import KanguroStorageDomain
import KanguroUserDomain
import KanguroPetDomain

enum PledgeOfHonorState {
    
    case started
    case beginToDraw
    case loading
    case requestFailed
    case requestSucceeded
}

class PledgeOfHonorViewModel {
    
    // MARK: - Dependencies
    @LazyInjected var analytics: KanguroAnalyticsModuleProtocol
    @LazyInjected var keychain: SecureStorage
    @LazyInjected var getLocalUserService: GetLocalUser
    @LazyInjected var createDTPClaimService: CreatePetVetDirectPaymentClaimUseCaseProtocol
    
    // MARK: - Published Properties
    @Published var state: PledgeOfHonorState = .started
    
    // MARK: - Stored Properties
    var signature: UIImage?
    var dtpClaimParameters: PetVetDirectPaymentParameters?
    var dtpNewClaim: PetClaim?
    var requestError: String
    
    // MARK: - Computed Properties
    var user: User? {
        guard let user: User = try? getLocalUserService.execute().get() else { return nil }
        return user
    }
    var userActualCause: UserDonationCause? {
        return user?.donation
    }

    init(signature: UIImage? = nil,
         dtpClaimParameters: PetVetDirectPaymentParameters? = nil,
         dtpNewClaim: PetClaim? = nil,
         requestError: String = "") {

        self.signature = signature
        self.dtpClaimParameters = dtpClaimParameters
        self.dtpNewClaim = dtpNewClaim
        self.requestError = requestError
    }
}

// MARK: - Analytics
extension PledgeOfHonorViewModel {
    
    func analyticsLogScreen() {
        analytics.analyticsLogScreen(screen: .PledgeOfHonor)
    }
}

// MARK: - Public Methods
extension PledgeOfHonorViewModel {
    
    func update(signature: UIImage) {
        self.signature = signature
    }

    func manageDTPSignature() {
        guard let signature,
              var dtpClaimParameters else { return }
        let encodedImage = signature.jpegData(compressionQuality: 0.35)?.base64EncodedString() ?? ""
        dtpClaimParameters.pledgeOfHonor = "\(encodedImage)"
        dtpClaimParameters.pledgeOfHonorExtension = ".jpg"
        self.dtpClaimParameters = dtpClaimParameters
    }
}

// MARK: - Network
extension PledgeOfHonorViewModel {

    func sendForm() {
        state = .loading
        guard let petVetDtpParameters = dtpClaimParameters else { return }
        createDTPClaimService.execute(petVetDtpParameters) { [weak self] result in
            guard let self else { return }
            switch result {
            case .success(let newPetClaim):
                self.dtpNewClaim = newPetClaim
                self.state = .requestSucceeded
            case .failure(let error):
                self.requestError = error.errorMessage ?? "serverError.default".localized
                self.state = .requestFailed
            }
        }
    }
}
