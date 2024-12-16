import UIKit

class ClaimsViewController: BaseViewController {
    
    // MARK: - Dependencies
    var viewModel: ClaimsViewModel!
    
    // MARK: - IB Outlets
    @IBOutlet var navigationBackButton: NavigationBackButton!
    @IBOutlet private var statusSegmentedControl: CustomSegmentedControl!
    @IBOutlet var titleLabel: CustomLabel!
    @IBOutlet private var claimListsScrollView: UIScrollView!
    @IBOutlet private var contentView: UIView!
    @IBOutlet private var claimListsContentView: UIView!
    @IBOutlet private var claimListsStackView: UIStackView!
    @IBOutlet var openClaimStatusList: ClaimStatusList!
    @IBOutlet private var closedClaimStatusList: ClaimStatusList!
    @IBOutlet private var draftClaimStatusList: ClaimStatusList!
    
    // MARK: - Actions
    var backAction: SimpleClosure = {}
    var goToDetailsAction: ClaimClosure = { _ in }
    var didTapFileClaimAction: SimpleClosure = {}
    var didTapContinueClaimAction: NextStepClosure = { _ in }
    var didTapPayVetAction: ClaimClosure = { _ in }

    // MARK: - Computed Properties
    var claimLists: [ClaimStatusList] {
        return [openClaimStatusList, closedClaimStatusList]
    }
    var partialWidth: CGFloat {
        let totalWidth = self.claimListsContentView.frame.width + claimListsStackView.spacing
        return totalWidth / CGFloat(statusSegmentedControl.numberOfSegments)
    }
}

// MARK: - Life Cycle
extension ClaimsViewController {
    
    override func viewDidLoad() {
        super.viewDidLoad()
        setupLayout()
        observe(viewModel.$state) { [weak self] state in
            guard let self = self else { return }
            self.changed(state: state)
        }
    }
    
    override func viewWillAppear(_ animated: Bool) {
        viewModel.getClaims()
    }
}

// MARK: - View State
extension ClaimsViewController {
    
    private func changed(state: DefaultViewState) {
                
        switch state {
        case .started:
            viewModel.analyticsLogScreen()
        case .loading:
            if viewModel.isFirstRequest { showLoadingView(shouldAnimate: false) }
        case .requestFailed:
            hideLoadingView()
            showActionAlert(message: viewModel.requestError,
                            action: backAction)
        case .requestSucceeded:
            hideLoadingView(completion: setupStatusLists)
        default:
            break
        }
    }
}

// MARK: - Setup
extension ClaimsViewController {
    
    private func setupLayout() {
        setupLabels()
        setupNavigationBackButton()
        setupSegmentedControl()
        setupScrollView()
    }
    
    private func setupLabels() {
        titleLabel.set(text: "claims.title.label".localized,
                       style: TextStyle(color: .secondaryDarkest, weight: .bold, size: .h32, font: .raleway))
        titleLabel.setupToFitWidth()
    }
    
    private func setupNavigationBackButton() {
        navigationBackButton.update(title: "claims.title.navigationBackButton".localized)
        navigationBackButton.update(action: backAction)
    }
    
    func setupStatusLists() {
        guard let openClaims = viewModel.openClaims,
              let closedClaims = viewModel.closedClaims else { return }
        
        openClaimStatusList.setup(claims: openClaims)
        openClaimStatusList.update(goToDetailsAction: goToDetailsAction)
        openClaimStatusList.update(didTapPayVetAction: didTapPayVetAction)
        
        closedClaimStatusList.setup(claims: closedClaims)
        closedClaimStatusList.update(goToDetailsAction: goToDetailsAction)
        
        claimLists.forEach { $0.didDragAction = viewModel.getClaims }
    }
    
    private func setupSegmentedControl() {
        statusSegmentedControl.didChangeSegmentedIndexAction = { [weak self] in
            guard let self = self else { return }
            self.setupScrollView()
        }
        statusSegmentedControl.set(titles: ["claimStatus.open.title".localized,
                                            "claimStatus.closed.title".localized])
    }
}

// MARK: - ScrollViewDelegate
extension ClaimsViewController: UIScrollViewDelegate {
    
    private func setupScrollView() {
        switch self.statusSegmentedControl.selectedIndex {
        case 0:
            self.claimListsScrollView.setContentOffset(CGPoint(x: 0, y: 0), animated: true)
        default:
            self.claimListsScrollView.setContentOffset(CGPoint(x: partialWidth, y: 0), animated: true)
        }
    }
    
    func updateSegmentedControl(scrollView: UIScrollView) {
        let scrollX = claimListsScrollView.contentOffset.x
        
        if scrollX < contentView.frame.width/2 {
            statusSegmentedControl.selectedIndex = 0
        } else {
            statusSegmentedControl.selectedIndex = 1
        }
    }
    
    //TODO: Enable scrolling
    func scrollViewDidEndDecelerating(_ scrollView: UIScrollView) {
        updateSegmentedControl(scrollView: scrollView)
    }
}


// MARK: - IB Actions
extension ClaimsViewController {
    
    @IBAction private func addClaimTouchUpInside(_ sender: UIButton) {
        didTapFileClaimAction()
    }
}


