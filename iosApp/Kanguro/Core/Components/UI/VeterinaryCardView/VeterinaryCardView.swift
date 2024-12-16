import UIKit

class VeterinaryCardView: BaseView, NibOwnerLoadable {
    
    // MARK: - IB Outlets
    @IBOutlet private var titleLabel: CustomLabel!
    @IBOutlet private var subtitleLabel: CustomLabel!
    
    // MARK: - Initializers
    required init?(coder: NSCoder) {
        super.init(coder: coder)
        self.loadNibContent()
        setupLayout()
    }
    
    override init(frame: CGRect) {
        super.init(frame: frame)
        self.loadNibContent()
    }
}

// MARK: - Setup
extension VeterinaryCardView {
    
    private func setupLayout() {
        setupLabels()
    }
    
    private func setupLabels() {
        titleLabel.set(text: "vet.title.label".localized,
                       style: TextStyle(color: .secondaryDarkest, weight: .bold, size: .p16))
        subtitleLabel.set(text: "vet.subtitle.label".localized,
                          style: TextStyle(color: .primaryDarkest, weight: .bold, size: .p11))
    }
    
    private func openInstagram() {
        let username =  "vet.instagramUsername".localized
        guard let url = AppURLs.instagram(username: username).url else { return }
        
        let application = UIApplication.shared
        if application.canOpenURL(url) {
            application.open(url)
        } else {
            // if Instagram app is not installed, open URL inside Safari
            guard let webURL = AppURLs.webInstagram(username: username).url else { return }
            application.open(webURL)
        }
    }
}

// MARK: - IB Actions
extension VeterinaryCardView {
    
    @IBAction private func instagramTouchUpInside(_ sender: UIButton) {
        openInstagram()
    }
}
