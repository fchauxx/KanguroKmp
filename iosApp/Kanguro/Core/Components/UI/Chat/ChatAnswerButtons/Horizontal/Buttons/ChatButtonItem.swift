import UIKit

enum ChatButtonItemStyle {
    
    case `default`
    case bolded
    
    var textStyle: TextStyle {
        let weight: TextBuilderFontWeight = (self == .bolded) ? .bold : .regular
        return TextStyle(color: .secondaryDarkest, weight: weight, size: .p16, alignment: .center)
    }
}

class ChatButtonItem: BaseView, NibOwnerLoadable {
    
    // MARK: - IB Outlets
    @IBOutlet private var button: UIButton!
    
    // MARK: - Stored Properties
    var data: ChatButtonItemData?
    var nextStepData: NextStepParameters?
    
    // MARK: - Actions
    var didTapButtonAction: NextStepClosure = { _ in }
    
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
extension ChatButtonItem {
    
    func setupButton() {
        guard let title = data?.title,
              let action = data?.nextStepAction,
              let style = data?.style.textStyle else { return }
        let textBuilder = TextBuilder()
        button.setAttributedTitle(textBuilder.buildText(text: title, style: style), for: .normal)
        didTapButtonAction = action
        button.backgroundColor = .neutralBackground
    }
}

// MARK: - Public Methods
extension ChatButtonItem {
    
    func update(data: ChatButtonItemData, nextStepData: NextStepParameters?) {
        self.data = data
        self.nextStepData = nextStepData
        setupButton()
    }
}

// MARK: - IB Actions
extension ChatButtonItem {
    
    @IBAction private func buttonTouchUpInside(_ sender: UIButton) {
        
        guard let data = data else { return }
        data.action?()
        data.nextStepAction?(nextStepData)
    }
}
