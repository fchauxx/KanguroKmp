import UIKit
import KanguroSharedDomain

class InformerAccordionView: BaseView, NibOwnerLoadable {
    
    // MARK: - IB Outlets
    @IBOutlet private var titleLabel: CustomLabel!
    @IBOutlet private var descriptionLabel: CustomLabel!
    @IBOutlet private var stackView: UIStackView!
    @IBOutlet private var arrowImageView: UIImageView!
    @IBOutlet private var descriptionView: UIView!
    
    // MARK: - Stored Properties
    var data: InformerData?
    var isExpanded: Bool = false
    
    // MARK: - Actions
    var didTapExpansionAction: SimpleClosure = {}
    
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
extension InformerAccordionView {
    
    private func setupLayout() {
        setupStackView()
        setupLabels()
        setupImages()
    }
    
    private func setupLabels() {
        guard let data = data,
              let value = data.value,
              let description = data.description else { return }
        titleLabel.set(text: value, style: TextStyle(color: .secondaryDarkest, weight: .bold, size: .p16))
        descriptionLabel.set(text: description, style: TextStyle(color: .secondaryDarkest, size: .p16))
    }
    
    private func setupImages() {
        arrowImageView.image = (isExpanded ? UIImage(named: "ic-up-arrow") : UIImage(named: "ic-down-arrow"))
    }
    
    private func setupStackView() {
        stackView.borderColor = .primaryDarkest
        descriptionView.isHidden = !isExpanded
    }
    
    private func setColoredBorderIsHidden(_ isHidden: Bool) {
        isHidden ? (stackView.borderWidth = 0) : (stackView.borderWidth = 2)
    }
    
    private func animateView() {
        UIView.animate(withDuration: 0.3) { [weak self] in
            guard let self = self else { return }
            self.updateViews()
        }
        stackView.superview?.layoutIfNeeded()
    }
    
    private func changeItemsStackStatus() {
        isExpanded.toggle()
        animateView()
    }
    
    private func updateViews() {
        setupImages()
        setupStackView()
        setColoredBorderIsHidden(!isExpanded)
    }
}

// MARK: - Public Methods
extension InformerAccordionView {
    
    func setupInformerData(_ data: InformerData) {
        self.data = data
        setupLayout()
    }
    
    func close() {
        isExpanded = false
        animateView()
    }
}

// MARK: - IB Actions
extension InformerAccordionView {
    
    @IBAction private func arrowButtonTouchUpInside(_ sender: UIButton) {
        changeItemsStackStatus()
        didTapExpansionAction()
    }
}
