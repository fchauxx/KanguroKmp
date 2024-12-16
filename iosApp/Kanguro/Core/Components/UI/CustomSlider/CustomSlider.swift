import UIKit

class CustomSlider: UIView, NibOwnerLoadable {
    
    // MARK: - IB Outlets
    @IBOutlet private var titleLabel: CustomLabel!
    @IBOutlet private var backgroundView: UIView!
    @IBOutlet private var purpleBar: UIView!
    @IBOutlet private var stackView: UIStackView!
    @IBOutlet private var stackMargins: NSLayoutConstraint!
    @IBOutlet private var draggingView: UIView!
    @IBOutlet private var purpleBarWidth: NSLayoutConstraint!
    @IBOutlet private var buttonViewPosition: NSLayoutConstraint!
    
    // MARK: - Stored Properties
    var feedbackRate: Int = 5
    private var circleSize: CGFloat = 10
    private var data: CustomSliderData?
    
    // MARK: - Computed Properties
    var currentPercent: CGFloat {
        (purpleBarWidth.constant*100)/backgroundView.frame.width
    }
    var basePercent: CGFloat {
        guard let data = data else { return 0 }
        return 100/CGFloat(data.numberOfSegments)
    }
    var segmentDistance: CGFloat {
        guard let data = data else { return 0 }
        return ((backgroundView.frame.width - draggingView.frame.width) / (CGFloat(data.numberOfSegments)-1).rounded())
    }
    
    // MARK: - Actions
    var didTapCircleButtonAction: IntClosure = { _ in}
    var didChangeRateValueAction: IntClosure = { _ in }
    
    // MARK: - Initializers
    required init?(coder: NSCoder) {
        super.init(coder: coder)
        self.loadNibContent()
    }
}

// MARK: - Life Cycle
extension CustomSlider {
    
    override func layoutSubviews() {
        super.layoutSubviews()
        setupLayout()
    }
}

// MARK: - Setup
extension CustomSlider {
    
    private func setupLayout() {
        setupLabels()
        setupActions()
        setupStackView()
        setupInitialPositions()
        setupDraggingView()
    }
    
    private func setupLabels() {
        guard let wordsList = data?.wordsList else { return }
        titleLabel.set(text: wordsList[feedbackRate-1],
                       style: TextStyle(color: .secondaryDarkest, weight: .bold, size: .h32, font: .raleway))
    }
    
    private func setupActions() {
        didTapCircleButtonAction = { [weak self] index in
            guard let self = self else { return }
            self.updateFeedbackRateValue(index: index)
            self.updateButtonPosition(index: CGFloat(index))
            self.setupLabels()
            self.updatePurpleBar()
        }
    }
    
    private func setupStackView() {
        guard let numberOfSegments = data?.numberOfSegments else { return }
        for index in 0..<numberOfSegments {
            let circleButton = CircleButton()
            circleButton.update(tag: index)
            circleButton.update(action: didTapCircleButtonAction)
            stackView.addArrangedSubview(circleButton)
        }
        stackView.layoutIfNeeded()
        let emptySpace = stackView.frame.width - (CGFloat(numberOfSegments)*circleSize)
        let spacing = emptySpace / (CGFloat(numberOfSegments)-1)
        stackView.spacing = spacing
    }
    
    private func setupInitialPositions() {
        guard let data = data else { return }
        let buttonViewWidth = draggingView.frame.width
        let buttonProgress = (self.frame.width - buttonViewWidth)
        if data.numberOfSegments % 2 != 0 {
            buttonViewPosition.constant = buttonProgress
            purpleBarWidth.constant = buttonProgress + (buttonViewWidth/2)
        } else {
            buttonViewPosition.constant = 0
            purpleBarWidth.constant = 0
        }
    }
    
    private func setupDraggingView() {
        draggingView.addGestureRecognizer(UIPanGestureRecognizer(target: self,
                                                                 action: #selector(handleSliderGestures)))
    }
}

// MARK: - Private Methods
extension CustomSlider {
    
    private func updateFeedbackRateValue(index: Int) {
        feedbackRate = index + 1
        didChangeRateValueAction(feedbackRate)
    }
    
    @objc private func handleSliderGestures(_ gesture: UIPanGestureRecognizer) {
        let point = gesture.translation(in: self)
        let position = draggingView.center.x + point.x
        let maxX = self.frame.width - stackMargins.constant
        var translationRange: CGFloat {
            if position < stackMargins.constant {
                return stackMargins.constant } else if position > maxX {
                    return maxX } else {
                        return position
                    }
        }
        draggingView.center = CGPoint(x: translationRange, y: draggingView.center.y)
        gesture.setTranslation(CGPoint.zero, in: self)
        
        if gesture.state == .ended {
            var index = ((currentPercent/basePercent).rounded())
            index = (index > 4) ? 4 : index
            updateButtonPosition(index: index)
            updateFeedbackRateValue(index: Int(index))
            setupLabels()
        }
        updatePurpleBar()
    }
    
    private func updateButtonPosition(index: CGFloat) {
        guard let data = data else { return }
        var index = index
        if index > (CGFloat(data.numberOfSegments)-1) { index = (CGFloat(data.numberOfSegments)-1) }
        draggingView.center = CGPoint(x: index*(segmentDistance.rounded()) + stackMargins.constant + circleSize/2,
                                      y: draggingView.center.y)
        draggingView.layoutIfNeeded()
    }
    
    private func updatePurpleBar() {
        purpleBarWidth.constant = draggingView.center.x-(circleSize/2)
        purpleBar.layoutIfNeeded()
    }
}

// MARK: - Public Methods
extension CustomSlider {
    
    func setupSliderData(_ data: CustomSliderData) {
        self.data = data
    }
}
