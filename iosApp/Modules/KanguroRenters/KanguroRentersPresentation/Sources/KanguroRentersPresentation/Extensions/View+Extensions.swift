import SwiftUI

extension View {
    
    func hideNavigationTabBar() {
        NotificationCenter.default.post(name: .hideTabBar, object: self)
    }
    
    func showNavigationTabBar() {
        NotificationCenter.default.post(name: .showTabBar, object: self)
    }
}
