import UIKit

enum ButtonViewStyle {
    
    case primary
    case secondary
    case tertiary
    case quaternary
    case underlined(color: UIColor)
    case outlined
    
    var textStyle: TextStyle {
        switch self {
        case .primary:
            return TextStyle(color: .white, weight: .black, size: .p16, alignment: .center)
        case .secondary, .quaternary:
            return TextStyle(color: .secondaryDarkest, weight: .bold, size: .p16, alignment: .center)
        case .tertiary:
            return TextStyle(color: .tertiaryDarkest, weight: .bold, size: .p16, alignment: .center)
        case .underlined(let color):
            return TextStyle(color: color, weight: .bold, alignment: .center, underlined: true)
        case .outlined:
            return TextStyle(color: .secondaryDarkest, weight: .black, size: .p12, alignment: .center)
        }
    }
}

// MARK: - Public Methods
class CustomButton: UIButton {
    
    // MARK: - Stored Properties
    private var textBuilder = TextBuilder()
    var loader: UIActivityIndicatorView?
    var originalText: String = ""
    var style: ButtonViewStyle = .primary
    var icon: UIImage? = UIImage(named: "ic-paw")
    
    // MARK: - Actions
    private var onTapAction: SimpleClosure = {}
}

// MARK: - Public Methods
extension CustomButton {
    
    func set(title: String, style: ButtonViewStyle = .primary) {
        setAttributedTitle(textBuilder.buildText(text: title, style: style.textStyle), for: .normal)
        originalText = title
        set(style: style)
    }
    
    func set(style: ButtonViewStyle) {
        
        self.style = style
        switch style {
        case .primary:
            backgroundColor = .secondaryDarkest
            tintColor = .white
            cornerRadius = 10
            setImage()
        case .secondary:
            backgroundColor = .white
            tintColor = .secondaryDarkest
            cornerRadius = 10
            borderWidth = 1
            borderColor = style.textStyle.color
        case .tertiary:
            backgroundColor = .clear
            borderWidth = 1
            borderColor = style.textStyle.color
            cornerRadius = 8
        case .quaternary:
            backgroundColor = .clear
            tintColor = .secondaryDarkest
            borderWidth = 1
            borderColor = .secondaryDarkest
            cornerRadius = 8
            setImage()
        case .underlined(let color):
            tintColor = color
            backgroundColor = .clear
            borderWidth = 0
            borderColor = .clear
        case .outlined:
            backgroundColor = .clear
            borderWidth = 1
            borderColor = style.textStyle.color
            cornerRadius = 14
        }
    }
    
    func onTap(_ action: @escaping SimpleClosure) {
        onTapAction = action
        addTarget(self, action: #selector(onTouchUpInside), for: .touchUpInside)
    }
    
    func isEnabled(_ enabled: Bool) {
        isEnabled = enabled
        alpha = isEnabled ? 1 : 0.5
    }
    
    func isLoading(_ isLoading: Bool) {
        loader?.isHidden = !isLoading
        imageView?.isHidden = isLoading
        isLoading ? startLoader() : stopLoader()
    }
    
    func startLoader() {
        clearTitle()
        imageView?.isHidden = true
        if (loader == nil) { loader = createActivityIndicator() }
        showSpinning()
    }
    
    func stopLoader(showIcon: Bool = true) {
        set(title: originalText, style: style)
        imageView?.isHidden = false
        showIcon ? setImage(icon, for: .normal) : setImage(nil, for: .normal)
        loader?.stopAnimating()
    }
    
    func updateIcon(_ icon: UIImage) {
        self.icon = icon
        setImage()
    }
    
    func updateBackgroundColor(with color: UIColor) {
        backgroundColor = color
    }
}

// MARK: - Private Methods
extension CustomButton {
    
    @objc private func onTouchUpInside(_ sender: Any) {
        onTapAction()
    }
    
    private func setImage() {
        
        self.setImage(icon, for: .normal)
        
        self.semanticContentAttribute = .forceRightToLeft
        if #available(iOS 15.0, *) {
            configuration?.imagePadding = 12
        } else {
            self.imageEdgeInsets = UIEdgeInsets(
                top: 2,
                left: 10,
                bottom: 0,
                right: 0
            )
        }
    }
    
    private func createActivityIndicator() -> UIActivityIndicatorView {
        let loader = UIActivityIndicatorView()
        loader.hidesWhenStopped = true
        loader.color = .lightGray
        return loader
    }
    
    private func showSpinning() {
        guard let loader = loader else { return }
        loader.translatesAutoresizingMaskIntoConstraints = false
        self.addSubview(loader)
        centerActivityIndicatorInButton()
        loader.startAnimating()
    }
    
    private func centerActivityIndicatorInButton() {
        guard let loader = loader else { return }
        loader.centerXAnchor.constraint(equalTo: self.centerXAnchor).isActive = true
        loader.centerYAnchor.constraint(equalTo: self.centerYAnchor).isActive = true
    }
    
    private func clearTitle() {
        setAttributedTitle(NSAttributedString(string: ""), for: .normal)
        setTitle("", for: .normal)
    }
}
