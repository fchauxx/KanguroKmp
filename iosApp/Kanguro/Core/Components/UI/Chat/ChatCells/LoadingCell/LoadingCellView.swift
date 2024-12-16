import UIKit
import Lottie

class LoadingCellView: UIView, NibOwnerLoadable {
    
    // MARK: - IB Outlets
    @IBOutlet private var loadingAnimationView: LottieAnimationView!
    
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

// MARK: - Public Methods
extension LoadingCellView {
    
    func startLoader() {
        loadingAnimationView.loopMode = .loop
        DispatchQueue.main.async { [weak self] in
            guard let self else { return }
            self.loadingAnimationView.play()
        }
    }
}
