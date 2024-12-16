import UIKit
import KanguroSharedDomain
import KanguroPetDomain

enum WarningViewType {
    
    case policy(policy: PetPolicy)
    case claim(claim: PetClaim)
}

class WarningView: BaseView, NibOwnerLoadable {
    
    // MARK: - IB Outlets
    @IBOutlet private var backgroundView: UIView!
    @IBOutlet private var leadingTitleLabel: CustomLabel!
    @IBOutlet private var topicLabelView: TopicLabelView!
    @IBOutlet private var traillingLabel: CustomLabel!
    
    @IBOutlet private var iconImageView: UIImageView!
    @IBOutlet private var iconImageHeight: NSLayoutConstraint!
    @IBOutlet private var iconImageWidth: NSLayoutConstraint!
    
    // MARK: - Stored Properties
    var type: WarningViewType?
    var isOnlyLabel: Bool = false
    
    // MARK: - Actions
    var didTapButtonAction: SimpleClosure = {}
    
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
extension WarningView {
    
    private func setupLayout() {
        setupLabels()
        setupImages()
        setupViews()
    }
    
    private func setupLabels() {
        guard let type = type else { return }
        
        var leadingText = ""
        
        switch type {
        case .policy(let policy):
            leadingText = "coverageDetails.endDateTitle.label".localized
            traillingLabel.set(text: policy.waitingPeriodFormatted,
                               style: TextStyle(color: .secondaryDarkest, weight: .bold))
            traillingLabel.setupToFitWidth()
            traillingLabel.isHidden = false
        case .claim(let claim):
            leadingText = switch claim.status {
            case .Denied: "claimDetails.deniedStatus.label".localized
            case .PendingMedicalHistory: "claimDetails.pendigMedicalHistoryText.label".localized
            default:"claimDetails.communicationStatus.label".localized
            }

            if let decision = claim.decision {
                topicLabelView.setup(
                    data: TopicLabelViewData(
                        title: decision,
                        style: .colored(color: .tertiaryExtraDark)
                    )
                )
                topicLabelView.isHidden = false
            }
        }
        
        leadingTitleLabel.set(
            text: leadingText,
            style: TextStyle(color: .secondaryDarkest, size: .p12)
        )
    }
    
    private func setupImages() {
        guard let type = type else { return }
        
        var image: UIImage?
        var size: CGFloat
        
        switch type {
        case .policy:
            image = UIImage(named: "ic-warning")
            size = 20
        case .claim(let claim):
            image = (claim.status == .Denied) ? claim.status?.image : UIImage(named: "ic-warning")
            size = 24
        }
        
        iconImageView.image = image
        iconImageWidth.constant = size
        iconImageHeight.constant = size
    }
    
    private func setupViews() {
        guard let type = type else { return }
        
        var backgroundColor: UIColor?
        var borderColor: UIColor?
        
        switch type {
        case .policy:
            backgroundColor = .warningLight
            borderColor = .warningDark
        case .claim(let claim):
            backgroundColor = (claim.status == .Denied) ? claim.status?.secondaryColor : .warningLightest
            borderColor = (claim.status == .Denied) ? claim.status?.primaryColor : .warningDark
        }
        
        backgroundView.backgroundColor = backgroundColor
        backgroundView.borderColor = borderColor ?? .clear
    }
}

// MARK: - Public Methods
extension WarningView {
    
    func setup(type: WarningViewType) {
        self.type = type
        setupLayout()
    }
    
    func update(didTapButtonAction: @escaping SimpleClosure) {
        self.didTapButtonAction = didTapButtonAction
    }
}

// MARK: - IB Actions
extension WarningView {
    
    @IBAction private func buttonTouchUpInside(sender: UIButton) {
        didTapButtonAction()
    }
}
