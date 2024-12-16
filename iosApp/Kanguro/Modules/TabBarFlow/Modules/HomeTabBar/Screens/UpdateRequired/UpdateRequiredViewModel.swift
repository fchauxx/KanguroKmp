//
//  UpgradeRequiredViewModel.swift
//  Kanguro
//
//  Created by Rodrigo Okido on 18/10/22.
//

import UIKit
import Resolver
import Combine
import KanguroAnalyticsDomain

class UpdateRequiredViewModel {
    
    // MARK: - Dependencies
    @LazyInjected var analytics: KanguroAnalyticsModuleProtocol

    // MARK: - Published Properties
    @Published var state: DefaultViewState = .started
}

// MARK: - Public Methods
extension UpdateRequiredViewModel {
    
    func openAppStore() {
        if let url = AppURLs.appleStore.url {
            UIApplication.shared.open(url)
        }
    }
}
