import UIKit

class DonationTypeViewController: BaseViewController {
    
    // MARK: - Dependencies
    var viewModel: DonationTypeViewModel!
    
    // MARK: - IB Outlets
    @IBOutlet private var titleLabel: CustomLabel!
    @IBOutlet private var subtitleLabel: CustomLabel!
    @IBOutlet private var donationCollectionView: DonationCollectionView!
    
    @IBOutlet private var donationValueView: UIView!
    @IBOutlet private var donatedImageView: UIImageView!
    @IBOutlet private var donatedTitleLabel: CustomLabel!
    @IBOutlet private var donatedAmountLabel: CustomLabel!
    
    // MARK: - Stored Properties
    let deviceLogicalHeight: CGFloat = UIScreen.main.bounds.height
    
    // MARK: Actions
    var goBackAction: SimpleClosure = {}
    var didTapDonationCard: DonationTypeClosure = { (_,_) in }
}

// MARK: - Life Cycle
extension DonationTypeViewController {
    
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
extension DonationTypeViewController {
    
    private func changed(state: DonationTypeViewState) {
        
        switch state {
        case .started:
            viewModel.analyticsLogScreen()
            viewModel.getDonationCauses()
            viewModel.getDonatedValue()
        case .requestFailed:
            showSimpleAlert(message: viewModel.requestError)
        case .requestDonationCausesSucceeded:
            setupDonationCollectionView()
        case .requestDonatedValueSucceeded:
            setupBottomDonationView()
        default:
            break
        }
    }
}

// MARK: - Setup
extension DonationTypeViewController {
    
    private func setupLayout() {
        setupLabels()
        setupImages()
    }
    
    private func setupLabels() {
        titleLabel.set(text: "donation.title.label".localized,
                       style: TextStyle(color: .secondaryDarkest, weight: .bold, size: .h32, font: .raleway))
        subtitleLabel.set(text: "donation.subtitle.label".localized,
                          style: TextStyle(color: .neutralDark, size: .p16))
    }
    
    private func setupImages() {
        if deviceLogicalHeight <= 667 {
            donatedImageView.translatesAutoresizingMaskIntoConstraints = false
            donatedImageView.heightAnchor.constraint(equalToConstant: 35).isActive = true
            donatedImageView.widthAnchor.constraint(equalToConstant: 35).isActive = true
        }
    }
    
    private func setupDonationCollectionView() {
        guard let donationCauses = viewModel.donationCauses else { return }
        donationCollectionView.update(tapAction: didTapDonationCard)
        donationCollectionView.setup(donationTypes: viewModel.donationData, donationCauses: donationCauses)
    }
    
    private func setupBottomDonationView() {
        let value = viewModel.donatedValue
        donatedTitleLabel.set(text: value != nil ? "donation.donated.label".localized : "donation.placeholder.label".localized,
                              style: TextStyle(color: .white, weight: .bold, size: .h24, font: .lato, alignment: .center, lines: 2))
        if value != nil {
            donatedAmountLabel.isHidden = false
            donatedAmountLabel.set(text: "$\(value ?? 0)",
                                   style: TextStyle(color: .white, weight: .bold, size: .h24, font: .lato))
        }
    }
}

// MARK: - IB Actions
extension DonationTypeViewController {
    
    @IBAction private func goBackTouchUpInside(_ sender: UIButton) {
        goBackAction()
    }
}
