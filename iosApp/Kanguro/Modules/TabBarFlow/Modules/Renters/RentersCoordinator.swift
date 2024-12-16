//
//  RentersCoordinator.swift
//  Kanguro
//
//  Created by Mateus Vagner on 25/04/24.
//

import Foundation
import SwiftUI
import KanguroNetworkDomain
import KanguroPetDomain
import KanguroRentersDomain
import KanguroSharedDomain
import KanguroRentersPresentation

class RentersCoordinator: Coordinator {
    
    private let network: NetworkProtocol
    private let router: RentersRouter<RentersPage>
    private let policies: [RenterPolicy]
    
    init(
        policies: [RenterPolicy],
        router: RentersRouter<RentersPage>,
        navigation: UINavigationController,
        network: NetworkProtocol
    ) {
        self.network = network
        self.router = router
        self.policies = policies
        
        super.init(navigation: navigation)
    }
    
    override func start() {      
        let tabRenters = if !policies.isEmpty {
            RentersNavigationViewFactory.make(
                page: .rentersDashboard(renterPolicies: policies),
                network: network, 
                router: router
            )
        } else {
            RentersNavigationViewFactory.make(
                page: .rentersUpselling,
                network: network,
                router: router
            )
        }
        
        let rentersView = UIHostingController(rootView: tabRenters)
        
        navigation.isNavigationBarHidden = true
        navigation.pushViewController(rentersView, animated: false)
    }
     
    override func backToRoot() {
        router.popToRoot()
    }
}
