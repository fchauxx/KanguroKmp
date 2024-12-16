import UIKit

enum TopicLabelViewStyle {
    
    case regular
    case colored(color: UIColor)
    case highlighted(color: UIColor)
    
    // MARK: - Computed Properties
    var color: UIColor {
        switch self {
        case .regular:
            return .secondaryMedium
        case .colored(let color), .highlighted(let color):
            return color
        }
    }
}

struct TopicLabelViewData {
    
    let title: String?
    let highlightedTitle: String?
    let style: TopicLabelViewStyle?
    
    init(title: String?, highlightedTitle: String? = nil, style: TopicLabelViewStyle? = .regular) {
        self.title = title
        self.highlightedTitle = highlightedTitle
        self.style = style
    }
}

class TopicLabelView: BaseView, NibOwnerLoadable {
    
    // MARK: - IB Outlets
    @IBOutlet private var titleLabel: CustomLabel!
    @IBOutlet private var circleView: UIView!
    
    // MARK: - Stored Properties
    var data: TopicLabelViewData?
    
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
extension TopicLabelView {
    
    private func setupLayout() {
        setupLabels()
        setupViews()
    }
    
    private func setupLabels() {
        guard let data,
              let title = data.title,
              let style = data.style else { return }
        
        let textStyle = TextStyle(color: style.color)
        let boldTextStyle = TextStyle(color: style.color, weight: .bold)
        
        switch style {
        case .regular:
            titleLabel.set(text: title, style: textStyle)
        case .colored:
            titleLabel.set(text: title, style: boldTextStyle)
        case .highlighted:
            guard let highlightedTitle = data.highlightedTitle else { return }
            titleLabel.setHighlightedText(text: title,
                                          style: textStyle,
                                          highlightedText: highlightedTitle,
                                          highlightedStyle: boldTextStyle)
        }
    }
    
    private func setupViews() {
        guard let data,
            let style = data.style else { return }
        circleView.backgroundColor = style.color
    }
}

// MARK: - Public Methods
extension TopicLabelView {
    
    func setup(data: TopicLabelViewData) {
        self.data = data
        setupLayout()
    }
}
