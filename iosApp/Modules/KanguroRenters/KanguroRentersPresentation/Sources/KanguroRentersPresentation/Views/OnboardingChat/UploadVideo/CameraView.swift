import SwiftUI
import AVKit
import MobileCoreServices
import UIKit

public struct ImagePickerView: UIViewControllerRepresentable {
    
    @Environment(\.dismiss) var dismiss
    public var sourceType: UIImagePickerController.SourceType
    public var dismissAction: (UIImage) -> Void
    
    public init(sourceType: UIImagePickerController.SourceType,
                dismissAction: @escaping (UIImage) -> Void) {
        self.sourceType = sourceType
        self.dismissAction = dismissAction
    }
    public func makeUIViewController(context: Context) -> UIImagePickerController {
        let imagePicker = UIImagePickerController()
        imagePicker.sourceType = self.sourceType
        imagePicker.delegate = context.coordinator
        return imagePicker
    }
    
    public func updateUIViewController(_ uiViewController: UIImagePickerController, context: Context) {}
    
    public func makeCoordinator() -> CameraCoordinator {
        return CameraCoordinator(picker: self)
    }
}

public class CameraCoordinator: NSObject, UINavigationControllerDelegate, UIImagePickerControllerDelegate {
    
    public var picker: ImagePickerView
    
    public init(picker: ImagePickerView) {
        self.picker = picker
    }
    
    public func imagePickerController(_ picker: UIImagePickerController, didFinishPickingMediaWithInfo info: [UIImagePickerController.InfoKey : Any]) {
        guard let selectedImage = info[.originalImage] as? UIImage else { return }
        self.picker.dismiss()
        self.picker.dismissAction(selectedImage)
    }
}

struct CameraView: UIViewControllerRepresentable {
    @ObservedObject var viewModel: UploadFileViewModel
    @Binding var showButtonsModal: Bool
    @Binding var isCameraPresented: Bool
    
    func makeUIViewController(context: Context) -> UIViewController {
        let controller = UIImagePickerController()
        controller.sourceType = .camera
        controller.mediaTypes = [UTType.movie.identifier]
        controller.delegate = context.coordinator
        return controller
    }
    
    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
    
    func makeCoordinator() -> Coordinator {
        return Coordinator(viewModel: viewModel, isCameraPresented: $isCameraPresented, showButtonsModal: $showButtonsModal)
    }
    
    class Coordinator: NSObject, UINavigationControllerDelegate, UIImagePickerControllerDelegate {
        var viewModel: UploadFileViewModel
        var isCameraPresented: Binding<Bool>
        var showButtonsModal: Binding<Bool>
        
        init(viewModel: UploadFileViewModel, isCameraPresented: Binding<Bool>, showButtonsModal: Binding<Bool>) {
            self.viewModel = viewModel
            self.isCameraPresented = isCameraPresented
            self.showButtonsModal = showButtonsModal
        }
        
        func imagePickerController(_ picker: UIImagePickerController, didFinishPickingMediaWithInfo info: [UIImagePickerController.InfoKey: Any]) {
            if let mediaType = info[.mediaType] as? String,
               mediaType == UTType.movie.identifier,
               let url = info[.mediaURL] as? URL {
                
                let fileName = url.lastPathComponent
                
                let recordedMovie = Movie(url: url, fileName: fileName)
                viewModel.recordedVideo = recordedMovie
                showButtonsModal.wrappedValue = false
            }
            
            isCameraPresented.wrappedValue = false
        }
        
        func imagePickerControllerDidCancel(_ picker: UIImagePickerController) {
            isCameraPresented.wrappedValue = false
        }
    }
}
