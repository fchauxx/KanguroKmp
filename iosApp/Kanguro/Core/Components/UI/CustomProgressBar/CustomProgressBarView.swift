import UIKit

class CustomProgressBarView: BaseView, NibOwnerLoadable {
    
    // MARK: - IB Outlets
    @IBOutlet private var bgView: UIView!
    @IBOutlet private var bgViewHeight: NSLayoutConstraint!
    @IBOutlet private var progressViewWidth: NSLayoutConstraint!
    @IBOutlet private var progressView: UIView!
    
    // MARK: - Stored Properties
    private var progressPercent: CGFloat?
    private var color: UIColor?
    private var progressViewWidthConstraint: NSLayoutConstraint?
    
    // MARK: - Computed Properties
    private var progressViewHeight: CGFloat {
        return progressView.frame.height
    }
    
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
extension CustomProgressBarView {
    
    private func setupLayout() {
        setupBgView()
        setupProgressView()
    }
    
    private func setupBgView() {
        bgView.setAsCircle()
    }
    
    private func setupProgressView() {
        if let currentConstraint = progressViewWidthConstraint {
            NSLayoutConstraint.deactivate([currentConstraint])
        }
        
        progressViewWidthConstraint = progressView.widthAnchor.constraint(equalTo: self.widthAnchor, multiplier: progressPercent ?? 0)
        if let progressViewWidthConstraint = progressViewWidthConstraint {
            NSLayoutConstraint.activate([progressViewWidthConstraint])
        }
        progressView.backgroundColor = color
        progressView.setAsCircle()
    }
}

// MARK: - Public Methods
extension CustomProgressBarView {
    
    func setupProgressBar(progressPercent: CGFloat, color: UIColor) {
        self.progressPercent = progressPercent
        self.color = color
        setupLayout()
    }
}
