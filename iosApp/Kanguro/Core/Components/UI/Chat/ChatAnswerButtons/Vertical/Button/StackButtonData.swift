import UIKit

struct StackButtonData {

    // MARK: - Stored Properties
    var title: String?
    var type: StackButtonItemType? = .standard
    var image: UIImage?
    var action: SimpleClosure?
    var nextStepAction: NextStepClosure?
    var indexAction: IntClosure?
}
