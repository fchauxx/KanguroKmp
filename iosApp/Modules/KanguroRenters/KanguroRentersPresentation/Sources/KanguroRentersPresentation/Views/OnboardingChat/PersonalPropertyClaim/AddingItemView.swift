import SwiftUI
import PhotosUI
import AVKit
import KanguroDesignSystem

private enum FileType {
    case item
    case warranty
}

public struct AddingItemView: View {
    
    // MARK: - Wrapped Properties
    @ObservedObject var viewModel: AddingItemViewModel
    @Environment(\.appLanguageValue) var language
    
    @State private var showButtonsModal = false
    @State private var showCamera = false
    @State private var showGallery = false
    @State private var showCloseActionPopUp = false
    @State private var fileType: FileType?
    
    // MARK: - Actions
    var closeAction: SimpleClosure
    var doneAction: SimpleClosure
    
    // MARK: - Computed Properties
    var lang: String {
        language.rawValue
    }
    private var canProceed: Bool {
        !viewModel.itemValue.isEmpty &&
        !viewModel.itemName.isEmpty
    }
    
    // MARK: - Initializers
    public init(viewModel: AddingItemViewModel,
                closeAction: @escaping SimpleClosure,
                doneAction: @escaping SimpleClosure) {
        self.viewModel = viewModel
        self.closeAction = closeAction
        self.doneAction = doneAction
    }
    
    public var body: some View {
        ZStack {
            VStack(spacing: InsetSpacing.xs) {
                PresenterNavigationHeaderView(closeAction: closeAction)
                    .padding(.horizontal, InsetSpacing.xxs)
                
                makeContent()
                    .padding(.horizontal, InsetSpacing.md)
            }
            
            if showButtonsModal {
                addPhotoModalButtons()
            }
        }
        .onAppear {
            hideNavigationTabBar()
        }
        .sheet(isPresented: $showCamera, content: {
            CameraView(viewModel: viewModel,
                       showButtonsModal: $showButtonsModal,
                       isCameraPresented: $showCamera)
        })
        .photosPicker(isPresented: $showGallery,
                      selection: $viewModel.selectedPhoto,
                      matching: .images)
        .onChange(of: viewModel.selectedPhoto) { selectedPhoto in
            guard viewModel.selectedPhoto != nil else { return }
            if let fileType {
                switch fileType {
                case .item:
                    viewModel.itemFile = selectedPhoto
                case .warranty:
                    viewModel.warrantyFile = selectedPhoto
                }
            }
            viewModel.selectedPhoto = nil
            self.showButtonsModal = false
        }
    }
    
    @ViewBuilder
    private func makeContent() -> some View {
        VStack(alignment: .leading, spacing: InsetSpacing.md) {
            VStack(alignment: .leading, spacing: InsetSpacing.xxs) {
                Text("renters.chat.propertyClaims.title".localized(lang).uppercased())
                    .bodySecondaryDarkBold()
                
                Text("renters.chat.propertyClaims.scheduledItems".localized(lang))
                    .title3SecondaryDarkestBold()
            }
            
            makeTextFields()
            makeAddItemViews()
            
            Spacer()
            
            PrimaryButtonView("common.done.label".localized(lang),
                              height: HeightSize.lg,
                              isDisabled: !canProceed,
                              {
                doneAction()
            })
            .padding(.bottom, InsetSpacing.md)
        }
    }
    
    @ViewBuilder
    private func makeTextFields() -> some View {
        VStack {
            CustomTextfieldView(fieldTitle: "renters.chat.propertyClaims.item".localized(lang),
                                placeholder: "renters.chat.propertyClaims.whatsItem".localized(lang),
                                value: $viewModel.itemName)
            
            CurrencyTextfieldView(fieldTitle: "renters.chat.propertyClaims.value".localized(lang),
                                  placeholder: "U$",
                                  didEndEditingAction: { value in
                viewModel.itemValue = value.wrappedValue
            })
        }
    }
    
    @ViewBuilder
    private func makeAddItemViews() -> some View {
        VStack {
            LabelsStackButtonView(title: "renters.chat.propertyClaims.itemTitle".localized(lang),
                                  description: "renters.chat.propertyClaims.itemDescription".localized(lang),
                                  buttonTitle: "renters.chat.propertyClaims.add".localized(lang),
                                  action: {
                self.showButtonsModal = true
                self.fileType = .item
            })
            
            if viewModel.itemFile != nil {
                VideoFileComponent(type: .regular,
                                   label: "renters.chat.propertyClaims.itemImage".localized(lang),
                                   icon: Image.videoPlayIcon,
                                   removeVideoAction: {
                    viewModel.itemFile = nil
                })
            }
        }
        
        VStack {
            LabelsStackButtonView(title: "renters.chat.propertyClaims.warrantyTitle".localized(lang),
                                  description: "renters.chat.propertyClaims.warrantyDescription".localized(lang),
                                  buttonTitle: "Add",
                                  action: {
                self.showButtonsModal = true
                self.fileType = .warranty
            })
            
            if viewModel.warrantyFile != nil {
                VideoFileComponent(type: .regular,
                                   label: "renters.chat.propertyClaims.warrantyImage".localized(lang),
                                   icon: Image.videoPlayIcon,
                                   removeVideoAction: {
                    viewModel.warrantyFile = nil
                })
            }
        }
    }
}

// MARK: - View builders
extension AddingItemView {
    
    @ViewBuilder
    private func addPhotoModalButtons() -> some View {
        ActionCardListModalView(cardButtons: [
            CardButton(
                content: AnyView(ActionCardButton(
                    title: "renters.chat.propertyClaims.takePicture".localized(lang),
                    icon: Image.cameraIcon,
                    style: .secondary,
                    didTapAction: {
                        self.showCamera = true
                    }))),
            CardButton(
                content: AnyView(ActionCardButton(
                    title: "renters.chat.propertyClaims.selectPicture".localized(lang),
                    icon: Image.fileIcon,
                    style: .secondary,
                    didTapAction: {
                        self.showGallery = true
                    })))
        ], corners: [.topLeft, .topRight],
                                closeAction: {
            self.showButtonsModal = false
        })
    }
}

#Preview {
    AddingItemView(viewModel: AddingItemViewModel(),
                   closeAction: {},
                   doneAction: {})
}
