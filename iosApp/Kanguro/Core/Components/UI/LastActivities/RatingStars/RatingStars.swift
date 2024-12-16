import UIKit

class RatingStars: BaseView, NibOwnerLoadable {
    
    // MARK: - IB Outlets
    @IBOutlet var rateStars: [UIImageView]!
    
    // MARK: - Actions
    var didRateAction: SimpleClosure = {}
    
    // MARK: - Stored Properties
    var rate: Int? = 0
    
    // MARK: - Initializers
    required init?(coder: NSCoder) {
        super.init(coder: coder)
        self.loadNibContent()
    }
}

// MARK: - Life Cycle
extension RatingStars {
    
    override func layoutSubviews() {
        super.layoutSubviews()
        showFilledStars()
    }
}

// MARK: - Setup
extension RatingStars {
    
    private func showFilledStars() {
        guard let rate = rate else { return }
        for star in rateStars {
            if star.tag <= rate {
                star.isHidden = false
            }
        }
    }
}

// MARK: - Public Methods
extension RatingStars {
    
    func update(rate: Int) {
        self.rate = rate
    }
}

// MARK: - IB Actions
extension RatingStars {
    @IBAction func rateTouchUpInside(_ sender: UIButton) {
        rate = sender.tag
        rateStars.forEach { $0.isHidden = true }
        showFilledStars()
    }
}


