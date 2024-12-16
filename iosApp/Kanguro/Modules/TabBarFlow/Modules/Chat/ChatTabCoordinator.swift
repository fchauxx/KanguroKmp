//
//  ChatTabCoordinator.swift
//  Kanguro
//
//  Created by Mateus Vagner on 13/03/24.
//

import Foundation
import SwiftUI
import KanguroNetworkDomain
import KanguroPetDomain
import KanguroRentersDomain
import KanguroSharedDomain

class ChatTabCoordinator: Coordinator {
    
    private let network: NetworkProtocol
    private let product: ProductType
    
    init(product: ProductType, navigation: UINavigationController, network: NetworkProtocol) {
        self.network = network
        self.product = product
        super.init(navigation: navigation)
    }
    
    override func start() {
        let tabBarChat = RentersNavigationViewFactory.make(
            page: .tabBarChat(product: product),
            network: network,
            navigation: navigation
        )
        
        let chatView = UIHostingController(rootView: tabBarChat)
        
        navigation.isNavigationBarHidden = true
        
        navigation.pushViewController(chatView, animated: false)
    }
}
