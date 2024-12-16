import UIKit

class StatusSelectionButtonView: BaseView, NibOwnerLoadable {
    
    // MARK: - IB Outlets
    @IBOutlet private var contentView: UIView!
    @IBOutlet private var titleLabel: CustomLabel!
    @IBOutlet private var circleView: UIView!
    
    // MARK: - Stored Properties
    var status: Status = .none
    private var isSelected = false
    
    // MARK: - Actions
    var didTapButtonAction: StatusClosure = { _ in }
    
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
extension StatusSelectionButtonView {
    
    private func setupLayout() {
        setupLabels()
        setupViews()
    }
    
    private func setupLabels() {
        titleLabel.set(text: status.title,
                       style: TextStyle(color: .secondaryDarkest, size: .p12))
        titleLabel.sizeToFit()
    }
    
    private func setupViews() {
        contentView.borderColor = .secondaryLightest
        contentView.borderWidth = isSelected ? 2 : 0
        circleView.backgroundColor = status.color
        circleView.isHidden = (status == .none)
    }
}

// MARK: - Public Methods
extension StatusSelectionButtonView {
    
    func setup(status: Status) {
        self.status = status
        setupLayout()
    }
    
    func update(isSelected: Bool) {
        self.isSelected = isSelected
        setupViews()
    }
}

// MARK: - IB Actions
extension StatusSelectionButtonView {
    
    @IBAction private func buttonTouchUpInside(_ sender: UIButton) {
        didTapButtonAction(status)
    }
}
