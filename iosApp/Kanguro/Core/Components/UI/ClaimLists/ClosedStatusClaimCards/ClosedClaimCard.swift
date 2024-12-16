import UIKit
import KanguroSharedDomain
import KanguroPetDomain

class ClosedClaimCard: BaseTableViewCell {
    
    // MARK: - IB Outlets
    @IBOutlet private var coloredDetailView: UIView!
    @IBOutlet private var petImageView: UIImageView!
    @IBOutlet private var petNameLabel: CustomLabel!
    @IBOutlet private var costLabel: CustomLabel!
    @IBOutlet private var feeTypeLabel: CustomLabel!
    @IBOutlet private var lastUpdateLabel: CustomLabel!
    @IBOutlet private var detailsButton: CustomButton!
    @IBOutlet private var statusLabelView: StatusLabelView!
    
    // MARK: - Stored Properties
    var claim: PetClaim?
    
    // MARK: - Actions
    var goToDetailsAction: ClaimClosure = { _ in }
}

// MARK: - Life Cycle
extension ClosedClaimCard {
    
    override func prepareForReuse() {
        super.prepareForReuse()
        resetLabels()
    }
    override func layoutSubviews() {
        super.layoutSubviews()
        setCellSpacement(bottom: 8)
        setupLayout()
    }
}

// MARK: - Setup
extension ClosedClaimCard {
    
    func setupLayout() {
        setupLabels()
        setupImage()
        setupDetailsButton()
        setupStatusLabelView()
        setupViews()
    }
    
    private func resetLabels() {
        petNameLabel.text = ""
        costLabel.text = ""
        lastUpdateLabel.text = ""
        feeTypeLabel.text = ""
    }
    
    private func setupLabels() {
        
        let grayStyle = TextStyle(color: .neutralMedium, weight: .regular, size: .p11)
        if let petName = claim?.pet?.name {
            petNameLabel.set(text: petName, style: TextStyle(color: .secondaryDarkest, weight: .black, size: .p16))
            petNameLabel.isHidden = false
        }
        if let cost = claim?.amountTransferred?.formatted {
            costLabel.set(text: "claimStatusCards.paid.label".localized + "$\(cost)",
                          style: TextStyle(color: .secondaryDark, weight: .regular, size: .p14))
            costLabel.isHidden = (claim?.status != .Paid)
        }
        if let feeType = claim?.type?.rawValue {
            feeTypeLabel.set(text: feeType, style: grayStyle)
            feeTypeLabel.isHidden = false
        }
        if let date = claim?.updatedAt?.USADate {
            lastUpdateLabel.setHighlightedText(text: "claimStatusCards.closedOn.label".localized + date,
                                               style: grayStyle,
                                               highlightedText: date)
            lastUpdateLabel.isHidden = false
        }
    }
    
    private func setupImage() {
        guard let claim else { return }
        petImageView.kf.setImage(with: claim.pet?.petPictureResource, placeholder: claim.pet?.placeholderImage)
        claim.pet != nil ? petImageView.setAsCircle() : (petImageView.isHidden = true)
    }
    
    private func setupDetailsButton() {
        detailsButton.set(title: "claimStatusCard.details.label".localized, style: .outlined)
        detailsButton.onTap { [weak self] in
            guard let self = self,
                  let claim = self.claim else { return }
            self.goToDetailsAction(claim)
        }
    }
    
    private func setupStatusLabelView() {
        guard let status = claim?.status else { return }
        let data = StatusLabelViewData(text: status.title,
                                       color: status.primaryColor,
                                       image: status.image)
        statusLabelView.setup(data: data)
    }
    
    private func setupViews() {
        guard let color = claim?.status?.primaryColor else { return }
        coloredDetailView.backgroundColor = color
    }
}

// MARK: - Public Methods
extension ClosedClaimCard {
    
    func update(claim: PetClaim) {
        self.claim = claim
    }
    
    func update(goToDetailsAction: @escaping ClaimClosure) {
        self.goToDetailsAction = goToDetailsAction
    }
}
