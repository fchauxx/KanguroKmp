import UIKit
import KanguroSharedDomain
import KanguroPetDomain

enum ProgressBarType {
    
    case deductible
    case annualLimit
}

class CoverageDetailsProgressBar: BaseView, NibOwnerLoadable {
    
    // MARK: - IB Outlets
    @IBOutlet private var backgroundView: UIView!
    @IBOutlet private var progressBarView: UIView!
    @IBOutlet private var leftToDeductibleLabel: CustomLabel!
    @IBOutlet private var minimumDeductibleLabel: CustomLabel!
    @IBOutlet private var maximumDeductibleLabel: CustomLabel!
    @IBOutlet private var progressWidthConstraint: NSLayoutConstraint!
    
    // MARK: - Stored Properties
    var policy: PetPolicy?
    var type: ProgressBarType = .deductible
    var initialValue: CGFloat = 10
    
    // MARK: - Computed Properties
    var maxDeductableProgress: CGFloat {
        return backgroundView.frame.width
    }
    var deductableProgress: CGFloat? {
        guard let deductable = policy?.deductable else { return nil }
        return deductable.consumed / deductable.limit
    }
    var remainingDeductable: Int? {
        guard let deductable = policy?.deductable else { return nil }
        return Int(deductable.limit - deductable.consumed)
    }
    var maxAnnualLimitProgress: CGFloat {
        return backgroundView.frame.width
    }
    var annualLimitProgress: CGFloat? {
        guard let sumInsured = policy?.sumInsured else { return nil }
        return 1 - (sumInsured.remainingValue/sumInsured.limit)
    }
    
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
extension CoverageDetailsProgressBar {
    
    override func layoutSubviews() {
        super.layoutSubviews()
        setupLayout()
    }
}

// MARK: - Setup
extension CoverageDetailsProgressBar {
    
    private func setupLayout() {
        //Labels are disabled for now
        //setupLabels()
        setupViews()
    }
    
    private func setupLabels() {
        let style = TextStyle(color: .neutralLight, weight: .bold)
        
        guard let totalDeductable = policy?.deductable?.limit,
              let remainingDeductable = remainingDeductable,
              let totalAnnualLimit = policy?.sumInsured?.limit else { return }
        
        switch type {
        case .deductible:
            leftToDeductibleLabel.setHighlightedText(text: "$ \(remainingDeductable) " + "coverageDetails.deductibleProgressBar.label".localized,
                                                     style: TextStyle(color: .secondaryDarkest),
                                                     highlightedText: "\(remainingDeductable)")
            minimumDeductibleLabel.set(text: "$0", style: style)
            maximumDeductibleLabel.set(text: "$\(totalDeductable.formatted)", style: style)
        case .annualLimit:
            leftToDeductibleLabel.isHidden = true
            minimumDeductibleLabel.set(text: "$0", style: style)
            maximumDeductibleLabel.set(text: "$\(totalAnnualLimit.formatted)", style: style)
        }
    }
    
    private func setupViews() {
        guard let deductibleProgress = deductableProgress,
              let annualLimitProgress = annualLimitProgress else { return }
        switch type {
        case .deductible:
            progressWidthConstraint.constant = deductibleProgress * maxDeductableProgress + initialValue
            progressBarView.backgroundColor = .primaryDarkest
        case .annualLimit:
            progressWidthConstraint.constant = annualLimitProgress * maxAnnualLimitProgress + initialValue
            progressBarView.backgroundColor = .tertiaryDark
        }
    }
}

// MARK: - Public Methods
extension CoverageDetailsProgressBar {
    
    func update(policy: PetPolicy) {
        self.policy = policy
        type = .annualLimit
        setupLayout()
    }
}
