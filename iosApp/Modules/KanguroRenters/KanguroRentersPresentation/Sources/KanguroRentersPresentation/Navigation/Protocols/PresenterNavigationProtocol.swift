import Foundation
import KanguroSharedDomain

protocol PresenterNavigationProtocol {
    
    var backAction: SimpleClosure? { get set }
    var closeAction: SimpleClosure? { get set }
}
