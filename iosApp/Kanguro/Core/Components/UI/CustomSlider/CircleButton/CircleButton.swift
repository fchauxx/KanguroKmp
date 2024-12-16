import UIKit

class CircleButton: UIView, NibOwnerLoadable {
    
    // MARK: - IB Outlets
    @IBOutlet private var button: UIButton!
    
    // MARK: - Actions
    var didTapButtonAction: IntClosure = { _ in }
    
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
extension CircleButton {
    
    func update(action: @escaping IntClosure) {
        self.didTapButtonAction = action
    }
    
    func update(tag: Int) {
        self.button.tag = tag
        self.setAsCircle()
    }
}

// MARK: - IB Actions
extension CircleButton {
    
    @IBAction private func buttonTouchUpInside(_ sender: UIButton) {
        didTapButtonAction(sender.tag)
    }
}
