import UIKit
import KanguroSharedDomain
import Kingfisher

class SecondaryAttachmentCardView: BaseView, NibOwnerLoadable {

    @IBOutlet private var previewImageView: UIImageView!
    @IBOutlet private var attachmentLabel: CustomLabel!
    @IBOutlet private var closeButton: CustomButton!

    // MARK: - Stored Properties
    var tempFileName: String?
    var tempFile: TemporaryFile?

    // MARK: - Actions
    var didTapCloseAction: TemporaryFileClosure = { _ in }

    // MARK: - Initializers
    required init?(coder: NSCoder) {
        super.init(coder: coder)
        self.loadNibContent()
    }
    override init(frame: CGRect) {
        super.init(frame: frame)
        self.loadNibContent()
    }
}

// MARK: - Private Methods
extension SecondaryAttachmentCardView {

    func setup(tempFileName: String, data: TemporaryFile) {
        self.tempFileName = tempFileName
        tempFile = data
        setupLabels()
        setupImage()
        setupConstraints()
        setupButtons()
    }

    private func setupLabels() {
        guard let tempFileName else { return }
        attachmentLabel.set(text: tempFileName, style: TextStyle(color: .neutralMedium, size: .p12))
    }

    private func setupImage() {
        guard let tempFileURL = tempFile?.url,
              let tempFileName,
              let url = URL(string: tempFileURL) else { return }
        previewImageView.tintColor = .secondaryMedium
        let image = KF.ImageResource(downloadURL: url, cacheKey: tempFileURL)
        previewImageView.kf.setImage(with: image, placeholder: UIImage(named: tempFileName.getFileExtension == FileExtension.pdf.fileType ? "ic-document-2" : "attachment"))
    }

    private func setupConstraints() {
        self.translatesAutoresizingMaskIntoConstraints = false
        self.heightAnchor.constraint(equalToConstant: 46).isActive = true
    }

    private func setupButtons() {
        closeButton.onTap { [weak self] in
            guard let self,
                  let tempFile else { return }
            self.didTapCloseAction(tempFile)
        }
    }
}

