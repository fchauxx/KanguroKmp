import UIKit
import Lottie

class TabBarMenuItemView: UIView, NibOwnerLoadable {
    
    // MARK: - IBOutlets
    @IBOutlet private var contentView: UIView!
    @IBOutlet private var iconImageView: UIImageView!
    @IBOutlet var label: CustomLabel!
    @IBOutlet private var backgroundAnimationView: LottieAnimationView!
    @IBOutlet var botImageView: UIImageView!
    @IBOutlet private var button: UIButton!
    
    // MARK: - Stored Properties
    var type: TabBarMenuItemType?
    var isActive: Bool = false
    
    // MARK: - Actions
    var didTapMenuAction: SimpleClosure = {}
    
    // MARK: - Initializers
    override init(frame: CGRect) {
        super.init(frame: frame)
        customInit()
    }
    
    required init?(coder: NSCoder) {
        super.init(coder: coder)
        customInit()
    }
    
    private func customInit() {
        Bundle.main.loadNibNamed("TabBarMenuItemView", owner: self, options: nil)
        addSubview(contentView)
        contentView.frame = self.bounds
        contentView.autoresizingMask = [.flexibleWidth, .flexibleHeight]
    }
}

// MARK: - Setup
extension TabBarMenuItemView {
    
    func setup(type: TabBarMenuItemType) {
        self.type = type
        
        (type == .bot) ? setMenuBotItem() : setMenuItem()
    }
    
    func update(action: @escaping SimpleClosure) {
        self.didTapMenuAction = action
    }
}

// MARK: - Public Methods
extension TabBarMenuItemView {
    
    func active() {
        guard let type else { return }
        iconImageView.setAnimatedImage(type.activeIcon, duration: 0.4)
        label.setAnimatedLabel(type.activeColor, duration: 0.4)
        isActive = true
    }
    
    func deactive() {
        guard let type else { return }
        iconImageView.image = type.defaultIcon
        label.textColor = type.defaultColor
        isActive = false
    }
}

// MARK: - Private Methods
extension TabBarMenuItemView {
    
    private func setMenuItem() {
        guard let type else { return }
        let text = type.title
        label.set(text: text, style: TextStyle(color: type.defaultColor, weight: .bold, size: .p10, alignment: .center))
        iconImageView.image = type.defaultIcon
        botImageView.image = UIImage(named: "javier")
    }
    
    private func setMenuBotItem() {
        setBotViews()
        backgroundAnimationView.loopMode = .loop
        backgroundAnimationView.play()
        button.isEnabled = true
    }
    
    private func setBotViews() {
        botImageView.isHidden = false
        backgroundAnimationView.isHidden = false
        iconImageView.isHidden = true
        label.isHidden = true
    }
}

// MARK: - IB Actions
extension TabBarMenuItemView {
    
    @IBAction func menuTouchUpInside(_ sender: UIButton) {
        didTapMenuAction()
    }
}
