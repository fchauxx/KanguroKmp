import SwiftUI
import KanguroPetDomain
import KanguroSharedDomain
import KanguroRentersPresentation
import KanguroRentersDomain
import KanguroNetworkDomain
import Resolver

// MARK: - Navigation
extension HomeTabBarViewController {

    func createNavigation() -> UINavigationController {
        let nav = UINavigationController()
        nav.view.frame = contentFrame
        nav.setNavigationBarHidden(true, animated: false)
        return nav
    }

    @discardableResult
    func createTabBarMenuItem(type: TabBarMenuItemType, 
                              coordinator: Coordinator) -> TabBarMenuItem {

        // Create the container and add to the view
        let container = getContainer(navigationView: coordinator.navigation.view)
        self.view.addSubview(container)

        // Create and add the menuView to the stack
        let itemView = TabBarMenuItemView()
        itemView.setup(type: type)

        let menuItem = TabBarMenuItem(container: container,
                                      tabBarMenuItemView: itemView,
                                      coordinator: coordinator)

        menuItem.tabBarMenuItemView.update(action: { [weak self] in
            guard let self = self else { return }
            self.tapMenu(menuItem: menuItem)
        })

        return menuItem
    }

    func addTabBarMenuItems(_ menuItems: [TabBarMenuItem?]) {
        menuItems.forEach { [weak self] menuItem in
            guard let self, let menuItem else { return }
            tabBarStackView.addArrangedSubview(menuItem.tabBarMenuItemView)

            // Add the TabBarMenuItem to the menu list
            viewModel.addTabBarMenuItem(menuItem)
        }

        // Bring stack to front again
        if let menuView = tabBarStackView.superview {
            self.view.bringSubviewToFront(menuView)
            self.view.bringSubviewToFront(tabBarBackgroundView)
        }
    }

    func removeSubviews() {
        tabBarStackView.removellArrangedSubviews()
        viewModel.menus.removeAll()
    }

    func getContainer(navigationView: UIView) -> UIView {
        let container = UIView(frame: contentFrame)
        container.isHidden = true
        container.autoresizingMask = [.flexibleHeight, .flexibleWidth]
        container.addSubview(navigationView)

        return container
    }
}

// MARK: - Setup TabBar
extension HomeTabBarViewController {
    
    func setupTabBar() {
        viewModel.didSetupTabBar = false
        removeSubviews()

        if (shouldShowRenters ?? false) {
            addRentersTabBarItems()
        } else {
            addTabBarItems()
        }
    }
    
    private func makePetScreen(policies: [PetPolicy], 
                               network: NetworkProtocol) -> Coordinator {
        if !policies.isEmpty {
            let petDashboard = DashboardCoordinator(
                type: .pet(policies: policies),
                navigation: createNavigation()
            )

            petDashboard.didTapUpdatePetPictureAction = { [weak self] in
                guard let self else { return }
                self.changedPetPicture = true
                self.viewModel.fetchPetsData(isPetPictureUpdated: true)
            }

            return petDashboard
        } else {
            let view = PetNavigationViewFactory.make(page: .petUpselling, network: network)
            let viewController = UIHostingController(rootView: view)
            return Coordinator(navigation: UINavigationController(rootViewController: viewController))
        }
    }
    
    func addRentersTabBarItems() {

        //Pet only dashboard
        let petDashboardItem = createTabBarMenuItem(
            type: .pet,
            coordinator: makePetScreen(
                policies: viewModel.petPoliciesSortedByPetId,
                network: network
            )
        )

        //Renters only dashboard
        let rentersCoordiantor = RentersCoordinator(
            policies: viewModel.rentersPolicies,
            router: RentersRouter<RentersPage>(),
            navigation: UINavigationController(),
            network: network
        )
        
        let rentersDashboardItem = createTabBarMenuItem(
            type: .renters,
            coordinator: rentersCoordiantor
        )

        //Mixed dashboard
        let mixedDashboardCoordinator = DashboardCoordinator(
            type: .mixed(
                petPolicies: viewModel.petPoliciesSortedByPetId,
                renterPolicies: viewModel.rentersPolicies
            ),
            navigation: createNavigation()
        )

        mixedDashboardCoordinator.didTapActivePolicyAction = { [weak self] activePolicyType in
            guard let self else { return }
            if activePolicyType == .renters {
                self.tapMenu(menuItem: petDashboardItem)
            } else if activePolicyType == .pet {
                self.tapMenu(menuItem: rentersDashboardItem)
            }
        }

        mixedDashboardCoordinator.didTapUpdatePetPictureAction = { [weak self] in
            guard let self else { return }
            self.changedPetPicture = true
            self.viewModel.fetchPetsData(isPetPictureUpdated: true)
        }
        let mixedDashboardItem = createTabBarMenuItem(type: .home, 
                                                      coordinator: mixedDashboardCoordinator)

        //chat page
        var productType: ProductType {
            let rentersPolicies = viewModel.rentersPolicies
            let petPolicies = viewModel.petPoliciesSortedByPetId
            
            if rentersPolicies.isEmpty && !petPolicies.isEmpty {
                return .petProduct
            } else if !rentersPolicies.isEmpty && petPolicies.isEmpty {
                return .rentersProduct
            } else {
                return .petAndRentersProduct
            }
        }
        
        let chatCoordinator = ChatTabCoordinator(product: productType, 
                                                 navigation: UINavigationController(),
                                                 network: network)

        let chatItem = createTabBarMenuItem(type: .chat, 
                                            coordinator: chatCoordinator)

        //More
        let moreCoordinator = MoreCoordinator(
            navigation: createNavigation(),
            policies: viewModel.petPoliciesSortedByPetId,
            productType: productType,
            logoutAction: logoutAction,
            didTapContactUsAction: {
                self.tapMenu(menuItem: chatItem)
            }
        )
        let moreItem = createTabBarMenuItem(type: .more,
                                            coordinator: moreCoordinator)

        tabBarStackView.layoutIfNeeded()
        viewModel.deactiveItems()
        viewModel.didSetupTabBar = true
        
        addTabBarMenuItems([mixedDashboardItem, 
                            petDashboardItem,
                            rentersDashboardItem,
                            chatItem,
                            moreItem])

        if let deepLink = viewModel.deepLinkType {
            handleDeepLink(deepLink)
        } else {
            if changedPetPicture {
                changedPetPicture = false
                let selected = viewModel.selected?.tabBarMenuItemView.type
                let selectedItem = (selected == .home) ? mixedDashboardItem : petDashboardItem
                hideLoadingView { [weak self] in
                    guard let self else { return }
                    self.tapMenu(menuItem: selectedItem)
                }
            } else {
                hideLoadingView { [weak self] in
                    guard let self else { return }
                    self.tapMenu(menuItem: mixedDashboardItem)
                }
            }
        }
    }
}
