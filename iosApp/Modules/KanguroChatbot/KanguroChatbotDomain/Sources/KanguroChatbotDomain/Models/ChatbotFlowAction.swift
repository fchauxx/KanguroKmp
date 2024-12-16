import Foundation

public enum ChatbotFlowAction: Equatable {
    case external(flow: ChatbotExternalFlow)
    case finish
}
