import UIKit
import KanguroSharedDomain

class PreventiveCoveredCardView: BaseView, NibOwnerLoadable, PreventiveCoveredCardViewProtocol {
    
    // MARK: - IB Outlets
    @IBOutlet var checkboxView: CheckboxView!
    @IBOutlet private var contentView: UIView!
    
    @IBOutlet private var titleLabel: CustomLabel!
    @IBOutlet private var completedLabel: CustomLabel!
    @IBOutlet private var remainingLimitLabel: CustomLabel!
    @IBOutlet var annualLimitLabel: CustomLabel!
    @IBOutlet var remainingValueLabel: CustomLabel!
    @IBOutlet private var availableCustomProgressBar: CustomProgressBarView!
    @IBOutlet private var remainingStackView: UIStackView!
    @IBOutlet private var remainingLabel: CustomLabel!
    @IBOutlet private var completedView: UIView!
    
    // MARK: - Stored Properties
    var data: KanguroSharedDomain.CoverageData?
    var isEditable: Bool?
    
    // MARK: - Computed Properties
    var isCheckboxSelected: Bool {
        return checkboxView.viewModel.isSelected ?? false
    }
    var isMultipleTimesApplicableExam: Bool {
        return (data?.usesLimit ?? 0) > 1
    }
    
    // MARK: - Actions
    var didSelectItemAction: PreventiveItemClosure = { _ in }
    var didTapCheckboxAction: SimpleClosure = {}
    
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
extension PreventiveCoveredCardView {
    
    private func setupLayout() {
        setupLabels()
        setupProgressBar()
        setupCheckBox()
        setupViews()
    }
    
    private func setupCheckBox() {
        guard let isEditable = isEditable,
              let isCompleted = data?.isCompleted else { return }
        checkboxView.viewModel = CheckboxViewModel()
        checkboxView.setup(isEditable: !isCompleted)
        checkboxView.didTapAction = { _ in
            self.didTapCheckboxAction()
        }
        checkboxView.isHidden = !isEditable
    }
    
    func setupLabels() {
        guard let data else { return }
        titleLabel.set(text: data.name, style: TextStyle(color: .secondaryDarkest, weight: .bold, size: .p16))
        completedLabel.set(text: "preventiveCovered.completed.label".localized,
                           style: TextStyle(color: .secondaryMedium, weight: .bold, size: .p12))
        
        if let annualLimit = data.annualLimit,
           let remainingValue = data.remainingValue {
            
            annualLimitLabel.set(text: "preventiveCovered.annualLimitUpTo.label".localized + annualLimit.getCurrencyFormatted(fractionDigits: 2),
                                 style: TextStyle(color: .tertiaryExtraDark, weight: .bold))
            remainingLimitLabel.set(text: remainingValue.getCurrencyFormatted(fractionDigits: 2),
                                    style: TextStyle(color: .secondaryMedium, weight: .bold, size: .p16))
            remainingLabel.set(text: "preventiveCovered.remainingLimit.label".localized,
                               style: TextStyle(color: .secondaryMedium, weight: .regular, size: .p12))
        }
        
        if let remainingAvailableText = data.remainingAvailableText,
           isMultipleTimesApplicableExam && !data.isCompleted {
                remainingValueLabel.set(text: remainingAvailableText + "preventiveCovered.available.label".localized,
                                        style: TextStyle(color: .secondaryMedium, weight: .regular))
                remainingValueLabel.isHidden = false
        }
    }
    
    private func setupViews() {
        guard let isCompleted = data?.isCompleted else { return }
        if isCompleted {
            completedView.isHidden = false
            checkboxView.isHidden = true
            annualLimitLabel.isHidden = true
            availableCustomProgressBar.isHidden = true
            remainingValueLabel.isHidden = true
            remainingLimitLabel.set(text: 0.getCurrencyFormatted(fractionDigits: 2),
                                    style: TextStyle(color: .secondaryMedium, weight: .bold, size: .p16))
        }
    }
    
    private func setupProgressBar() {
        guard let data,
              let percentProgress = data.remainingAvailable else { return }
        if isMultipleTimesApplicableExam && !data.isCompleted  {
            availableCustomProgressBar.setupProgressBar(progressPercent: percentProgress,
                                                        color: .positiveDarkest)
            availableCustomProgressBar.isHidden = false
        }
    }
}

// MARK: - Public Methods
extension PreventiveCoveredCardView {
    
    func setupData(_ data: KanguroSharedDomain.CoverageData, isEditable: Bool) {
        self.data = data
        self.isEditable = isEditable
        setupLayout()
    }
}
