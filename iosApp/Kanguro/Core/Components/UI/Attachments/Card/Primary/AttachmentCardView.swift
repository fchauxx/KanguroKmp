import UIKit
import KanguroSharedDomain

class AttachmentCardView: BaseView, NibOwnerLoadable {
    
    // MARK: - IB Outlets
    @IBOutlet private var titleLabel: CustomLabel!
    @IBOutlet private var sizeLabel: CustomLabel!
    
    // MARK: - Stored Properties
    var attachment: KanguroSharedDomain.Attachment?
    
    // MARK: - Actions
    var didTapCardAction: AttachmentClosure = { _ in }
    
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
extension AttachmentCardView {
    
    func setup(data: KanguroSharedDomain.Attachment) {
        attachment = data
        titleLabel.set(text: data.fileName,
                       style: TextStyle(color: .secondaryDarkest, size: .p16))
        sizeLabel.set(text: data.fileSize.getBytesToKBytesFormatted,
                      style: TextStyle(color: .neutralMedium))
    }
}

// MARK: - IB Actions
extension AttachmentCardView {
    
    @IBAction private func cardButtonTouchUpInside(_ sender: UIButton) {
        guard let attachment else { return }
        didTapCardAction(attachment)
    }
}
