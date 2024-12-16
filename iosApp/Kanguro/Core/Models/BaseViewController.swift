import UIKit
import Combine

open class BaseViewController: UIViewController {
    
    // MARK: - Stored Properties
    private var cancellables: [Cancellable] = []
    private let loadingView = LoadingView(frame: UIScreen.main.bounds)
    private let notificationCenter = NotificationCenter.default
    
    // MARK: - Initializers
    deinit {
        removeObservers()
    }
}

// MARK: - Public Methods
extension BaseViewController {
    
    public func observe<Type>(_ publisher: Published<Type>.Publisher, action: @escaping (Type) -> Void) {
        cancellables.append(publisher.receive(on: DispatchQueue.main).sink { value in
            action(value)
        })
    }
    
    public func removeObservers() {
        for cancellable in cancellables {
            cancellable.cancel()
        }
        cancellables.removeAll()
    }
}

// MARK: - Loading
extension BaseViewController {
    
    func showLoadingView(shouldAnimate: Bool) {
        guard let window = UIApplication.shared.connectedScenes
            .compactMap({ $0 as? UIWindowScene })
            .flatMap({ $0.windows })
            .first(where: { $0.isKeyWindow }) else { return }
        loadingView.setup(shouldAnimate: shouldAnimate)
        DispatchQueue.main.async { [weak self] in
            guard let self = self else { return }
            window.addSubview(self.loadingView)
        }
    }
    
    func hideLoadingView(completion: SimpleClosure? = nil, duration: CGFloat = 1) {
        DispatchQueue.main.asyncAfter(deadline: (.now() + duration)) { [weak self] in
            guard let self else { return }
            self.loadingView.removeFromSuperview()
            completion?()
        }
    }
}

// MARK: - Navigation TabBar
extension BaseViewController {
    
    func hideNavigationTabBar() {
        notificationCenter.post(name: .hideTabBar, object: self)
    }
    
    func showNavigationTabBar() {
        notificationCenter.post(name: .showTabBar, object: self)
    }
}
