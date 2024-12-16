import UIKit

class DataFilterTableViewCell: UITableViewCell {

    // MARK: - IB Outlets
    @IBOutlet private var titleLabel: CustomLabel!
    @IBOutlet private var plusImage: UIImageView!
    
    // MARK: - Stored Properties
    private var title = ""
    private var isCustomOption = false
}

// MARK: - Life Cycle
extension DataFilterTableViewCell {
    
    override func prepareForReuse() {
        title = ""
    }
}

// MARK: - Setup
extension DataFilterTableViewCell {
    
    private func setupLayout() {
        setupImage()
        setupLabel()
    }
    
    private func setupImage() {
        plusImage.isHidden = !isCustomOption
        plusImage.tintColor = isCustomOption ? .tertiaryExtraDark : .secondaryDarkest
    }
    
    private func setupLabel() {
        titleLabel.set(text: title, style: TextStyle(color: isCustomOption ? .tertiaryExtraDark : .secondaryDarkest))
    }
}

// MARK: - Public Methods
extension DataFilterTableViewCell {
    
    func setup(title: String, isCustomOption: Bool = false) {
        self.title = title
        self.isCustomOption = isCustomOption
        setupLayout()
    }
}
