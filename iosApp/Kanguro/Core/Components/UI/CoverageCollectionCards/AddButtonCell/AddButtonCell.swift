import UIKit

class AddButtonCell: UICollectionViewCell {
    
    // MARK: - Actions
    private var didTapButtonAction: SimpleClosure = {}
}

// MARK: - Public Methods
extension AddButtonCell {
    
    func setup(didTapAddButtonAction: @escaping SimpleClosure) {
        self.didTapButtonAction = didTapAddButtonAction
    }
}

// MARK: - IB Actions
extension AddButtonCell {
    @IBAction func addTouchUpInside(_ sender: UIButton) {
        didTapButtonAction()
    }
}
