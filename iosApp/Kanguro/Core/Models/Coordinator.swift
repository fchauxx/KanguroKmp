import UIKit

class Coordinator {
    
    // MARK: - Stored Properties
    var navigation: UINavigationController
    
    // MARK: - Initializers
    init(navigation: UINavigationController) {
        self.navigation = navigation
    }
    
    // MARK: - Methods
    func start() { }

    func back() {
        navigation.popViewController(animated: true)
    }
    
    func backToRoot() {
        navigation.popToRootViewController(animated: true)
    }
}
