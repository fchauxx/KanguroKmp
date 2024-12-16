import UIKit

class DualButtonsView: BaseView, NibOwnerLoadable {
    
    // MARK: - IB Outlets
    @IBOutlet var stackView: UIStackView!
    
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

// MARK: - Public Methods
extension DualButtonsView {
    
    func addButton(data: ChatButtonItemData, nextStepData: NextStepParameters? = nil, action: SimpleClosure? = nil) {
        let chatButtonItem = ChatButtonItem()
        chatButtonItem.update(data: data, nextStepData: nextStepData)
        stackView.addArrangedSubview(chatButtonItem)
        stackView.layoutIfNeeded()
    }
    
    func clean() {
        stackView.removellArrangedSubviews()
    }
}
