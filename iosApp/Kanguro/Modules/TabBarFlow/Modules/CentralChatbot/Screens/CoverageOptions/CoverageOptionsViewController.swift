import UIKit

class CoverageOptionsViewController: BaseViewController {
    
    // MARK: - Dependencies
    var viewModel: CoverageOptionsViewModel!
    
    // MARK: - IB Outlets
    @IBOutlet var titleLabel: CustomLabel!
    @IBOutlet private var subtitleLabel: CustomLabel!
    @IBOutlet var preventiveCoveredListView: PreventiveCoveredListView!
    @IBOutlet private var getReimbursementButton: CustomButton!
    
    // MARK: Actions
    var backAction: SimpleClosure = {}
    var goToNewClaimAction: SimpleClosure = {}
}

// MARK: - Life Cycle
extension CoverageOptionsViewController {
    
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
extension CoverageOptionsViewController {
    
    func changed(state: DefaultViewState) {
        
        switch state {
        case .started:
            viewModel.analyticsLogScreen()
            viewModel.getPolicy()
        case .dataChanged:
            viewModel.getCoverages()
            setupLabels()
        case .requestFailed:
            showActionAlert(message: viewModel.requestError, action: backAction)
        case .requestSucceeded:
            guard let coverages = viewModel.coverageDataList else { return }
            preventiveCoveredListView.setupLayout()
            preventiveCoveredListView.setupData(coverages, isEditable: true)
        default:
            break
        }
    }
}

// MARK: - Setup
extension CoverageOptionsViewController {
    
    private func setupLayout() {
        setupLabels()
        setupButtons()
        setupViews()
    }
    
    func setupViews() {
        preventiveCoveredListView.setShadow(shadowOpacity: 0.3)
    }
    
    private func setupLabels() {
        titleLabel.set(text: (viewModel.petName ?? "") + "preventiveCovered.coverage.label".localized,
                       style: TextStyle(color: .secondaryDarkest, weight: .bold, size: .h32, font: .raleway))
        titleLabel.setupToFitWidth()
        subtitleLabel.set(text: "preventiveCovered.subtitle.label".localized.uppercased(),
                          style: TextStyle(color: .secondaryMedium, weight: .bold, size: .p16))
    }
    
    private func setupButtons() {
        getReimbursementButton.set(title: "preventiveCovered.getReimbursement.button".localized,
                                   style: .primary)
        getReimbursementButton.onTap(goToNewClaimAction)
        getReimbursementButton.setImage(nil, for: .normal)
    }
}

// MARK: - IB Actions
extension CoverageOptionsViewController {
    
    @IBAction private func backButtonTouchUpInside(_ sender: UIButton) {
        backAction()
    }
}
