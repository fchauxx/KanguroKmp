import UIKit

class PopUpItem: BaseView, NibOwnerLoadable {
    
    // MARK: - IB Outlets
    @IBOutlet var imageView: UIImageView!
    @IBOutlet var titleLabel: CustomLabel!
    
    // MARK: - Stored Properties
    var data: PopUpData?
    
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
extension PopUpItem {
    
    private func setupLayout() {
        setupLabels()
        setupImages()
    }
    
    func setupLabels() {
        guard let title = data?.title else { return }
        titleLabel.set(text: title, style: TextStyle(color: .secondaryDarkest, weight: .bold, size: .p16))
    }
    
    func setupImages() {
        guard let image = data?.image else { return }
        imageView.image = image
    }
}

// MARK: - Public Methods
extension PopUpItem {
    
    func update(data: PopUpData) {
        self.data = data
        setupLayout()
    }
}

// MARK: - IB Actions
extension PopUpItem {
    
    @IBAction private func buttonTouchUpInside(_ sender: UIButton) {
        data?.action()
    }
}
