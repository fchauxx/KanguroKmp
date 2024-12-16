import UIKit

class TitleNavigationBarView: BaseView, NibOwnerLoadable {

    // MARK: - IB Outlets
    @IBOutlet private var backButton: CustomButton!
    @IBOutlet private var closeButton: CustomButton!
    @IBOutlet private var titleLabel: CustomLabel!

    // MARK: - Stored Properties
    private var title: String?

    // MARK: - Actions
    var didTapBackButtonAction: SimpleClosure?
    var didTapCloseButtonAction: SimpleClosure?

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

// MARK: - Setup
extension TitleNavigationBarView {

    private func setup() {
        setupButtons()
        guard let title else { return }
        titleLabel.set(text: title,
                       style: TextStyle(color: .secondaryDarkest, weight: .bold, size: .h24, font: .lato))
    }
    
    private func setupButtons() {
        backButton.isHidden = (didTapBackButtonAction == nil)
        closeButton.isHidden = (didTapCloseButtonAction == nil)
        
        backButton.onTap {
            self.didTapBackButtonAction?()
        }
        closeButton.onTap {
            self.didTapCloseButtonAction?()
        }
    }
}

// MARK: - Public Methods
extension TitleNavigationBarView {

    func setup(title: String,
               didTapBackButtonAction: SimpleClosure? = nil,
               didTapCloseButtonAction: SimpleClosure? = nil) {
        self.title = title
        self.didTapBackButtonAction = didTapBackButtonAction
        self.didTapCloseButtonAction = didTapCloseButtonAction
        setup()
    }
}
