import Foundation
import KanguroChatbotDomain
import _PhotosUI_SwiftUI
import KanguroSharedDomain
import AVFoundation

enum UploadFileRequestStatus {
    
    case sendingError
    case uploadingError
    case uploading
    case uploadSucceeded
    case uploadExternalLinkSucceeded
    
    // MARK: - Computed Properties
    var message: String {
        switch self {
        case .sendingError:
            return "onboarding.chatbot.sendVideo.error.message"
        case .uploadingError:
            return "onboarding.chatbot.uploadVideo.error.message"
        case .uploading:
            return "onboarding.chatbot.uploadingVideo.message"
        default:
            return ""
        }
    }
}

public class UploadFileViewModel: NSObject, ObservableObject {
    
    // MARK: - Dependencies
    let getTemporaryFileService: GetTemporaryFilesUseCaseProtocol?
    let uploadTemporaryFileService: UploadTemporaryFilesUseCaseProtocol?
    let createTemporaryFileService: CreateTemporaryFilesUseCaseProtocol?
    weak var delegate: ChatbotExternalFlowDelegate?
    
    // MARK: - Wrapped Properties
    @Published var selectedVideo: PhotosPickerItem?
    @Published var recordedVideo: Movie?
    @Published var file: Movie?
    @Published var uploadFileRequestStatus: UploadFileRequestStatus?
    @Published private var externalTemporaryFile: TemporaryFile?
    @Published var requestError: RequestError?
    @Published var isLoading: Bool = false
    
    // MARK: - Computed Properties
    var videoData: Data? {
        guard let file else { return nil }
        return try? Data(contentsOf: file.url)
    }
    var canProceed: Bool {
        uploadFileRequestStatus == .uploadExternalLinkSucceeded && !isLoading
    }
    
    public init(getTemporaryFileService: GetTemporaryFilesUseCaseProtocol? = nil,
                uploadTemporaryFileService: UploadTemporaryFilesUseCaseProtocol? = nil,
                createTemporaryFileService: CreateTemporaryFilesUseCaseProtocol? = nil,
                delegate: ChatbotExternalFlowDelegate? = nil) {
        self.getTemporaryFileService = getTemporaryFileService
        self.uploadTemporaryFileService = uploadTemporaryFileService
        self.createTemporaryFileService = createTemporaryFileService
        self.delegate = delegate
        super.init()
        self.getExternalUploadURL()
    }
}

// MARK: - Chatbot
extension UploadFileViewModel {
    
    func submitItems() {
        guard let delegate, 
        let fileId = externalTemporaryFile?.id else { return }
        if videoData != nil {
            delegate.didReceiveExternalFlowResponse([fileId], chatText: "🎥")
        } else {
            closeWithoutSave()
        }
        
        self.uploadFileRequestStatus = .uploadSucceeded
    }
    
    func closeWithoutSave() {
        guard let delegate else { return }
        let emptyArray: [Int] = []
        delegate.didReceiveExternalFlowResponse(emptyArray, chatText: "Later") // TODO: add localized message
    }
}

// MARK: - Network
extension UploadFileViewModel {
    
    func uploadFile(_ file: Movie) {
        self.file = file
        self.isLoading = true
        uploadVideoToExternalLink()
    }
    
    private func getExternalUploadURL() {
        getTemporaryFileService?.execute { result in
            switch result {
            case .success(let temporaryFile):
                self.externalTemporaryFile = temporaryFile
            case .failure(let error):
                self.requestError = error
            }
        }
    }
    
    private func uploadVideoToExternalLink() {
        guard let videoData,
              let url = externalTemporaryFile?.url,
              let blobType = externalTemporaryFile?.blobType else {
            uploadFileRequestStatus = .uploadingError
            self.isLoading = false
            return
        }
        uploadFileRequestStatus = .uploading
        uploadTemporaryFileService?.execute(
            url: url,
            headers: ["x-ms-blob-type": blobType],
            tempFile: videoData
        ) { result in
            switch result {
            case .success:
                self.uploadFileRequestStatus = .uploadExternalLinkSucceeded
                self.isLoading = false
            case .failure(let error):
                //getting error in here
                self.requestError = error
            }
        }
    }
    
    func createTemporaryFile() {
        guard let fileId = externalTemporaryFile?.id else {
            self.isLoading = false
            return
        }
        self.isLoading = true
        
        let data = withUnsafeBytes(of: fileId) { Data($0) }
        createTemporaryFileService?.execute(tempFile: data) { completion in
            switch completion {
            case .success:
                self.uploadFileRequestStatus = .uploadSucceeded
            case .failure(let error):
                self.requestError = error
            }
            self.isLoading = false
        }
    }
}
