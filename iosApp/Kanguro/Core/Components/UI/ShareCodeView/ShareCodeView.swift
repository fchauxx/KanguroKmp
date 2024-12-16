import UIKit

class ShareCodeView: BaseView, NibOwnerLoadable {

    // MARK: - IB Outlets
    @IBOutlet private var titleLabel: CustomLabel!
    @IBOutlet private var codeLabel: CustomLabel!
    
    // MARK: - Stored Properties
    var code: String?

    // MARK: - Actions
    var didTapCodeAction: SimpleClosure = {}
    
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
extension ShareCodeView {
    
    private func setup() {
        guard let code = code else { return }
        titleLabel.set(text: "referFriends.shareCode.share".localized,
                       style: TextStyle(color: .secondaryDark, weight: .bold))
        codeLabel.set(text: code.uppercased(),
                      style: TextStyle(color: .tertiaryExtraDark, weight: .bold, size: .p21, font: .raleway, alignment: .center))
        codeLabel.setupToFitWidth()
    }
}

// MARK: - Public Methods
extension ShareCodeView {

    func setup(code: String) {
        self.code = code
        setup()
    }
    
    func update(action: @escaping SimpleClosure) {
        self.didTapCodeAction = action
    }
}

// MARK: - IB Actions
extension ShareCodeView {

    @IBAction private func shareCodeTouchUpInside(_ sender: UIButton) {
        didTapCodeAction()
    }
}
