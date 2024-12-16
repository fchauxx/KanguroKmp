import UIKit

class CreditCardView: BaseView, NibOwnerLoadable {
    
    // MARK: - IB Outlets
    @IBOutlet private var creditCardLabel: CustomLabel!
    
    // MARK: - Stored Properties
    var creditCardNumbers: Int?
    
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
extension CreditCardView {
    
    private func setupLayout() {
        guard let creditCardNumbers = creditCardNumbers else { return }
        creditCardLabel.set(text: "•••• \(creditCardNumbers)", style: TextStyle(color: .secondaryMedium, weight: .bold))
    }
}

// MARK: - Public Methods
extension CreditCardView {
    
    func setup(creditCard: Int) {
        self.creditCardNumbers = creditCard
        setupLayout()
    }
}
