import UIKit

class StackButtonsView: BaseView, NibOwnerLoadable {
    
    // MARK: - IB Outlets
    @IBOutlet var stackView: UIStackView!
    @IBOutlet private var scrollViewHeightConstraint: NSLayoutConstraint!
    
    // MARK: - Computed Properties
    var stackViewCount: Int {
        return stackView.subviews.count
    }
    
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
extension StackButtonsView {
    
    func addStackButton(stackButtonData: StackButtonData, nextStepData: NextStepParameters? = nil) {
        let stackButtonItem = StackButtonItem()
        stackButtonItem.update(data: stackButtonData, nextStepData: nextStepData)
        stackView.addArrangedSubview(stackButtonItem)
        stackButtonItem.index = stackViewCount
        stackView.layoutIfNeeded()
        scrollViewHeightConstraint.constant = stackView.frame.height
    }
    
    func clean() {
        stackView.removellArrangedSubviews()
    }
}
