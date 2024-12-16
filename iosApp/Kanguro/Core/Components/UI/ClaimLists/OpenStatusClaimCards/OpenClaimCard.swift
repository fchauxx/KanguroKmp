import UIKit
import KanguroSharedDomain
import KanguroPetDomain

class OpenClaimCard: BaseTableViewCell {
    
    // MARK: - IB Outlets
    @IBOutlet private var petImageView: UIImageView!
    @IBOutlet private var petNameLabel: CustomLabel!
    @IBOutlet private var costLabel: CustomLabel!
    @IBOutlet private var lastUpdateLabel: CustomLabel!
    @IBOutlet private var detailsButton: CustomButton!
    @IBOutlet private var trackerView: ClaimTrackerView!
    @IBOutlet private var vetPayButton: CustomButton!
    
    // MARK: - Stored Properties
    var claim: PetClaim?
    
    // MARK: - Actions
    private var goToDetailsAction: ClaimClosure = { _ in }
    private var didTapPayVetAction: ClaimClosure = { _ in }
}

// MARK: - Life Cycle
extension OpenClaimCard {
    
    override func prepareForReuse() {
        super.prepareForReuse()
        resetViews()
    }
    
    override func layoutSubviews() {
        super.layoutSubviews()
        setCellSpacement(bottom: 8)
    }
}

// MARK: - Setup
extension OpenClaimCard {
    
    func setupLayout() {
        setupLabels()
        setupImage()
        setupTrackerView()
        setupDetailsButton()
        
        if claim?.reimbursementProcess == .VeterinarianReimbursement {
            setupVetButton()
        }
    }
    
    private func resetViews() {
        resetLabels()
        trackerView.resetData()
    }
    
    private func resetLabels() {
        petNameLabel.text = ""
        costLabel.text = ""
        lastUpdateLabel.text = ""
    }
    
    private func setupLabels() {
        guard let petName = claim?.pet?.name,
              let amount = claim?.amount else { return }
        
        let amountFormatted = amount.formatted
        petNameLabel.set(text: petName,
                         style: TextStyle(color: .secondaryDarkest, weight: .black, size: .p16))
        costLabel.setHighlightedText(text: "claimStatusCards.claimAmount".localized + ": " + "$\(amountFormatted)",
                                     style: TextStyle(color: .secondaryDark, size: .p11),
                                     highlightedText: amountFormatted)
        
        if let date = claim?.updatedAt?.USADate {
            lastUpdateLabel.setHighlightedText(text: "claimStatusCards.lastUpdate".localized + date,
                                               style: TextStyle(color: .secondaryDark, weight: .regular, size: .p11),
                                               highlightedText: date)
            lastUpdateLabel.isHidden = false
        }
    }
    
    private func setupImage() {
        guard let claim = claim else { return }
        petImageView.kf.setImage(with: claim.pet?.petPictureResource, placeholder: claim.pet?.placeholderImage)
        claim.pet != nil ? petImageView.setAsCircle() : (petImageView.isHidden = true)
    }
    
    private func setupTrackerView() {
        guard let claim else { return }
        trackerView.setup(statusList: claim.trackerStatusList, currentClaim: claim)
    }
    
    private func setupDetailsButton() {
        detailsButton.set(title: "claimStatusCard.details.label".localized, style: .outlined)
        detailsButton.onTap { [weak self] in
            guard let self = self,
                  let claim = self.claim else { return }
            self.goToDetailsAction(claim)
        }
    }
    
    private func setupVetButton() {
        vetPayButton.set(title: "claimStatusCards.payVet.button".localized,
                         style: .underlined(color: .white))
        vetPayButton.updateBackgroundColor(with: .secondaryDarkest)
        if let icon = UIImage(named: "ic-share") {
            vetPayButton.updateIcon(icon)
        } else {
            vetPayButton.setImage(nil, for: .normal)
        }
        
        vetPayButton.onTap { [weak self] in
            guard let self,
                  let claim = self.claim else { return }
            self.didTapPayVetAction(claim)
        }
        
        vetPayButton.isHidden = false
    }
}

// MARK: - Public Methods
extension OpenClaimCard {
    
    func update(claim: PetClaim) {
        self.claim = claim
        setupLayout()
    }
    
    func update(goToDetailsAction: @escaping ClaimClosure) {
        self.goToDetailsAction = goToDetailsAction
    }
    
    func update(didTapPayVetAction: @escaping ClaimClosure) {
        self.didTapPayVetAction = didTapPayVetAction
    }
}
