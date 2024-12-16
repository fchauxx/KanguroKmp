import UIKit

class ClaimTrackerItemView: BaseView, NibOwnerLoadable {
    
    // MARK: - IB Outlets
    @IBOutlet private var titleLabel: CustomLabel!
    @IBOutlet private var indexLabel: CustomLabel!
    
    @IBOutlet private var confirmedView: UIView!
    @IBOutlet private var confirmedStickView: UIView!
    @IBOutlet private var confirmedStickViewTraillingDistance: NSLayoutConstraint!
    @IBOutlet private var confirmedIconImageView: UIImageView!
    
    @IBOutlet private var neutralBgView: UIView!
    @IBOutlet private var neutralStickView: UIView!
    @IBOutlet private var neutralBgViewWidth: NSLayoutConstraint!
    
    // MARK: - Stored Properties
    var data: ClaimTrackerViewItemData?
    
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
extension ClaimTrackerItemView {
    
    private func setupLayout() {
        setupLabels()
        setupImages()
        setupViews()
    }
    
    private func setupLabels() {
        guard let data = data else { return }
        
        let indexText = "\(data.status.index)"
        let alignment: NSTextAlignment = (data.status == .Closed) ? .center : .left
        let color: UIColor = data.isConfirmed ? .secondaryDarkest : .secondaryLight
        
        titleLabel.set(text: data.status.title,
                       style: TextStyle(color: color, weight: .bold, size: .p12, alignment: alignment))
        titleLabel.setupToFitWidth()
        indexLabel.set(text: indexText,
                       style: TextStyle(color: color, weight: .black, size: .p21))
    }
    
    private func setupViews() {
        guard let data = data else { return }
        neutralBgView.setAsCircle()
        confirmedView.setAsCircle()
        
        neutralStickView.isHidden = (data.status == .Closed)
        
        confirmedView.isHidden = !data.isConfirmed
        let confirmedColor: UIColor = data.isConfirmed ? .positiveMedium : .clear
        confirmedView.backgroundColor = !data.shouldBeAlertView ? confirmedColor : .warningMedium
        
        confirmedStickView.isHidden = !data.isConfirmed
        confirmedStickViewTraillingDistance.constant = data.isNextItemConfirmed ? -4 : 12
        confirmedStickView.backgroundColor = (!data.shouldBeAlertView ? .positiveMedium : .clear)
        if data.shouldShowCommunicationProgress {
            confirmedStickView.applyGradient(isVertical: false,
                                             colorArray: [.positiveMedium, .warningMedium])
        }
    }
    
    private func setupImages() {
        guard let data = data else { return }
        let image = data.shouldBeAlertView ? UIImage(named: "ic-question") : UIImage(named: "ic-confirmed")
        confirmedIconImageView.image = image
    }
}

// MARK: - Public Methods
extension ClaimTrackerItemView {
    
    func setup(data: ClaimTrackerViewItemData) {
        self.data = data
        setupLayout()
    }
}
