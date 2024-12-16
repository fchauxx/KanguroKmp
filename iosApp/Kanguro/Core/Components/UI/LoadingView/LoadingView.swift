import UIKit
import Lottie

class LoadingView: BaseView, NibOwnerLoadable {
    
    // MARK: - IB Outlets
    @IBOutlet private var bgView: UIView!
    @IBOutlet private var kanguroAnimationView: LottieAnimationView!
    @IBOutlet private var loaderView: UIActivityIndicatorView!
    
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
extension LoadingView {
    
    func setup(shouldAnimate: Bool) {
        shouldAnimate ? setupAnimationView() : setupBasicView()
    }
    
    private func setupBasicView() {
        kanguroAnimationView.isHidden = true
        loaderView.isHidden = false
        bgView.backgroundColor = .neutralLightest
        bgView.alpha = 0.4
        loaderView.setScaleSize(1.5)
    }
    
    private func setupAnimationView() {
        loaderView.isHidden = true
        kanguroAnimationView.isHidden = false
        bgView.backgroundColor = .neutralBackground
        kanguroAnimationView.backgroundColor = .clear
        kanguroAnimationView.loopMode = .loop
        kanguroAnimationView.play()
    }
}
