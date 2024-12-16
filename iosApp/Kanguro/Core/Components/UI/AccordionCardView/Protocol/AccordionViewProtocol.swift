import UIKit

protocol AccordionViewProtocol: BaseView {
    
    // MARK: - Properties
    var isExpanded: Bool { get set }
    
    // MARK: - Actions
    var didTapExpandAction: SimpleClosure { get set }
    
    // MARK: - Methods
    func close()
}
