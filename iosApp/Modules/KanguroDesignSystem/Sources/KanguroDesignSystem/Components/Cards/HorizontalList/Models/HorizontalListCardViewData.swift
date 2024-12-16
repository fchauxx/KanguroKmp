import SwiftUI

public struct HorizontalListCardViewData: Identifiable {
    
    public let id = UUID()
    
    let icon: Image
    let title: String
    let subtitle: String
    let statusText: String
    let status: Bool
    let linkText: String
    let tapAction: SimpleClosure
    
    public init(icon: Image,
                title: String,
                subtitle: String,
                statusText: String,
                status: Bool,
                linkText: String,
                tapAction: @escaping SimpleClosure) {
        self.icon = icon
        self.title = title
        self.subtitle = subtitle
        self.statusText = statusText
        self.status = status
        self.linkText = linkText
        self.tapAction = tapAction
    }
}
