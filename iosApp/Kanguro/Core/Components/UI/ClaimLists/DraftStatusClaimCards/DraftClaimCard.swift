import UIKit
import KanguroSharedDomain
import KanguroPetDomain

class DraftClaimCard: BaseTableViewCell {
    
    // MARK: - Stored Properties
    var claim: PetClaim?
    
    // MARK: - IB Outlets
    @IBOutlet private var petImageView: UIImageView!
    @IBOutlet private var petNameLabel: CustomLabel!
    @IBOutlet private var stackView: UIStackView!
    @IBOutlet private var lastUpdateLabel: CustomLabel!
    @IBOutlet private var continueClaimLabel: CustomLabel!
    @IBOutlet private var seeMoreButton: UIButton!
    
    // MARK: - Actions
    var didTapContinueClaimButtonAction: SimpleClosure = {}
    var didTapDeleteAction: SimpleClosure = {}
}

// MARK: - Life Cycle
extension DraftClaimCard {
    
    override func prepareForReuse() {
        super.prepareForReuse()
        resetLabels()
    }
    
    override func layoutSubviews() {
        super.layoutSubviews()
        setCellSpacement(bottom: 8)
    }
}

// MARK: - Setup
extension DraftClaimCard {
    
    private func setupLayout() {
        setupLabels()
        setupImage()
    }
    
    private func resetLabels() {
        petNameLabel.text = ""
        continueClaimLabel.text = ""
        lastUpdateLabel.text = ""
    }
    
    private func setupLabels() {
        guard let claim = claim,
              let petName = claim.pet?.name else { return }
        petNameLabel.set(text: petName,
                         style: TextStyle(color: .secondaryDarkest, weight: .black, size: .p16))
        continueClaimLabel.set(text: "claimStatusCards.continueClaim.label".localized,
                               style: TextStyle(color: .warningDark, weight: .black, size: .p11))
        
        if let date = claim.createdAt?.USADate {
            lastUpdateLabel.setHighlightedText(text: "claimStatusCards.createdOn.label".localized + date,
                                               style: TextStyle(color: .neutralMedium, weight: .regular, size: .p11),
                                               highlightedText: date)
            lastUpdateLabel.isHidden = false
        }
        stackView.layoutIfNeeded()
    }
    
    private func setupImage() {
        guard let claim = claim else { return }
        petImageView.kf.setImage(with: claim.pet?.petPictureResource, placeholder: claim.pet?.placeholderImage)
        claim.pet != nil ? petImageView.setAsCircle() : (petImageView.isHidden = true)
    }
    
    private func showPopUpMenu() {
        guard let popUpMenu = PopUpMenuViewController.create() as? PopUpMenuViewController,
              let keyWindow = UIApplication.shared.connectedScenes
            .compactMap({ $0 as? UIWindowScene })
            .flatMap({ $0.windows })
            .first(where: { $0.isKeyWindow }), let currentViewController = keyWindow.rootViewController,
              let seeMoreButtonPosition = seeMoreButton.pointRelatedToScreen else { return }
        
        let popUp = PopUpViewController(contentViewController: popUpMenu)
        
        let continueButtonAction = { [weak self] in
            guard self != nil else { return }
            popUp.close()
            self?.didTapContinueClaimButtonAction()
        }
        
        popUpMenu.addMenu(popUpData: PopUpData(title: "claims.continue.popUp".localized, image: UIImage(named: "ic-edit"),
                                               action: continueButtonAction))
        //TODO: - Disabled for now
        //        popUpMenu.addMenu(popUpData: PopUpData(title: "claims.delete.popUp".localized, image: UIImage(named: "ic-delete"),
        //                                               action: didTapDeleteAction))
        
        let seeMoreButtonHeight = seeMoreButton.frame.height
        let seeMoreButtonWidth = seeMoreButton.frame.width
        let x = seeMoreButtonPosition.x + seeMoreButtonWidth/2
        let y = seeMoreButtonPosition.y + seeMoreButtonHeight/2
        
        popUp.setContentViewConstraints(positionX: x, positionY: y, leftAligned: false)
        popUp.show(onViewController: currentViewController)
    }
}

// MARK: - Public Methods
extension DraftClaimCard {
    
    func setup(claim: PetClaim) {
        self.claim = claim
        setupLayout()
    }
}

// MARK: - IB Actions
extension DraftClaimCard {
    
    @IBAction private func seeMoreButtonTouchUpInside(_ sender: UIButton) {
        showPopUpMenu()
    }
    
    @IBAction private func continueClaimButtonTouchUpInside(_ sender: UIButton) {
        didTapContinueClaimButtonAction()
    }
}
