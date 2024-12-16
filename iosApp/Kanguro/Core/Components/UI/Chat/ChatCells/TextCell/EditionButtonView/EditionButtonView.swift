import UIKit

enum TextCellType: String, Codable {
    
    case Standard
    case Editable
    case Excludable
}

class EditionButtonView: BaseView, NibOwnerLoadable {
    
    // MARK: - IB Outlets
    @IBOutlet private var backgroundView: UIView!
    @IBOutlet private var editionImageView: UIImageView!
    @IBOutlet private var button: UIButton!
    
    // MARK: - Stored Properties
    var didTapEditionButtonAction: SimpleClosure = {}
    
    // MARK: - Initializers
    required init?(coder: NSCoder) {
        super.init(coder: coder)
        self.loadNibContent()
    }
}

// MARK: - Setup
extension EditionButtonView {
    
    func setup(type: TextCellType) {
        isHidden = (type == .Standard)
        backgroundView.setAsCircle()
        editionImageView.image = (type == .Editable ? UIImage(named: "ic-purple-edit") : UIImage(named: "ic-purple-close"))
    }
    
    func update(action: @escaping SimpleClosure) {
        self.didTapEditionButtonAction = action
    }
}

// MARK: - IB Actions
extension EditionButtonView {
    
    @IBAction private func editMessageTouchUpInside(_ sender: UIButton) {
        didTapEditionButtonAction()
    }
}
