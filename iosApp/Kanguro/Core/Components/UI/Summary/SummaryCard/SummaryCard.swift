import UIKit

enum SummaryCardType {
    
    case regular
    case statusView
    case image
}

class SummaryCard: BaseView, NibOwnerLoadable {
    
    // MARK: - IB Outlets
    @IBOutlet var leadingTitleLabel: CustomLabel!
    @IBOutlet var traillingTitleLabel: CustomLabel!
    @IBOutlet var statusView: StatusView!
    
    // MARK: - Stored Properties
    var data: SummaryData?
    
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
extension SummaryCard {
    
    override func layoutSubviews() {
        super.layoutSubviews()
        setupLayout()
    }
}

// MARK: - Setup
extension SummaryCard {
    
    private func setupLayout() {
        setupLabels()
        setupSummaryType()
        setupStatusView()
    }
    
    func setupLabels() {
        guard let data = data,
              let leadingTitle = data.leadingTitle else { return }
        leadingTitleLabel.set(text: leadingTitle, style: TextStyle(color: .neutralMedium))
        leadingTitleLabel.setupToFitWidth()
        
        if let traillingTitle = data.traillingTitle {
            traillingTitleLabel.set(text: traillingTitle, style: TextStyle(color: .secondaryDarkest, weight: .bold, size: .p16))
            traillingTitleLabel.isHidden = false
        }
    }
    
    func setupSummaryType() {
        
        guard let data = data,
              let summaryType = data.summaryType else { return }
        switch summaryType {
        case .statusView:
            statusView.isHidden = false
            traillingTitleLabel.isHidden = true
        case .regular:
            statusView.isHidden = true
        default:
            break
        }
    }
    
    func setupStatusView() {
        guard let data = data,
              let claim = data.claim else { return }
        statusView.update(claim: claim)
    }
}

// MARK: - Public Methods
extension SummaryCard {
    
    func update(data: SummaryData) {
        self.data = data
    }
}
