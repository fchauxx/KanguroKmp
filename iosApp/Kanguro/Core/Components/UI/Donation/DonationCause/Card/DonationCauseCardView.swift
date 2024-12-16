import UIKit
import KanguroSharedDomain

class DonationCauseCardView: BaseView, NibOwnerLoadable {
    
    // MARK: - IB Outlets
    @IBOutlet private var stackView: UIStackView!
    @IBOutlet private var buttonViewTopTitleLabel: CustomLabel!
    @IBOutlet private var buttonViewBottomTitleLabel: CustomLabel!
    @IBOutlet private var arrowImageView: UIImageView!
    @IBOutlet private var bottomView: UIView!
    @IBOutlet private var descriptionLabel: CustomLabel!
    @IBOutlet private var chooseButton: CustomButton!
    
    // MARK: - Stored Properties
    var data: DonationCause?
    var isToChangeCause: Bool = false
    var isExpanded: Bool = false
    
    // MARK: - Actions
    var didTapExpansionAction: SimpleClosure = {}
    var didTapChooseButtonAction: DonationCauseSelectedTypeClosure = { _ in }
    var didTapChooseToChangeCause: SimpleClosure = {}
    
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
extension DonationCauseCardView {
    
    private func setupLayout() {
        setupLabels()
        setupViews()
        setupImages()
        setupButtons()
    }
    
    private func setupLabels() {
        guard let data = data else { return }
        buttonViewTopTitleLabel.set(text: data.attributes.abreviatedTitle,
                                    style: TextStyle(color: .secondaryDarkest, weight: .bold, size: .p16))
        buttonViewBottomTitleLabel.set(text: data.attributes.title,
                                       style: TextStyle(color: .neutralDark))
        descriptionLabel.set(text: data.attributes.description,
                             style: TextStyle(color: .secondaryDarkest, size: .p16))
    }
    
    private func setupViews() {
        bottomView.isHidden = !isExpanded
        stackView.isHidden = false
    }
    
    private func setupImages() {
        arrowImageView.image = (isExpanded ? UIImage(named: "ic-up-arrow") : UIImage(named: "ic-down-arrow"))
    }
    
    private func setupButtons() {
        chooseButton.set(title: isToChangeCause ? "supportCause.change.button".localized : "donation.choose.button".localized,
                         style: isToChangeCause ? .quaternary : .primary)
        chooseButton.onTap { [weak self] in
            guard let self,
                  let data = self.data else { return }
            
            if self.isToChangeCause {
                self.didTapChooseToChangeCause()
            } else {
                let selectedCause = DonationCauseSelected(charityId: data.attributes.charityKey, title: data.attributes.title, cause: data.attributes.cause)
                self.didTapChooseButtonAction(selectedCause)
            }
        }
        guard let image = UIImage(named: isToChangeCause ? "ic-heart-search-fill" : "ic-heart-search") else { return }
        chooseButton.setImage(image, for: .normal)
    }
}

// MARK: - Private Methods
extension DonationCauseCardView {
    
    private func animateView() {
        UIView.animate(withDuration: 0.3) { [weak self] in
            guard let self = self else { return }
            self.setupImages()
            self.setupViews()
            self.setColoredBorderHidden(!self.isExpanded)
            self.stackView.superview?.layoutIfNeeded()
        }
    }
    
    private func changeItemsStackStatus() {
        isExpanded.toggle()
        animateView()
    }
    
    private func setColoredBorderHidden(_ isHidden: Bool) {
        self.cornerRadius = 8
        self.borderColor = .primaryMedium
        isHidden ? (borderWidth = 0) : (borderWidth = 2)
    }
}

// MARK: - Public Methods
extension DonationCauseCardView {
    
    func setup(data: DonationCause, isToChangeCause: Bool = false) {
        self.data = data
        self.isToChangeCause = isToChangeCause
        setupLayout()
    }
    
    func close() {
        isExpanded = false
        animateView()
    }
}

// MARK: - IB Actions
extension DonationCauseCardView {
    
    @IBAction private func arrowViewButton(sender: UIButton) {
        changeItemsStackStatus()
        didTapExpansionAction()
    }
}
