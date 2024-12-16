import SwiftUI
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
        imagePicker.delegate = context.coordinator // confirming the delegate
        return imagePicker
    }

    public func updateUIViewController(_ uiViewController: UIImagePickerController, context: Context) {}

    // Connecting the Coordinator class with this struct
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
