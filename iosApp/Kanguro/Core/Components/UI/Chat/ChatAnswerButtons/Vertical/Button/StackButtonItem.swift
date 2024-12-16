import UIKit

enum StackButtonItemType {
    
    case standard
    case file
}

class StackButtonItem: BaseView, NibOwnerLoadable {
    
    // MARK: - IB Outlets
    @IBOutlet private var leadingStackView: UIStackView!
    @IBOutlet private var leadingImage: UIImageView!
    @IBOutlet private var leadingLabel: CustomLabel!
    @IBOutlet private var centerLabel: CustomLabel!
    
    // MARK: - Stored Properties
    var data: StackButtonData?
    var nextStepData: NextStepParameters?
    var index: Int?
    
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

// MARK: - Life Cycle
extension StackButtonItem {
    
    override func layoutSubviews() {
        super.layoutSubviews()
        setupLayout()
    }
}

// MARK: - Setup
extension StackButtonItem {
    
    private func setupLayout() {
        setupLabels()
        setupImages()
        setupTypeViews()
    }
    
    private func setupTypeViews() {
        guard let data = data,
              let type = data.type else { return }
        type == .standard ? (leadingStackView.isHidden = true) : (centerLabel.isHidden = true)
    }
    
    private func setupLabels() {
        guard let data = data,
              let title = data.title else { return }
        let style = TextStyle(color: .secondaryDarkest, size: .p16)
        leadingLabel.set(text: title, style: style)
        centerLabel.set(text: title, style: style)
    }
    
    private func setupImages() {
        guard let data = data,
              let image = data.image else { return }
        leadingImage.image = image
        leadingImage.tintColor = .secondaryMedium
    }
}

// MARK: - Public Methods
extension StackButtonItem {
    
    func update(data: StackButtonData, nextStepData: NextStepParameters?) {
        self.data = data
        self.nextStepData = nextStepData
    }
}

// MARK: - IB Actions
extension StackButtonItem {
    
    @IBAction private func buttonTouchUpInside(_ sender: UIButton) {
        
        guard let data = data else { return }
        if let indexAction = data.indexAction,
           let index = index {
            indexAction(index)
        } else {
            data.action?()
            guard let nextStepData = nextStepData else { return }
            data.nextStepAction?(nextStepData)
        }
    }
}
