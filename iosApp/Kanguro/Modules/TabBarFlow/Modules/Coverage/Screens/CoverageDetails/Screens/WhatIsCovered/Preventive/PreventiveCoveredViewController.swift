import UIKit

class PreventiveCoveredViewController: BaseViewController {
    
    // MARK: - Dependencies
    var viewModel: PreventiveCoveredViewModel!
    
    // MARK: - IB Outlets
    @IBOutlet private var titleLabel: CustomLabel!
    @IBOutlet private var subtitleLabel: CustomLabel!
    @IBOutlet private var planCoversTitleLabel: CustomLabel!
    @IBOutlet private var shadowView: UIView!
    @IBOutlet private var preventiveCoveredListView: PreventiveCoveredListView!
    @IBOutlet private var getReimbursementButton: CustomButton!
    
    // MARK: Actions
    var backAction: SimpleClosure = {}
    var goToNewClaimAction: SimpleClosure = {}
    var didFinishPreventiveItemsAction: StringClosure = { _ in }
}

// MARK: - Life Cycle
extension PreventiveCoveredViewController {
    
    override func viewDidLoad() {
        super.viewDidLoad()
        setupLayout()
        observe(viewModel.$state) { [weak self] state in
            guard let self = self else { return }
            self.changed(state: state)
        }
    }
}

// MARK: - View State
extension PreventiveCoveredViewController {
    
    private func changed(state: PreventiveCoveredViewState) {
        
        switch state {
        case .started:
            viewModel.analyticsLogScreen()
            viewModel.getPetPolicy()
        case .requestFailed:
            showActionAlert(message: viewModel.requestError, action: backAction)
        case .getPolicySucceeded:
            viewModel.handleEditableCase()
        case .getPetSucceeded, .getPetFailed:
            updateTitleLabel()
            viewModel.getCoverages()
        case .getCoverageSucceeded:
            setupCoverageListView()
        case .fallbackCoverageRequest:
            viewModel.coverageFallbackRequestUsed = true
            viewModel.getCoverages()
        default:
            break
        }
    }
}

// MARK: - Setup
extension PreventiveCoveredViewController {
    
    private func setupLayout() {
        setupLabels()
        setupViews()
        setupButtons()
    }
    
    private func setupViews() {
        shadowView.setShadow(shadowOpacity: 0.3)
    }
    
    private func setupCoverageListView() {
        guard let coverages = viewModel.coverageDataList else { return }
        preventiveCoveredListView.setupLayout()
        preventiveCoveredListView.setupData(coverages,
                                            isEditable: (viewModel.type == .editable))
        preventiveCoveredListView.didTapCheckboxAction = { [weak self] in
            guard let self else { return }
            self.setButtonEnabled()
        }
    }
    
    private func setupLabels() {
        if !viewModel.isEditable {
            titleLabel.set(text: "preventiveCovered.title.label".localized,
                           style: TextStyle(color: .secondaryDarkest, weight: .bold, size: .h32, font: .raleway))
        }
        subtitleLabel.set(text: "preventiveCovered.subtitle.label".localized.uppercased(),
                          style: TextStyle(color: .secondaryMedium, weight: .bold, size: .p16))
        planCoversTitleLabel.set(text: "preventiveCovered.planCoverTitle.label".localized.uppercased(),
                                 style: TextStyle(color: .secondaryMedium, weight: .bold, font: .raleway))
    }
    
    private func updateTitleLabel() {
        var text = ""
        if let petName = viewModel.pet?.name {
            text = "\(petName)'s " + "preventiveCovered.covered.label".localized
        } else {
            text = "preventiveCovered.title.label".localized
        }
        titleLabel.set(text: text,
                       style: TextStyle(color: .secondaryDarkest, weight: .bold, size: .h32, font: .raleway))
    }
    
    private func setupButtons() {
        getReimbursementButton.set(title: "preventiveCovered.getReimbursement.button".localized,
                                   style: .primary)
        getReimbursementButton.setImage(nil, for: .normal)
        switch viewModel.type {
        case .regular:
            getReimbursementButton.onTap(goToNewClaimAction)
        case .editable:
            setButtonEnabled()
            getReimbursementButton.onTap { [weak self] in
                guard let self else { return }
                self.didFinishPreventiveItemsAction(self.preventiveCoveredListView.concatenatedNames)
            }
        }
    }
}

// MARK: - Private Methods
extension PreventiveCoveredViewController {
    
    private func setButtonEnabled() {
        getReimbursementButton.isEnabled(preventiveCoveredListView.hasCheckboxSelected)
    }
}

// MARK: - IB Actions
extension PreventiveCoveredViewController {
    
    @IBAction private func backButtonTouchUpInside(_ sender: UIButton) {
        backAction()
    }
}
