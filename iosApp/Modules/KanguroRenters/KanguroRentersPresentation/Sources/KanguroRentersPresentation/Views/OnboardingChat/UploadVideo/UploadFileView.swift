import SwiftUI
import KanguroDesignSystem
import AVKit
import PhotosUI

public struct UploadFileView: View {
    
    // MARK: - Wrapped Properties
    @ObservedObject var viewModel: UploadFileViewModel
    @Environment(\.appLanguageValue) var language
    
    @State private var showButtonsModal = false
    @State private var showCamera = false
    @State private var showGallery = false
    @State private var showCloseActionPopUp = false
    
    // MARK: - Computed Properties
    private var lang: String {
        language.rawValue
    }
    
    // MARK: - Actions
    var closeAction: SimpleClosure
    var nextAction: SimpleClosure
    
    // MARK: - Initializers
    public init(viewModel: UploadFileViewModel,
                closeAction: @escaping SimpleClosure,
                nextAction: @escaping SimpleClosure) {
        self.viewModel = viewModel
        self.closeAction = closeAction
        self.nextAction = nextAction
    }
    
    public var body: some View {
        ZStack {
            VStack(spacing: InsetSpacing.xs) {
                PresenterNavigationHeaderView(closeAction: {
                    self.showCloseActionPopUp = true
                }).padding(.horizontal, InsetSpacing.xxs)
                
                makeContent()
                    .padding(.horizontal, InsetSpacing.md)
            }
            if showButtonsModal {
                addVideosModalButtons()
            }
            if showCloseActionPopUp {
                createCloseActionPopUp()
            }
        }
        .sheet(isPresented: $showCamera, content: {
            CameraView(viewModel: viewModel, 
                       showButtonsModal: $showButtonsModal,
                       isCameraPresented: $showCamera)
        })
        .photosPicker(isPresented: $showGallery, 
                      selection: $viewModel.selectedVideo,
                      matching: .videos)

        .onChange(of: viewModel.selectedVideo) { _ in
            hideButtonsAndResetUploadRequestStatus()
            Task {
                await pickVideo()
            }
        }
        .onChange(of: viewModel.recordedVideo) { file in
            guard let file = file else { return }
            hideButtonsAndResetUploadRequestStatus()
            viewModel.uploadFile(file)
            
        }
        .onChange(of: viewModel.uploadFileRequestStatus) { status in
            if status == .uploadSucceeded {
                nextAction()
            }
        }
    }
    
    @ViewBuilder
    private func makeContent() -> some View {
        VStack(spacing: InsetSpacing.md) {
            Text("onboarding.chatbot.uploadVideo.title".localized(lang))
                .title3SecondaryDarkestBold()
            
            Text("onboarding.chatbot.uploadVideo.description".localized(lang))
                .bodySecondaryDarkBold()
            
            VStack(spacing: InsetSpacing.xxs) {
                PrimaryButtonView("onboarding.chatbot.uploadVideo.button".localized(lang),
                                  height: HeightSize.md, 
                                  cornerRadius: CornerRadius.lg,
                                  isDisabled: viewModel.isLoading,
                                  isLoading: viewModel.isLoading, {
                    self.showButtonsModal = true
                })
                
                if let status = viewModel.uploadFileRequestStatus {
                    switch status {
                    case .sendingError, .uploadingError:
                        Text(status.message.localized(lang))
                            .font(.lato(.latoRegular, size: 10))
                            .foregroundStyle(Color.negativeDarkest)
                    case .uploading:
                        Text(status.message.localized(lang))
                            .font(.lato(.latoRegular, size: 10))
                            .foregroundStyle(Color.secondaryDarkest)
                    default: EmptyView()
                    }
                }
                
                if let video = viewModel.file, !viewModel.isLoading {
                    VideoFileComponent(
                        type: .regular,
                        label: video.fileName,
                        icon: Image.videoPlayIcon,
                        removeVideoAction: {
                            viewModel.file = nil
                            viewModel.selectedVideo = nil
                            viewModel.recordedVideo = nil
                            viewModel.uploadFileRequestStatus = nil
                        }
                    )
                }
            }
            
            Spacer()
            
            PrimaryButtonView(
                "common.done.label".localized(lang),
                height: HeightSize.lg,
                isDisabled: !viewModel.canProceed,
                {
                    viewModel.submitItems()
                }
            )
            .padding(.bottom, InsetSpacing.tabBar)
        }
    }
}

// MARK: - Private methods
extension UploadFileView {
    
    private func pickVideo() async {
        do {
            if let movie = try await viewModel.selectedVideo?.loadTransferable(type: Movie.self) {
                viewModel.uploadFile(movie)
            } else {
                viewModel.uploadFileRequestStatus = .sendingError
            }
        } catch {
            viewModel.uploadFileRequestStatus = .sendingError
        }
    }

    private func hideButtonsAndResetUploadRequestStatus() {
        showButtonsModal = false
        viewModel.uploadFileRequestStatus = nil
    }
}

// MARK: - View builders
extension UploadFileView {
    
    @ViewBuilder
    private func addVideosModalButtons() -> some View {
        ActionCardListModalView(
            cardButtons: [
                CardButton(
                    content: AnyView(
                        ActionCardButton(title: "onboarding.chatbot.video.acion.record".localized(lang),
                                         icon: Image.cameraIcon,
                                         style: .secondary,
                                         didTapAction: {self.showCamera = true}
                                        )
                    )
                ),
                CardButton(
                    content: AnyView(
                        ActionCardButton(title: "onboarding.chatbot.video.acion.select".localized(lang),
                                         icon: Image.fileIcon,
                                         style: .secondary,
                                         didTapAction: {self.showGallery = true}
                                        )
                    )
                )
            ],
            corners: [
                .topLeft,
                .topRight
            ],
            closeAction: { self.showButtonsModal = false}
        )
    }
    
    @ViewBuilder
    private func createCloseActionPopUp() -> some View {
        PopUpActionView(popUpImage: Image.warningImage,
                        popUpDescription: "onboarding.chatbot.video.popUp.description".localized(lang),
                        popUpSubdescription: "onboarding.chatbot.video.popUp.subdescription".localized(lang),
                        mainActionLabel: "onboarding.chatbot.video.popUp.close".localized(lang),
                        secondaryActionLabel: "common.cancel.label".localized(lang),
                        mainActionBackground: .secondaryDarkest) {
            self.showCloseActionPopUp = false
        } confirmAction: {
            viewModel.closeWithoutSave()
            closeAction()
        }
    }
}
