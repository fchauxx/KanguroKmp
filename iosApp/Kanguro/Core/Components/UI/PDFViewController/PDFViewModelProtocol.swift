import UIKit

protocol PDFViewModelProtocol {
    
    // MARK: - Published Properties
    var statePublisher: Published<DefaultViewState>.Publisher { get }
    
    // MARK: - Stored Properties
    var data: Data? { get }
    var requestError: String { get }
}
