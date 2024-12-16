import UIKit
import KanguroSharedDomain

protocol PreventiveCoveredCardViewProtocol {
    
    var data: KanguroSharedDomain.CoverageData? { get set }
    var isCheckboxSelected: Bool { get }
}
