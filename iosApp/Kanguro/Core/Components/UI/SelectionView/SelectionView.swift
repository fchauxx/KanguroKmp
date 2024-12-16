import UIKit

class SelectionView: BaseView, NibOwnerLoadable {
    
    // MARK: - IB Outlets
    @IBOutlet private var imageView: UIImageView!
    @IBOutlet private var titleLabel: CustomLabel!
    @IBOutlet private var button: UIButton!
    
    // MARK: - Stored Properties
    var data: SelectionViewData?
    
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
extension SelectionView {
    
    private func setupLayout() {
        setupLabels()
        setupViews()
    }
    
    private func setupViews() {
        guard let isSelected = data?.isSelected else { return }
        imageView.isHidden = !isSelected
        button.isEnabled = !isSelected
    }
    
    private func setupLabels() {
        guard let title = data?.title else { return }
        titleLabel.set(text: title,
                       style: TextStyle(color: .secondaryDarkest, size: .p16))
    }
}

// MARK: - Public Methods
extension SelectionView {
    
    func setup(data: SelectionViewData) {
        self.data = data
        setupLayout()
    }
    
    func isSelected(_ isSelected: Bool) {
        self.data?.isSelected = isSelected
        setupViews()
    }
}

// MARK: - IB Actions
extension SelectionView {
    
    @IBAction private func selectTouchUpInside(_ sender: UIButton) {
        guard let action = data?.didTapButtonAction else { return }
        action(tag)
    }
}
