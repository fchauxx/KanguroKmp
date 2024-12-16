import UIKit
import KanguroSharedDomain

class AttachmentsListView: BaseView, NibOwnerLoadable {
    
    // MARK: - IB Outlets
    @IBOutlet private var attachmentsTitle: CustomLabel!
    @IBOutlet private var stackView: UIStackView!
    
    // MARK: - Computed Properties
    var attachmentsQty: Int {
        return stackView.subviews.count
    }
    
    // MARK: Actions
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
extension AttachmentsListView {
    
    private func setupLabels() {
        attachmentsTitle.set(text: "claimDetails.attachments.label".localized.uppercased() + "(\(attachmentsQty))",
                             style: TextStyle(weight: .bold, size: .p12))
        attachmentsTitle.isHidden = false
    }
}

// MARK: - Public Methods
extension AttachmentsListView {
    
    func setup(attachments: [KanguroSharedDomain.Attachment]) {
        for data in attachments {
            let attachmentCardView = AttachmentCardView()
            attachmentCardView.setup(data: data)
            attachmentCardView.didTapCardAction = { [weak self] attachment in
                guard let self else { return }
                self.didTapCardAction(attachment)
            }
            stackView.addArrangedSubview(attachmentCardView)
        }
        layoutIfNeeded()
        setupLabels()
    }
    
    func clearAttachmentList() {
        stackView.removellArrangedSubviews()
    }
}
