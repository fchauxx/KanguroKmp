import UIKit
import KanguroSharedDomain

class SecondaryAttachmentListView: BaseView, NibOwnerLoadable {

    // MARK: - IBOutlets
    @IBOutlet private var stackView: UIStackView!

    // MARK: - Stored Properties
    var attachments: [SecondaryAttachmentCardView]

    // MARK: - Computed Properties
    var attachmentsQty: Int {
        return stackView.subviews.count
    }

    // MARK: Actions
    var didTapCardAction: TemporaryFileClosure = { _ in }

    // MARK: - Initializers
    required init?(coder: NSCoder) {
        attachments = []
        super.init(coder: coder)
        self.loadNibContent()
    }

    override init(frame: CGRect) {
        attachments = []
        super.init(frame: frame)
        self.loadNibContent()
    }
}

// MARK: - Public Methods
extension SecondaryAttachmentListView {

    func setup(temporaryFile: [TemporaryFile]) {
        for data in temporaryFile {
            addSingleAttachment(data: data)
        }
        layoutIfNeeded()
    }

    func addSingleAttachment(data: TemporaryFile) {
        let filename = data.filename ?? "uploaded_document_\(attachments.count)"
        let attachmentCardView = SecondaryAttachmentCardView()
        attachmentCardView.setup(tempFileName: filename, data: data)
        attachmentCardView.didTapCloseAction = { [weak self] attachment in
            guard let self else { return }
            self.didTapCardAction(attachment)
        }
        attachments.append(attachmentCardView)
        stackView.addArrangedSubview(attachmentCardView)
        layoutIfNeeded()
    }

    func removeAttachment(_ dataToRemove: TemporaryFile) {
        guard let removeId = dataToRemove.id else { return }
        for data in attachments {
            if let dataId = data.tempFile?.id {
                if dataId == removeId {
                    stackView.removeFully(view: data)
                }
            }
        }
        layoutIfNeeded()
    }

    func clearAttachmentList() {
        stackView.removellArrangedSubviews()
    }
}
