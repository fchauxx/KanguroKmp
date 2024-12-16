import SwiftUI
import KanguroDesignSystem
import KanguroSharedDomain
import KanguroRentersDomain

public struct ScheduleItemAddPictureView: View, PresenterNavigationProtocol {
    
    // MARK: - Dependencies
    @ObservedObject var viewModel: ScheduleItemAddPictureViewModel
    @Environment(\.appLanguageValue) var language
    @State private var sourceType: UIImagePickerController.SourceType = .photoLibrary
    @State private var isImagePickerDisplay = false
    @State private var showButtonsModal = false
    
    // MARK: - Computed Properties
    var lang: String {
        language.rawValue
    }
    
    // MARK: - Actions
    var backAction: SimpleClosure?
    var closeAction: SimpleClosure?
    var didPicturesUploaded: SimpleClosure

    // MARK: - Initializer
    public init(viewModel: ScheduleItemAddPictureViewModel,
                backAction: SimpleClosure? = nil,
                closeAction: SimpleClosure? = nil,
                didPicturesUploaded: @escaping SimpleClosure) {
        self.viewModel = viewModel
        self.backAction = backAction
        self.closeAction = closeAction
        self.didPicturesUploaded = didPicturesUploaded
    }
    
    public var body: some View {
        ZStack {
            VStack {
                PresenterNavigationHeaderView(backAction: backAction)
                    .padding(.horizontal, InsetSpacing.md)
                    .padding(.top, InsetSpacing.xs)
                VStack {
                    ScheduledItemViewHeader(text: "scheduled.header.second.label".localized(lang))
                        .padding(.bottom, InsetSpacing.xs)
                    
                    UploadPictureSectionView(content: ImageThumbnailListView(items: viewModel.receiptOrAppraisalImages, didDeletedImage: { id in
                        viewModel.removeScheduledItem(id: id)
                    }),
                                             uploadSectionTitle: "scheduled.pictureReceiptOrAppraisal.label".localized(lang),
                                             didTapAddAction: {
                        showButtonsModal = true
                        viewModel.currentImageType = .ReceiptOrAppraisal
                    })
                    UploadPictureSectionView(content: ImageThumbnailListView(items: viewModel.itemImages, didDeletedImage: { id in
                        viewModel.removeScheduledItem(id: id)
                    }),
                                             uploadSectionTitle: "scheduled.pictureYourItems.label".localized(lang),
                                             didTapAddAction: {
                        showButtonsModal = true
                        viewModel.currentImageType = .Item
                    })
                    UploadPictureSectionView(content: ImageThumbnailListView(items: viewModel.itemWithReceiptOrAppraisalImages, didDeletedImage: { id in
                        viewModel.removeScheduledItem(id: id)
                    }),
                                             uploadSectionTitle: "scheduled.picturePlacedOnReceipt.label".localized(lang),
                                             didTapAddAction: {
                        showButtonsModal = true
                        viewModel.currentImageType = .ItemWithReceiptOrAppraisal
                    })
                    Spacer()
                    HStack {
                        PrimaryButtonView("common.done.label".localized(lang),
                                          height: HeightSize.lg,
                                          isDisabled: !viewModel.hasAtLeastOneImageForType) {
                            guard let scheduledItemId = viewModel.scheduledItemId else { return }
                            viewModel.updateScheduledItemsImages(scheduledItemId: scheduledItemId)
                        }
                    }
                    .padding(.top, InsetSpacing.lg)
                }
                .padding([.leading, .trailing, .bottom], InsetSpacing.md)
            } //: MainVStack
            .navigationBarBackButtonHidden(true)
            
            if showButtonsModal {
                addPhotoModalButtons()
            }
        } //: ZStack
        .sheet(isPresented: $isImagePickerDisplay) {
            ImagePickerView(sourceType: sourceType) { image in
                showButtonsModal = false
                addNewImage(image)
            }
        }
        .onChange(of: viewModel.isPicturesUploaded) { _ in
            if viewModel.isPicturesUploaded {
                didPicturesUploaded()
            }
        }
    }
    
    private func addNewImage(_ image: UIImage) {
        if let _ = viewModel.currentImageType {
            viewModel.createPicture(image: image)
        }
    }
    
    @ViewBuilder
    private func addPhotoModalButtons() -> some View {
        ActionCardListModalView(cardButtons: [
            CardButton(content: AnyView(ActionCardButton(title: "scheduled.takePicture.button".localized(lang),
                                                         icon: Image.cameraIcon,
                                                         style: .secondary,
                                                         didTapAction: {
                                                             sourceType = .camera
                                                             isImagePickerDisplay.toggle()
                                                         }))),
            CardButton(content: AnyView(ActionCardButton(title: "scheduled.selectPicture.button".localized(lang),
                                                         icon: Image.fileIcon,
                                                         style: .secondary,
                                                         didTapAction: {
                                                             sourceType = .photoLibrary
                                                             isImagePickerDisplay.toggle()
                                                         })))
        ], closeAction: {
            self.showButtonsModal = false
        })
    }
    
    
}

struct ScheduledItemAddPictureView_Previews: PreviewProvider {
    static var previews: some View {
        ScheduleItemAddPictureView(viewModel: ScheduleItemAddPictureViewModel(updateScheduledItemsImagesService: nil,
                                                                              createTemporaryPictureService: nil,
                                                                              receiptOrAppraisalImages: [
                                                                                ScheduledItemImage(id: 99, fileName: "", type: .Item)
                                                                              ],
                                                                              itemImages: [],
                                                                              itemWithReceiptOrAppraisalImages: [],
                                                                              scheduledItemId: "id"),
                                   backAction: {},
                                   closeAction: {},
                                   didPicturesUploaded: {})
        .previewDevice(
            PreviewDevice(
                rawValue: "iPhone 14 Pro")).previewDisplayName("iPhone 14 Pro")
        ScheduleItemAddPictureView(viewModel: ScheduleItemAddPictureViewModel(updateScheduledItemsImagesService: nil,
                                                                              createTemporaryPictureService: nil,
                                                                              receiptOrAppraisalImages: [],
                                                                              itemImages: [],
                                                                              itemWithReceiptOrAppraisalImages: [],
                                                                              scheduledItemId: "id"),
                                   backAction: {},
                                   closeAction: {},
                                   didPicturesUploaded: {})
        .previewDevice(
            PreviewDevice(
                rawValue: "iPhone SE (3rd generation)")).previewDisplayName("iPhone SE (3rd generation)")
        ScheduleItemAddPictureView(viewModel: ScheduleItemAddPictureViewModel(updateScheduledItemsImagesService: nil,
                                                                              createTemporaryPictureService: nil,
                                                                              receiptOrAppraisalImages: [
                                                                                ScheduledItemImage(id: 99, fileName: "", type: .ReceiptOrAppraisal)
                                                                              ],
                                                                              itemImages: [
                                                                                ScheduledItemImage(id: 99, fileName: "", type: .Item)
                                                                              ],
                                                                              itemWithReceiptOrAppraisalImages: [
                                                                                ScheduledItemImage(id: 99, fileName: "", type: .ItemWithReceiptOrAppraisal)
                                                                              ],
                                                                              scheduledItemId: "id"),
                                   backAction: {},
                                   closeAction: {},
                                   didPicturesUploaded: {})
        .previewDevice(
            PreviewDevice(
                rawValue: "iPhone 14")).previewDisplayName("iPhone 14")
    }
}
