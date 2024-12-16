import UIKit
import KanguroUserDomain

class DonationCauseViewController: BaseViewController {
    
    // MARK: - Dependencies
    var viewModel: DonationCauseViewModel!
    
    // MARK: - IB Outlets
    @IBOutlet private var navigationBackButton: NavigationBackButton!
    @IBOutlet private var titleImageView: UIImageView!
    @IBOutlet private var titleLabel: CustomLabel!
    @IBOutlet private var selectCauseLabel: CustomLabel!
    @IBOutlet private var donationCauseListView: DonationCauseListView!
    
    // MARK: Actions
    var goBackAction: SimpleClosure = {}
    var goToRootAction: SimpleClosure = {}
    var goNextAction: SimpleClosure = {}
    var didTapDonationCardAction: UserDonationCauseTypeClosure = { _ in }
}

// MARK: - Life Cycle
extension DonationCauseViewController {
    
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
extension DonationCauseViewController {
    
    private func changed(state: DefaultViewState) {
        
        switch state {
        case .started:
            viewModel.analyticsLogScreen()
        case .requestFailed:
            showActionAlert(message: viewModel.requestError, action: goToRootAction)
        case .requestSucceeded:
            showActionAlert(message: "donation.causeChosen.button".localized, action: goNextAction)
        default:
            break
        }
    }
}

// MARK: - Setup
extension DonationCauseViewController {
    
    private func setupLayout() {
        setupLabels()
        setupImages()
        setupNavigationBackButton()
        setupDonationListView()
    }
    
    private func setupLabels() {
        titleLabel.set(text: viewModel.type.title,
                       style: TextStyle(color: .secondaryDarkest, weight: .bold, size: .h32, font: .raleway))
        titleLabel.setupToFitWidth()
        selectCauseLabel.set(text: "donationCause.selectCause.label".localized,
                             style: TextStyle(color: .neutralDark, size: .p16))
    }
    
    private func setupImages() {
        titleImageView.image = viewModel.type.image
    }
    
    private func setupNavigationBackButton() {
        navigationBackButton.update(title: "common.back".localized)
        navigationBackButton.update(action: goBackAction)
    }
    
    private func setupDonationListView() {
        donationCauseListView.setup(donationCauseList: viewModel.donationCauses)
        donationCauseListView.didTapChooseButtonAction = { [weak self] selectedCause in
            guard let self,
                  let userId = self.viewModel.user?.id else { return }
            self.viewModel.patchUserDonationCause(userId: userId,
                                                  charityId: selectedCause.charityId,
                                                  title: selectedCause.title,
                                                  cause: selectedCause.cause)
        }
    }
}

// MARK: - IB Actions
extension DonationCauseViewController {
    
    @IBAction private func goBackTouchUpInside(_ sender: UIButton) {
        goBackAction()
    }
    
    @IBAction private func goToRootTouchUpInside(_ sender: UIButton) {
        goToRootAction()
    }
}
