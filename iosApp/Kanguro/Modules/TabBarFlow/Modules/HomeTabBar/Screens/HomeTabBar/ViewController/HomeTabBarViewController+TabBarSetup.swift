import SwiftUI
import KanguroChatbotData

extension HomeTabBarViewController {
    
    @available(*, deprecated, message: "addTabBarItems function is deprecated since Renters launch, use addRentersTabBarItems instead")
    func addTabBarItems() {
        //first item
        guard let dashboardCoordinator else { return }
        dashboardCoordinator.navigation = createNavigation()
        dashboardCoordinator.logoutAction = logoutAction
        dashboardCoordinator.oldPetPolicies = viewModel.petPoliciesSortedByPetId
        dashboardCoordinator.blockedAction = goToBlockedUserAction
        let dashboardTabBarItem = createTabBarMenuItem(type: .dashboard, coordinator: dashboardCoordinator)
        dashboardCoordinator.didTapUpdatePetPictureAction = { [weak self] in
            guard let self else { return }
            self.changedPetPicture = true
            self.viewModel.getPets(showLoading: true)
            self.viewModel.getAllPetPolicies()
        }
        
        //second item
        let coverageCoordinator = CoverageCoordinator(navigation: createNavigation(),
                                                      policies: viewModel.petPoliciesSortedByPetId, blockedAction: goToBlockedUserAction)
        let coverageTabBarItem = createTabBarMenuItem(type: .coverage, coordinator: coverageCoordinator)
        coverageCoordinator.didTapUpdatePetPictureAction = { [weak self] in
            guard let self else { return }
            self.changedPetPicture = true
            self.viewModel.getPets(showLoading: true)
            self.viewModel.getAllPetPolicies()
        }
        
        //third item
        var chatItem: TabBarMenuItem?
        if let chatbotCoordinator {
            chatbotCoordinator.navigation = createNavigation()
            chatItem = createTabBarMenuItem(type: .bot, coordinator: chatbotCoordinator)
            chatbotCoordinator.didTapGoToDashboardAction = { [weak self] in
                guard let self = self else { return }
                self.tapMenu(menuItem: dashboardTabBarItem)
            }
        }
        
        
        //fourth item
        let cloudCoordinator = CloudCoordinator(navigation: createNavigation())
        let cloudItem = createTabBarMenuItem(type: .cloud, coordinator: cloudCoordinator)
        
        //fifth item
        let moreCoordinator = MoreCoordinator(
            navigation: createNavigation(),
            policies: viewModel.petPoliciesSortedByPetId,
            productType: .petProduct,
            logoutAction: logoutAction,
            didTapContactUsAction: {
                guard let chatItem else { return }
                self.tapMenu(menuItem: chatItem)
            }
        )
        let moreItem = createTabBarMenuItem(type: .more, coordinator: moreCoordinator)
        
        tabBarStackView.layoutIfNeeded()
        viewModel.deactiveItems()
        viewModel.didSetupTabBar = true
        
        addTabBarMenuItems([dashboardTabBarItem, coverageTabBarItem, chatItem, cloudItem, moreItem])
        
        if let deepLink = viewModel.deepLinkType {
            handleDeepLink(deepLink)
        } else {
            if changedPetPicture {
                changedPetPicture = false
                let selected = viewModel.selected?.tabBarMenuItemView.type
                let selectedItem = (selected == .dashboard) ? dashboardTabBarItem : coverageTabBarItem
                hideLoadingView { [weak self] in
                    guard let self else { return }
                    self.tapMenu(menuItem: selectedItem)
                }
            } else {
                hideLoadingView { [weak self] in
                    guard let self else { return }
                    self.tapMenu(menuItem: dashboardTabBarItem)
                }
            }
        }
    }
}
