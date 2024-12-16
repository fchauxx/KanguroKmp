import UIKit

class VetLocationCardView: UIView, NibOwnerLoadable {
    
    // MARK: - IB Outlets
    @IBOutlet private var nameLabel: CustomLabel!
    @IBOutlet private var adressLabel: CustomLabel!
    @IBOutlet private var statusLabel: CustomLabel!
    @IBOutlet private var phoneButton: UIButton!
    
    // MARK: - Stored Properties
    var data: VetLocationData?
    
    // MARK: - Computed Properties
    var labels: [CustomLabel] {
        return [nameLabel, adressLabel, statusLabel]
    }
    
    // MARK: - Actions
    var didTapLocationAction: LocationClosure = { _ in }
    
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
extension VetLocationCardView {
    
    private func setupLayout() {
        setupLabels()
        setupButtons()
    }
    
    private func setupLabels() {
        guard let name = data?.name  else { return }
        
        nameLabel.set(text: name,
                      style: TextStyle(color: .secondaryDarkest, weight: .bold, size: .p16))
        if let adressText = data?.adressText {
            adressLabel.set(text: adressText, style: TextStyle(color: .tertiaryDark, size: .p12))
            adressLabel.isHidden = false
        }
        if let status = data?.status {
            statusLabel.set(text: status.0, style: TextStyle(color: status.1, size: .p12))
            statusLabel.isHidden = false
        }
        labels.forEach { $0.setupToFitWidth() }
    }
    
    private func setupButtons() {
        phoneButton.isHidden = (data?.phoneNumber == nil)
    }
}

// MARK: - Private Methods
extension VetLocationCardView {
    
    private func callSupport() {
        guard let phoneNumber = data?.phoneNumber?.onlyNumbers else { return }
        guard let url = URL(string: "tel://+\(phoneNumber)"),
              UIApplication.shared.canOpenURL(url) else { return }
        UIApplication.shared.open(url)
    }
}

// MARK: - Public Methods
extension VetLocationCardView {
    
    func setup(data: VetLocationData) {
        self.data = data
        setupLayout()
    }
    
    func update(locationAction: @escaping LocationClosure) {
        self.didTapLocationAction = locationAction
    }
}

// MARK: - IB Action
extension VetLocationCardView {
    
    @IBAction private func callButtonTouchUpInside(_ sender: UIButton) {
        callSupport()
    }
    
    @IBAction private func locationButtonTouchUpInside(_ sender: UIButton) {
        guard let location = data?.location else { return }
        didTapLocationAction(location)
    }
}
