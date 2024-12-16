import UIKit

struct ChatButtonItemData {

    // MARK: - Stored Properties
    var title: String?
    var style: ChatButtonItemStyle = .`default`
    var action: SimpleClosure?
    var nextStepAction: NextStepClosure?
}
