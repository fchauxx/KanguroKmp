import UIKit

class CustomSegmentedControl: BaseView, NibOwnerLoadable {
    
    // MARK: - IB Outlets
    @IBOutlet var backgroundView: UIView!
    @IBOutlet var segmentedControl: UISegmentedControl!
    
    // MARK: - Stored Properties
    private var textBuilder = TextBuilder()
    var defaultTitleColor: UIColor = .secondaryLight
    private var selectedTitleColor: UIColor = .white
    
    // MARK: - Computed Properties
    var selectedIndex: Int {
        get {
            segmentedControl.selectedSegmentIndex
        }
        set (index) {
            segmentedControl.selectedSegmentIndex = index
        }
    }
    var numberOfSegments: Int {
        segmentedControl.numberOfSegments
    }
    
    // MARK: - Actions
    var didChangeSegmentedIndexAction: SimpleClosure = {}
    
    // MARK: - Initializers
    required init?(coder: NSCoder) {
        super.init(coder: coder)
        self.loadNibContent()
    }
}

// MARK: - Life Cycle
extension CustomSegmentedControl {
    
    override func layoutSubviews() {
        super.layoutSubviews()
        setupLayout()
    }
}

// MARK: - Setup
extension CustomSegmentedControl {
    
    private func setupLayout() {
        setupSegmentedControlLayout()
    }
    
    private func setupSegmentedControlLayout() {
        let defaultTitleTextAttributes = textBuilder.buildAttributedStyle(style: TextStyle(color: defaultTitleColor))
        let selectedTitleTextAttributes = textBuilder.buildAttributedStyle(style: TextStyle(color: selectedTitleColor))
        segmentedControl.setTitleTextAttributes(defaultTitleTextAttributes, for: .normal)
        segmentedControl.setTitleTextAttributes(selectedTitleTextAttributes, for: .selected)
        
        segmentedControl.layer.backgroundColor = UIColor.white.cgColor
        segmentedControl.setDividerImage(UIImage().withTintColor(.clear), forLeftSegmentState: .normal, rightSegmentState: .normal, barMetrics: .default)
    }
}

// MARK: - Public Methods
extension CustomSegmentedControl {
    
    func update(borderColor: UIColor) {
        backgroundView.borderColor = borderColor
    }
    
    func update(defaultColor: UIColor, selectedColor: UIColor) {
        defaultTitleColor = defaultColor
        selectedTitleColor = selectedColor
    }
    
    func update(selectedTint: UIColor) {
        segmentedControl.selectedSegmentTintColor = selectedTint
    }
    
    func set(titles: [String]) {
        if numberOfSegments > titles.count { return }
        for index in 0..<numberOfSegments {
            segmentedControl.setTitle(titles[index], forSegmentAt: index)
        }
    }
}

// MARK: - IB Actions
extension CustomSegmentedControl {
    
    @IBAction func segmentedControlValueChanged(_ sender: UISegmentedControl) {
        didChangeSegmentedIndexAction()
    }
}
