import UIKit
import UniformTypeIdentifiers
import KanguroSharedDomain

// MARK: - Retrive file
extension ShareWithVetViewController {

    func showAttachmentOptionsView(_ fileUrl: String) {
        guard let document = URL(string: fileUrl) else { return }
        DispatchQueue.main.async() { [weak self] in
            guard let self else { return }
            let activityViewController = UIActivityViewController(activityItems: [document], applicationActivities: nil)
            self.present(activityViewController, animated: true, completion: nil)
        }
    }
}


// MARK: - Picker selections
extension ShareWithVetViewController {

    @objc func takePicture() {
        imagePicker.sourceType = .camera
        self.present(imagePicker, animated: true)
    }

    @objc func selectFile() {
        let importMenu = UIDocumentPickerViewController(forOpeningContentTypes: [UTType.pdf], 
                                                        asCopy: true)
        importMenu.delegate = self
        importMenu.modalPresentationStyle = .formSheet
        self.present(importMenu, animated: true, completion: nil)
    }
}

// MARK: - UIImagePickerControllerDelegate
extension ShareWithVetViewController: UIImagePickerControllerDelegate, UINavigationControllerDelegate {

    func imagePickerController(_ picker: UIImagePickerController, didFinishPickingMediaWithInfo info: [UIImagePickerController.InfoKey : Any]) {
        
        let filename = viewModel.getImageName(info: info)
        if let image = info[.originalImage] as? UIImage {
            viewModel.manageAttachments(images: [image], filename: filename)
        } else if let image = info[.editedImage] as? UIImage {
            viewModel.manageAttachments(images: [image], filename: filename)
        }
        picker.dismiss(animated: true, completion: .none)
    }
}

// MARK: - UIDocumentPickerViewControllerDelegate
extension ShareWithVetViewController: UIDocumentPickerDelegate {

    func documentPicker(_ controller: UIDocumentPickerViewController, didPickDocumentsAt urls: [URL]) {
        guard let myURL = urls.first else { return }
        viewModel.manageFile(file: myURL)
    }

    func documentMenu(_ documentMenu: UIDocumentPickerViewController, didPickDocumentPicker documentPicker: UIDocumentPickerViewController) {
        documentPicker.delegate = self
        documentMenu.present(documentPicker, animated: true, completion: nil)
    }

    func documentPickerWasCancelled(_ controller: UIDocumentPickerViewController) {
        controller.dismiss(animated: true, completion: nil)
    }
}

extension ShareWithVetViewController {

    func addPreviewFile(file: TemporaryFile) {
        hideUploadDocumentSelection()
        setDoneButtonStatus()
        if uploadPreviewStackView.attachments.count >= 1 {
            uploadPreviewStackView.clearAttachmentList()
            self.viewModel.uploadedFilesIds = []
        }
        uploadPreviewStackView.addSingleAttachment(data: TemporaryFile(filename: file.filename,
                                                                       url: file.url,
                                                                       id: file.id))
        uploadPreviewStackView.didTapCardAction = { [weak self] attachment in
            guard let self else { return }
            uploadPreviewStackView.removeAttachment(attachment)
            let updatedList = self.viewModel.uploadedFilesIds.filter{ $0 != attachment.id }
            self.viewModel.uploadedFilesIds = updatedList
            self.setDoneButtonStatus()
        }
    }
}
