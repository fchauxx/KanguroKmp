//
//  BlockedAccountViewModel.swift
//  Kanguro
//
//  Created by Rodrigo Okido on 23/09/22.
//

import UIKit
import Resolver
import Combine
import KanguroAnalyticsDomain

class BlockedAccountViewModel {
    
    // MARK: - Dependencies
    @LazyInjected var analytics: KanguroAnalyticsModuleProtocol

    // MARK: - Published Properties
    @Published var state: DefaultViewState = .started
    
    // MARK: - Stored Properties
    private let email = "profile.delete.label".localized
    private let supportPhoneNumber = "forgotPassword.phone".localized
}

// MARK: - Analytics
extension BlockedAccountViewModel {
    
    func analyticsLogScreen() {
        analytics.analyticsLogScreen(screen: .BlockedUserAccount)
    }
}

// MARK: - Public Methods
extension BlockedAccountViewModel {
    
    func openEmail() {
        guard let url = URL(string: "mailto:\(email)"),
              UIApplication.shared.canOpenURL(url) else { return }
        UIApplication.shared.open(url)
    }
    
    func callSupport() {
        guard let url = URL(string: "tel://\(supportPhoneNumber)"),
              UIApplication.shared.canOpenURL(url) else { return }
        UIApplication.shared.open(url)
    }
}
