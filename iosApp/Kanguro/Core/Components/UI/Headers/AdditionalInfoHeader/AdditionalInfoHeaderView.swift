import UIKit

class AdditionalInfoHeaderView: BaseView, NibOwnerLoadable {
    
    // MARK: - IB Outlets
    @IBOutlet private weak var photoBackgroundImageView: UIImageView!
    @IBOutlet var botPhoto: UIImageView!
    @IBOutlet private var backgroundCircleView: UIView!
    @IBOutlet private var greenCircleView: UIView!
    @IBOutlet var titleMessageLabel: CustomLabel!
    @IBOutlet private var subtitleMessageLabel: CustomLabel!
    
    // MARK: - Initializers
    required init?(coder: NSCoder) {
        super.init(coder: coder)
        self.loadNibContent()
    }
}

// MARK: - Life Cycle
extension AdditionalInfoHeaderView {
    
    override func layoutSubviews() {
        super.layoutSubviews()
        setupLayout()
    }
}

// MARK: - Setup
extension AdditionalInfoHeaderView {
    
    private func setupLayout() {
        setupLabels()
        setupImages()
    }
    
    func setupLabels() {
        titleMessageLabel.set(text: "header.title.label".localized, style: TextStyle(color: .secondaryDarkest, weight: .bold, size: .p21, font: .raleway))
        subtitleMessageLabel.set(text: "header.subtitle.label".localized.uppercased(), style: TextStyle(color: .white, weight: .bold, size: .p12))
    }
    
    func setupImages() {
        photoBackgroundImageView.image = UIImage(named: "bg-javier")
        botPhoto.image = UIImage(named: "javier")
        backgroundCircleView.setAsCircle()
        greenCircleView.setAsCircle()
    }
}
