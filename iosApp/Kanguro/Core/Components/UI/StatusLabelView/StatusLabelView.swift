import UIKit

class StatusLabelView: BaseView, NibOwnerLoadable {

    // MARK: - IB Outlets
    @IBOutlet private var titleLabel: CustomLabel!
    @IBOutlet private var imageView: UIImageView!

    // MARK: - Stored Properties
    var data: StatusLabelViewData?

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
extension StatusLabelView {

    private func setupLayout() {
        setupLabels()
        setupImages()
    }
    
    private func setupLabels() {
        guard let data = data else { return }
        titleLabel.set(text: data.text,
                       style: TextStyle(color: data.color))
    }
    
    private func setupImages() {
        guard let data = data else { return }
        imageView.image = data.image
    }
}

// MARK: - Public Methods
extension StatusLabelView {

    func setup(data: StatusLabelViewData) {
        self.data = data
        setupLayout()
    }
}
