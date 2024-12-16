import Foundation
import SwiftUI
import KanguroRentersDomain
import KanguroSharedDomain

public class ScheduleItemAddPictureViewModel: ObservableObject {
    
    // MARK: - Dependencies
    let updateScheduledItemsImagesService: UpdateScheduledItemsImagesUseCaseProtocol?
    let createTemporaryPictureService: CreateTemporaryFilesUseCaseProtocol?
    
    // MARK: - Wrapped Properties
    @Environment(\.appLanguageValue) var language
    @Published public var receiptOrAppraisalImages: [ScheduledItemImage]
    @Published public var itemImages: [ScheduledItemImage]
    @Published public var itemWithReceiptOrAppraisalImages: [ScheduledItemImage]
    @Published public var currentImageType: ScheduledItemImageType?
    @Published public var isPicturesUploaded: Bool
    @Published var isLoading: Bool
    
    // MARK: - Stored Properties
    var scheduledItemId: String?
    var requestError: String?
    
    // MARK: - Computed Properties
    var lang: String {
        language.rawValue
    }
    public var hasAtLeastOneImageForType: Bool {
        return !receiptOrAppraisalImages.isEmpty &&
        !itemImages.isEmpty &&
        !itemWithReceiptOrAppraisalImages.isEmpty
    }
    private var allImages: [ScheduledItemImage] {
        receiptOrAppraisalImages +
        itemImages +
        itemWithReceiptOrAppraisalImages
    }
    
    public init(updateScheduledItemsImagesService: UpdateScheduledItemsImagesUseCaseProtocol?,
                createTemporaryPictureService: CreateTemporaryFilesUseCaseProtocol?,
                receiptOrAppraisalImages: [ScheduledItemImage],
                itemImages: [ScheduledItemImage],
                itemWithReceiptOrAppraisalImages: [ScheduledItemImage],
                isPicturesUploaded: Bool = false,
                isLoading: Bool = false,
                scheduledItemId: String? = nil,
                requestError: String? = nil) {
        self.updateScheduledItemsImagesService = updateScheduledItemsImagesService
        self.createTemporaryPictureService = createTemporaryPictureService
        self.receiptOrAppraisalImages = receiptOrAppraisalImages
        self.itemImages = itemImages
        self.itemWithReceiptOrAppraisalImages = itemWithReceiptOrAppraisalImages
        self.isPicturesUploaded = isPicturesUploaded
        self.isLoading = isLoading
        self.scheduledItemId = scheduledItemId
        self.requestError = requestError
    }
    
    public func createPicture(image: UIImage) {
        guard let scheduledItemId,
              let imageData = image.jpegData(compressionQuality: 1.0) else {
            print("Failed to convert image to data")
            return
        }
        createTemporaryPicture(image: imageData, scheduledItemId: scheduledItemId)
    }

    func removeScheduledItem(id: Int) {
        receiptOrAppraisalImages = receiptOrAppraisalImages.filter { $0.id != id }
        itemImages = itemImages.filter { $0.id != id }
        itemWithReceiptOrAppraisalImages = itemWithReceiptOrAppraisalImages.filter { $0.id != id }
    }
}

// MARK: - Private Methods
extension ScheduleItemAddPictureViewModel {

    private func addNewLocalPicture(image: TemporaryFile) {
        guard let currentImageType else { return }
        switch currentImageType {
        case .ReceiptOrAppraisal:
            receiptOrAppraisalImages.append(ScheduledItemImage(id: image.id, type: currentImageType, url: image.url))
        case .Item:
            itemImages.append(ScheduledItemImage(id: image.id, type: currentImageType, url: image.url))
        case .ItemWithReceiptOrAppraisal:
            itemWithReceiptOrAppraisalImages.append(ScheduledItemImage(id: image.id, type: currentImageType, url: image.url))
        }
    }

    private func mapToSendDefinitiveScheduledItemImages() -> [ScheduledItemDefinitiveImageParameter] {

        var definitiveImages: [ScheduledItemDefinitiveImageParameter] = []
        for image in allImages {
            definitiveImages.append(ScheduledItemDefinitiveImageParameter(fileId: image.id, type: image.type))
        }
        return definitiveImages
    }
}

// MARK: - Network
extension ScheduleItemAddPictureViewModel {
    
    private func createTemporaryPicture(image: Data, scheduledItemId: String) {
        guard let createTemporaryPictureService else { return }
        isLoading = true
        createTemporaryPictureService.execute(tempFile: image) { [weak self] response in
            guard let self else { return }
            switch response {
            case .failure(let error):
                switch error {
                default:
                    self.requestError = error.errorMessage ?? "serverError.default".localized(lang)
                }
            case .success(let temporaryImage):
                addNewLocalPicture(image: temporaryImage)
            }
            isLoading = false
        }
    }
    
    public func updateScheduledItemsImages(scheduledItemId: String) {
        guard let updateScheduledItemsImagesService else { return }
        isLoading = true
        updateScheduledItemsImagesService.execute(images: mapToSendDefinitiveScheduledItemImages(),
                                                  scheduledItemId: scheduledItemId) { [weak self] response in
            guard let self else { return }
            switch response {
            case .failure(let error):
                switch error {
                default:
                    self.requestError = error.errorMessage ?? "serverError.default".localized(lang)
                }
                self.isPicturesUploaded = false
            case .success:
                self.isPicturesUploaded = true
                break
            }
            isLoading = false
        }
    }
}
