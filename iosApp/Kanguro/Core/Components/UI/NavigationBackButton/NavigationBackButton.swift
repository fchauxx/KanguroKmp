import UIKit

class NavigationBackButton: BaseView, NibOwnerLoadable {
    
    // MARK: - IB Outlets
    @IBOutlet var titleLabel: CustomLabel!
    
    // MARK: - Stored Properties
    var title: String?
    
    // MARK: - Actions
    var didTapAction: SimpleClosure = {}
    
    // MARK: - Initializers
    required init?(coder: NSCoder) {
        super.init(coder: coder)
        self.loadNibContent()
    }
}

// MARK: - Setup
extension NavigationBackButton {
    
    func setupLayout() {
        guard let title = title else { return }
        titleLabel.set(text: title, style: TextStyle(color: .secondaryDark, weight: .bold, size: .p14))
    }
    
    func update(title: String) {
        self.title = title
        setupLayout()
    }
    
    func update(action: @escaping SimpleClosure) {
        self.didTapAction = action
    }
}

// MARK: - IB Outlets
extension NavigationBackButton {
    
    @IBAction func doAction(_ sender: UIButton) {
        didTapAction()
    }
}
