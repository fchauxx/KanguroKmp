import SwiftUI

public struct UIViewControllerAsView<Content: UIViewController>: UIViewControllerRepresentable {
    
    public typealias UIViewControllerType = Content
    
    public let viewController: Content
    
    public init(viewController: Content) {
        self.viewController = viewController
    }
    
    public func makeUIViewController(context: Context) -> Content {
        return viewController
    }
    
    public func updateUIViewController(_ uiViewController: Content, context: Context) {
        // Update the view controller if needed.
    }
}
