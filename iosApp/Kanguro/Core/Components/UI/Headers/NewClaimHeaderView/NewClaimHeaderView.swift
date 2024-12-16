import UIKit

class NewClaimHeaderView: BaseView, NibOwnerLoadable {
    
    // MARK: - IB Outlets
    @IBOutlet private var botPhoto: UIImageView!
    @IBOutlet private var titleMessageLabel: CustomLabel!
    @IBOutlet private var subtitleMessageLabel: CustomLabel!
    
    // MARK: - Initializers
    required init?(coder: NSCoder) {
        super.init(coder: coder)
        self.loadNibContent()
        setupLayout()
    }
}

// MARK: - Life Cycle
extension NewClaimHeaderView {
    
    override func layoutSubviews() {
        super.layoutSubviews()
        setupLayout()
    }
}

// MARK: - Setup
extension NewClaimHeaderView {
    
    private func setupLayout() {
        setupLabels()
        setupImages()
    }
    
    func setupLabels() {
        titleMessageLabel.set(text: "header.title.label".localized,
                              style: TextStyle(color: .secondaryDarkest, weight: .bold, size: .h24, font: .raleway))
        subtitleMessageLabel.set(text: "header.subtitle.label".localized.uppercased(),
                                 style: TextStyle(color: .primaryDarkest, weight: .bold, size: .p12, alignment: .center))
    }
    
    func setupImages() {
        botPhoto.image = UIImage(named: "javier-chatbot")
    }
}
