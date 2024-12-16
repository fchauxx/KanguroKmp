import Foundation

public protocol ChatbotExternalFlowDelegate: AnyObject {

    func didReceiveExternalFlowResponse(_ response: Any, chatText: String?)
}
