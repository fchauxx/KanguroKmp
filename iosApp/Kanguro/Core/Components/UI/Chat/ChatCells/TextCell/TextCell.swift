import UIKit

enum EditingCellStatus {
    
    case basic
    case isEditing
    case isEditable
    
    // MARK: - Computed Properties
    var backgroundColor: UIColor {
        switch self {
        case .basic, .isEditable:
            return .tertiaryLight
        case .isEditing:
            return .primaryLight
        }
    }
    var borderColor: UIColor {
        switch self {
        case .basic:
            return .clear
        case .isEditable:
            return .tertiaryExtraDark
        case .isEditing:
            return .primaryDarkest
        }
    }
    var hasBorder: Bool {
        return self != .basic
    }
    var hasIcon: Bool {
        return self == .isEditable
    }
}

class TextCell: UITableViewCell, ChatCellProtocol {
    
    // MARK: - IB Outlets
    @IBOutlet private var botPhotoImageView: UIImageView!
    @IBOutlet private var botView: UIView!
    @IBOutlet private var botLabel: CustomLabel!
    @IBOutlet private var userView: UIView!
    @IBOutlet private var userLabel: CustomLabel!
    @IBOutlet private var botTopConstraint: NSLayoutConstraint!
    @IBOutlet private var userTopConstraint: NSLayoutConstraint!
    @IBOutlet private var userCellHeightConstraint: NSLayoutConstraint!
    @IBOutlet private var editingIconImageView: UIImageView!
    @IBOutlet private var editingBorderView: UIView!
    
    // MARK: - Stored Properties
    var didAnimate: Bool = false
    private let kanguroNumber: String = "1-888-546-5264"
    
    // MARK: - Computed Properties
    var botTextStyle: TextStyle = {
        TextStyle(color: .secondaryDarkest, size: .p16)
    }()
    var userTextStyle: TextStyle = {
        TextStyle(color: .secondaryDarkest, size: .p16, alignment: .right)
    }()
}

// MARK: - Life Cycle
extension TextCell {
    
    override func prepareForReuse() {
        botLabel.text = ""
        userLabel.text = ""
        didAnimate = false
    }
}

// MARK: - Setup
extension TextCell {
    
    func setup(message: ChatMessage, isFirstMessage: Bool, editingStatus: EditingCellStatus = .basic) {
        
        let text = message.content
        
        switch message.sender {
        case .Bot:
            !text.contains(kanguroNumber) ? botLabel.set(text: text, style: botTextStyle) : botLabel.setHighlightedText(text: text, style: botTextStyle, highlightedText: kanguroNumber, highlightedStyle: TextStyle(color: .tertiaryExtraDark, weight: .bold, size: .p16))
            botView.cornerRadius = 8
            botView.layer.maskedCorners = allCorners
            if isFirstMessage {
                botView.layer.maskedCorners = [.layerMaxXMaxYCorner, .layerMaxXMinYCorner, .layerMinXMaxYCorner]
            }
        case .User:
            userLabel.numberOfLines = 0
            userLabel.set(text: text, style: userTextStyle)
            userView.cornerRadius = 8
            userView.layer.maskedCorners = allCorners
            if isFirstMessage {
                userView.layer.maskedCorners = [.layerMaxXMinYCorner, .layerMinXMaxYCorner, .layerMinXMinYCorner]
            }
            setupUserEditingViews(editingStatus: editingStatus)
        default:
            break
        }
        botView.isHidden = message.sender == .User
        userView.isHidden = message.sender == .Bot
        botPhotoImageView.image = UIImage(named: "javier")
        botPhotoImageView.isHidden = !(isFirstMessage && message.sender == .Bot)
        setTopConstraints(constant: isFirstMessage ? 16 : 4)
        userCellHeightConstraint.priority = (message.sender == .User) ? .required : .defaultLow
    }
}

// MARK: - Private Methods
extension TextCell {
    
    private func setupUserEditingViews(editingStatus: EditingCellStatus) {
        userView.backgroundColor = editingStatus.backgroundColor
        editingBorderView.backgroundColor = editingStatus.borderColor
        editingBorderView.isHidden = !editingStatus.hasBorder
        editingIconImageView.isHidden = !editingStatus.hasIcon
    }
    
    private func setTopConstraints(constant: CGFloat) {
        botTopConstraint.constant = constant
        userTopConstraint.constant = constant
    }
}
