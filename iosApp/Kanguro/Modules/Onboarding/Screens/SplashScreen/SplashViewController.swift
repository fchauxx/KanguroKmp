import UIKit

class SplashViewController: UIViewController {

    // MARK: - Dependencies
    private var viewModel: SplashViewModel?

    // MARK: - IB Outlets
    @IBOutlet var kanguroImageView: UIImageView!
    
    // MARK: - Stored Properties
    private var didAnimate = false
    var deepLinkType: DeepLink?
    
    // MARK: - Action
    var didFinishAnimationAction: BoolClosure = {_ in }

    func setupWith(viewModel: SplashViewModel) {
        self.viewModel = viewModel
    }
}

// MARK: - Life Cycle
extension SplashViewController {
    
    override func viewDidAppear(_ animated: Bool) {
        super.viewDidAppear(animated)
        if didAnimate { return }
        
        guard let viewModel else { return }

        viewModel.checkTokenAndNavigate { [weak self] shouldNavigateToLogin in
            guard let self else { return }
            self.animate(shouldNavigateToLogin: shouldNavigateToLogin)
        }
    }
}

// MARK: - Private Methods
extension SplashViewController {

    private func animate(shouldNavigateToLogin: Bool) {
        let size = 190.0
        
        Timer.scheduledTimer(withTimeInterval: 2, repeats: false) { [weak self] _ in
            UIView.animate(withDuration: 1) { [weak self] in
                guard let self = self else { return }
                self.kanguroImageView.transform = CGAffineTransform(scaleX: size, y: size)
            } completion: { [weak self] _ in
                guard let self = self else { return }
                self.didFinishAnimationAction(shouldNavigateToLogin)
            }
        }
        didAnimate = true
    }
}
