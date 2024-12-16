//
//  ImageViewController.swift
//  Kanguro
//
//  Created by Rodrigo Okido on 09/06/23.
//

import UIKit
import PDFKit

class FilePreviewViewController: BaseViewController {
    
    // MARK: - IBOutlets
    @IBOutlet private var imageView: UIImageView!
    @IBOutlet private var pdfView: PDFView!
    
    // MARK: - Dependencies
    var viewModel: FilePreviewViewModel!
}

// MARK: - Life Cycle
extension FilePreviewViewController {
    
    override func viewDidLoad() {
        setupPreview()
    }
}

// MARK: - Setup
extension FilePreviewViewController {
    
    private func setupPreview() {
        guard let fileType = viewModel.getFileMimeType(),
              let data = viewModel.data else { return }
    
        if fileType == .pdf {
            let document = PDFDocument(data: data)
            pdfView.document = document
            pdfView.autoScales = true
            pdfView.isHidden = false
        } else {
            imageView.image = UIImage(data: data)
            imageView.isHidden = false
        }
    }
}

// MARK: - Private Methods
extension FilePreviewViewController {
    
    private func showAttachmentOptionsView(_ attachment: Data) {
        DispatchQueue.main.async() { [weak self] in
            guard let self else { return }
            let activityViewController = UIActivityViewController(activityItems: [attachment], applicationActivities: nil)
            self.present(activityViewController, animated: true, completion: nil)
        }
    }
}

// MARK: - IB Actions
extension FilePreviewViewController {
    
    @IBAction private func downloadTouchUpInside(_ sender: UIButton) {
        guard let data = viewModel.data else { return }
        showAttachmentOptionsView(data)
    }
}
