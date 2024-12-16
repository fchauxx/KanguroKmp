import UIKit
import Kingfisher
import KanguroSharedDomain
import KanguroPetDomain

class CoverageDetailsViewController: BaseViewController {
    
    // MARK: - Dependencies
    var viewModel: CoverageDetailsViewModel!

    var pet: Pet {
        viewModel.policy.pet
    }
    var placeholder: UIImage? {
        guard let placeholder: UIImage = pet.type == .Dog ? UIImage(named: "defaultPicture-dog") : UIImage(named: "defaultPicture-cat") else { return nil }
        return placeholder
    }
    var petPictureResource: KF.ImageResource? {
        guard let petPictureUrl = pet.petPictureUrl, let url = URL(string: petPictureUrl) else { return nil}
        return KF.ImageResource(downloadURL: url, cacheKey: petPictureUrl.urlWithoutQuery)
    }

    // MARK: - IB Outlets
    @IBOutlet var transitionView: UIView!
    @IBOutlet var transitionImageView: UIImageView!
    @IBOutlet var transitionViewHeight: NSLayoutConstraint!
    @IBOutlet var transitionViewWidth: NSLayoutConstraint!
    @IBOutlet var transitionPetImageViewHeightConstraint: NSLayoutConstraint!
    @IBOutlet var transitionBottomViewConstraint: NSLayoutConstraint!
    @IBOutlet var petImageView: UIImageView!
    @IBOutlet var backButtonView: UIView!
    @IBOutlet var scrollView: UIScrollView!
    @IBOutlet var contentView: UIView!
    @IBOutlet var headerImageHeight: NSLayoutConstraint!
    @IBOutlet var minimumBottomScrollableConstraint: NSLayoutConstraint!
    @IBOutlet var maximumTopScrollableConstraint: NSLayoutConstraint!
    @IBOutlet var backButtonTopConstraint: NSLayoutConstraint!
    @IBOutlet var editPetPictureStackViewHeightConstraint: NSLayoutConstraint!
    @IBOutlet var helloLabel: CustomLabel!
    @IBOutlet var petNameLabel: CustomLabel!
    @IBOutlet var petDataLabel: CustomLabel!
    @IBOutlet var alphaView: UIView!
    @IBOutlet var perroBuenoCoverageDetailCard: CoverageDetailsCard!
    @IBOutlet var preventiveCoverageDetailCard: CoverageDetailsCard!
    @IBOutlet var paymentCoverageDetailCard: CoverageDetailsCard!
    @IBOutlet var actionCardsList: ActionCardsList!
    @IBOutlet weak var policyNumberLabel: CustomLabel!
    @IBOutlet var petPictureMenuStackView: UIStackView!
    @IBOutlet var petPictureStackView: UIStackView!
    
    // MARK: Actions
    var goBackAction: SimpleClosure = {}
    var goToBillingPreferencesAction: SimpleClosure = {}
    var didTapFileClaimAction: SimpleClosure = {}
    var didTapClaimsAction: SimpleClosure = {}
    var didTapFAQAction: SimpleClosure = {}
    var didTapPlanCoveredAction: SimpleClosure = {}
    var didTapPreventiveCoveredAction: SimpleClosure = {}
    var didTapDocumentAction: AnyClosure = { _ in }
    var didTapUpdatePetPictureAction: SimpleClosure = {}
    var didTapDirectPayAction: PetsClosure = { _ in }
    
    // MARK: - Stored Properties
    var maxHeaderHeight: CGFloat = 300
    let minHeaderHeight: CGFloat = 72
    var previousScrollOffset: CGFloat = 0
    var cardPosition: CGPoint?
    var imagePicker = UIImagePickerController()

    // MARK: - Computed Properties
    var bottomScrollLimit: CGFloat {
        return (-72 + contentView.cornerRadius)
    }
}

// MARK: - Life Cycle
extension CoverageDetailsViewController {
    
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
extension CoverageDetailsViewController {
    
    private func changed(state: CoverageDetailsViewState) {
        
        switch state {
        case .started:
            viewModel.analyticsLogScreen()
            viewModel.getPolicy()
        case .loading:
            showLoadingView(shouldAnimate: false)
        case .requestFailed:
            hideLoadingView(duration: 0)
            showSimpleAlert(message: viewModel.requestError)
        case .requestSucceeded:
            setupDocumentsCards()
            hideLoadingView(duration: 0)
        case .requestPolicySucceeded:
            setupCoverageDetailCards()
            viewModel.getPolicyDocuments()
        case .updatedPetPicture(let image):
            petImageView.image = image
            hideLoadingView(duration: 0)
            didTapUpdatePetPictureAction()
        default:
            break
        }
    }
}



// MARK: - Private Methods
extension CoverageDetailsViewController {
    
    @objc func selectPicture() {
        imagePicker.sourceType = .photoLibrary
        self.present(imagePicker, animated: true)
    }
    
    @objc func takePicture() {
        imagePicker.sourceType = .camera
        self.present(imagePicker, animated: true)
    }
}

// MARK: - UIScrollViewDelegate
extension CoverageDetailsViewController: UIScrollViewDelegate {
    
    func scrollViewDidScroll(_ scrollView: UIScrollView) {
        if scrollView.contentOffset.y < bottomScrollLimit { scrollView.contentOffset.y = bottomScrollLimit }
        let scrollDiff = (scrollView.contentOffset.y - previousScrollOffset)
        let isScrollingDown = scrollDiff > 0
        let isScrollingUp = scrollDiff < 0
        if canHeaderBeAnimated(scrollView) {
            var newHeight = headerImageHeight.constant
            if isScrollingDown {
                newHeight = max(minHeaderHeight, headerImageHeight.constant - abs(scrollDiff))
            } else if isScrollingUp {
                newHeight = min(maxHeaderHeight, headerImageHeight.constant + abs(scrollDiff))
            }
            if newHeight != headerImageHeight.constant {
                headerImageHeight.constant = newHeight
                setScrollPosition()
                previousScrollOffset = scrollView.contentOffset.y
            }
        }
    }
    
    func canHeaderBeAnimated(_ scrollView: UIScrollView) -> Bool {
        let scrollViewMaxHeight = scrollView.frame.height + self.headerImageHeight.constant - minHeaderHeight
        return scrollView.contentSize.height > scrollViewMaxHeight
    }
}


// MARK: - IB Action
extension CoverageDetailsViewController {
    
    @IBAction private func backButtonTouchUpInside(_ sender: UIButton) {
        goBackAction()
    }
    
    @IBAction private func editPetPictureButtonTouchUpInside(_ sender: UIButton) {
        petPictureStackView.isHidden = false
        self.hideNavigationTabBar()
    }
}
