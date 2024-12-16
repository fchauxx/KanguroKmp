import Foundation
import UIKit
import Resolver
import KanguroAnalyticsDomain
import KanguroSharedDomain
import KanguroPetDomain

enum ShareWithVetViewState {

    case started
    case uploadSucceeded(_ data: TemporaryFile)
    case getPreSignedURLSucceeded
    case loading
    case requestFailed
    case requestSucceeded
}

class ShareWithVetViewModel {

    // MARK: - Dependencies
    @LazyInjected var analytics: KanguroAnalyticsModuleProtocol
    @LazyInjected var uploadService: CreateTemporaryFilesUseCaseProtocol
    @LazyInjected var createDTPVetSignatureService: CreateDirectPaymentVeterinarianSignatureUseCaseProtocol
    @LazyInjected var getDirectPaymentPreSignedFileUrl: GetPetVetDirectPaymentPreSignedFileURLUseCaseProtocol

    // MARK: - Published Properties
    @Published var state: ShareWithVetViewState = .started

    // MARK: - Stored Properties
    var petClaim: PetClaim
    var uploadedFilesIds: [Int]
    var requestError: String

    // MARK: - Initializers
    init(petClaim: PetClaim,
         uploadedFilesIds: [Int] = [],
         requestError: String = "") {
        self.petClaim = petClaim
        self.uploadedFilesIds = uploadedFilesIds
        self.requestError = requestError
    }
}

// MARK: - Analytics
extension ShareWithVetViewModel {

    func analyticsLogScreen() {
        analytics.analyticsLogScreen(screen: .DTPShareWithVet)
    }
}

// MARK: - Public Methods
extension ShareWithVetViewModel {

    func getImageName(info: [UIImagePickerController.InfoKey : Any]) -> String {
        if let imageUrl = info[.imageURL] as? URL {
            let filename = imageUrl.lastPathComponent
            return filename
        }
        return "image_\(uploadedFilesIds.count)"
    }

    func manageAttachments(images: [UIImage], filename: String) {

        guard let image = images.first,
              let imageData = image.jpegData(compressionQuality: 0.75) else {
            print("Failed to convert image to data")
            return
        }
        uploadSignedDocument(file: imageData, filename: filename)
    }

    func manageFile(file: URL) {
        do {
            let fileData = try Data.init(contentsOf: file)
            uploadSignedDocument(file: fileData, filename: file.lastPathComponent)
        } catch {
            print("Error: Failed trying to initialize data")
        }
    }
}

// MARK: - Network
extension ShareWithVetViewModel {

    public func getDTPPreSignedURL(claimId: String) {
        state = .loading
        getDirectPaymentPreSignedFileUrl.execute(claimId) { [weak self] result in
            guard let self else { return }
            switch result {
            case .success(let url):
                self.petClaim.fileUrl = url
                self.state = .getPreSignedURLSucceeded
            case .failure(let error):
                self.requestError = error.errorMessage ?? "serverError.default".localized
                self.state = .requestFailed
            }
        }
    }

    public func uploadSignedDocument(file: Data, filename: String) {
        state = .loading
        uploadService.execute(tempFile: file) { [weak self] result in
            guard let self else { return }
            switch result {

            case .success(let tempFile):
                guard let tempFileId = tempFile.id,
                      let tempUrl = tempFile.url else { return }

                let newTemporaryFile = TemporaryFile(filename: filename,
                                                     url: tempUrl,
                                                     id: tempFileId)
                self.uploadedFilesIds.append(tempFileId)
                self.state = .uploadSucceeded(newTemporaryFile)
            case .failure(let error):
                self.requestError = error.errorMessage ?? "serverError.default".localized
                self.state = .requestFailed
            }
        }
    }

    public func createDTPVetSignature() {
        state = .loading
        createDTPVetSignatureService.execute(claimId: petClaim.id,
                                             parameters: UploadedDocumentParameters(fileIds: uploadedFilesIds)) { [weak self] result in
            guard let self else { return }
            switch result {
            case .success:
                self.state = .requestSucceeded
                break
            case .failure(let error):
                self.requestError = error.errorMessage ?? "serverError.default".localized
                self.state = .requestFailed
            }
        }
    }
}
