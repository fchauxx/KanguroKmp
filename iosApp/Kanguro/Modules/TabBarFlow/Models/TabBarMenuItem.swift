import UIKit

class TabBarMenuItem {
    
    // MARK: - Stored Properties
    let container: UIView
    let tabBarMenuItemView: TabBarMenuItemView
    let coordinator: Coordinator
    var isLoaded: Bool = false
    
    // MARK: - Computed Properties
    var hasLastVCLoadedView: Bool {
        return coordinator.navigation.viewControllers.last?.isViewLoaded ?? false
    }
    
    // MARK: Initialization
    init(container: UIView,
         tabBarMenuItemView: TabBarMenuItemView,
         coordinator: Coordinator) {
        
        self.container = container
        self.tabBarMenuItemView = tabBarMenuItemView
        self.coordinator = coordinator
    }
}

// MARK: - Functions
extension TabBarMenuItem {
    
    func activate() {
        let lastViewController = coordinator.navigation.viewControllers.last
        lastViewController?.viewWillAppear(false)
        tabBarMenuItemView.active()
        container.isHidden = false
        lastViewController?.viewDidAppear(false)
    }
    
    func deactive() {
        let lastViewController = coordinator.navigation.viewControllers.last
        lastViewController?.viewWillDisappear(false)
        tabBarMenuItemView.deactive()
        container.isHidden = true
        lastViewController?.viewDidDisappear(false)
    }
    
    func popToRoot(animated: Bool = true) {
        coordinator.backToRoot()
    }
}

// MARK: - Static Methods
extension TabBarMenuItem: Equatable {
    static func == (lhs: TabBarMenuItem, rhs: TabBarMenuItem) -> Bool {
        return lhs.container == rhs.container &&
        lhs.tabBarMenuItemView == rhs.tabBarMenuItemView
    }
}
