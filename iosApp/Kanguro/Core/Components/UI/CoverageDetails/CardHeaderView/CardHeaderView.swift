import UIKit

class CardHeaderView: BaseView, NibOwnerLoadable {
    
    // MARK: - IB Outlets
    @IBOutlet private var leadingIconImageView: UIImageView!
    @IBOutlet private var leadingTitleLabel: CustomLabel!
    @IBOutlet private var traillingTitleLabel: CustomLabel!
    @IBOutlet private var leadingSubtitleLabel: CustomLabel!
    @IBOutlet private var traillingSubtitleLabel: CustomLabel!
    
    
    // MARK: - Stored Properties
    var leadingIcon: UIImage?
    var leadingTitle: String?
    var leadingSubtitle: String?
    var policyPeriod: String?
    var expirationDate: String?
    
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

// MARK: - Life Cycle
extension CardHeaderView {
    
    override func layoutSubviews() {
        super.layoutSubviews()
        setupLayout()
    }
}

// MARK: - Setup
extension CardHeaderView {
    
    private func setupLayout() {
        setupLabels()
        setupImages()
    }
    
    private func setupLabels() {
        guard let leadingTitle = leadingTitle else { return }
        leadingTitleLabel.set(text: leadingTitle, style: TextStyle(color: .secondaryDarkest, weight: .bold, size: .p16))
        if let policyPeriod {
            traillingTitleLabel.isHidden = false
            traillingTitleLabel.set(text: policyPeriod, style: TextStyle(color: .neutralMedium, size: .p11))
        }
        if let expirationDate {
            traillingSubtitleLabel.isHidden = false
            traillingSubtitleLabel.set(text: expirationDate, style: TextStyle(color: .neutralMedium, size: .p11))
        }
        if let leadingSubtitle {
            leadingSubtitleLabel.set(text: leadingSubtitle.uppercased(), style: TextStyle(color: .secondaryMedium, weight: .bold, size: .p10))
            leadingSubtitleLabel.isHidden = false
        }
    }
    
    private func setupImages() {
        guard let leadingIcon = leadingIcon else { return }
        leadingIconImageView.image = leadingIcon
    }
}

// MARK: - Public Methods
extension CardHeaderView {
    
    func update(leadingTitle: String, leadingSubtitle: String? = nil) {
        self.leadingTitle = leadingTitle
        self.leadingSubtitle = leadingSubtitle
        setupLabels()
    }
    
    func update(leadingIcon: UIImage) {
        self.leadingIcon = leadingIcon
        setupImages()
    }
    
    func update(policyPeriod: String) {
        self.policyPeriod = policyPeriod
        setupLabels()
    }
    
    func update(expirationDate: String) {
        self.expirationDate = expirationDate
        setupLabels()
    }
    
    func setTraillingTitleIsHidden(_ isHidden: Bool) {
        traillingTitleLabel.isHidden = isHidden
        setupLabels()
    }
}
