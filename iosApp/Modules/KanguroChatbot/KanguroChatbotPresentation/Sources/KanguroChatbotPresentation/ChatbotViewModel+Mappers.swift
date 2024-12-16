import Foundation
import KanguroDesignSystem
import KanguroChatbotDomain

extension ChatbotViewModel {

    func mapChatbotStep(step: ChatbotStep) -> [String] {

        var chatMessages: [String] = []
        for message in step.botMessages {
            guard let message: String = message as? String else { continue }
            chatMessages.append(message)
        }
        return chatMessages
    }

    func mapChatbotExternalFlow(step: ChatbotStep) {
        isOnExternalFlow = true

        guard let externalFlowAction = step.expectedFlowAction,
              let delegate else { return }
        
        switch externalFlowAction {
        case .external(flow: let flow):
            currentFlow = flow
            delegate.didAskExternalFlow(flow)
        case .finish:
            currentFlow = nil
            delegate.didFinished()
            break
        }
    }

    func mapChatbotInputType(step: ChatbotStep) -> ChatMessageInputType? {
        isOnExternalFlow = false

        guard let stepInputType = step.expectedUserResponseType else { return nil }
        
        switch stepInputType {
        case .text:
            return .freeText
        case .date(constraints: let constraints):
            #warning("Aplicar o caso de ter um range fechado de datas (min e max date).Refactor datepicker component")
            if constraints.minDate == nil && constraints.maxDate == nil {
                return .anyDate
            } else if constraints.minDate == nil {
                return .pastDate
            } else {
                return .futureDate
            }
        case .singleChoice(options: let options):
            var userChoices: [MultipleChoiceViewData] = []
            for choice in options.options {
                userChoices.append(MultipleChoiceViewData(choice: choice.label, action: {
                    self.userResponse(choice.label, responseId: choice.id)
                }))
            }
            self.userChoices = userChoices
            return .choiceButton
        case .video:
            return nil
        }
    }
}
