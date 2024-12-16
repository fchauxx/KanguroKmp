import Foundation
import UIKit

extension CoverageDetailsViewController: UIImagePickerControllerDelegate, UINavigationControllerDelegate {

    func imagePickerController(_ picker: UIImagePickerController, didFinishPickingMediaWithInfo info: [UIImagePickerController.InfoKey : Any]) {
        if let image = info[.originalImage] as? UIImage {
            viewModel.updatePetPicture(image: image)
        } else if let image = info[.editedImage] as? UIImage {
            viewModel.updatePetPicture(image: image)
        }
        picker.dismiss(animated: true, completion: .none)
        if picker.sourceType == .camera {
            self.setEditClosePetPictureMenuConstraints()
        }
        self.hideEditClosePetPictureMenu()
    }

    func imagePickerControllerDidCancel(_ picker: UIImagePickerController) {
        picker.dismiss(animated: true, completion: .none)
        if picker.sourceType == .camera {
            self.setEditClosePetPictureMenuConstraints()
        }
    }
}
