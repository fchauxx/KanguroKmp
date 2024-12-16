import Foundation
import KanguroNetworkDomain
import KanguroChatbotDomain
import KanguroSharedDomain

public struct ChatbotDateTypeMapper: ModelMapper {
    public typealias T = ChatbotDateConstraints

    public static func map<T>(_ input: some Decodable & Encodable) throws -> T {
        guard let input: RemoteChatbotDateRange = input as? RemoteChatbotDateRange else {
            throw RequestError(errorType: .couldNotMap, errorMessage: "Could not map")
        }

        var minDate: Date?
        var maxDate: Date?
        if let mappedDate = input.startDate?.date {
            minDate = mappedDate
        }
        if let mappedDate = input.endDate?.date {
            maxDate = mappedDate
        }
        let dateConstraint = ChatbotDateConstraints(minDate: minDate, maxDate: maxDate)
        guard let result: T = dateConstraint as? T else {
            throw RequestError(errorType: .couldNotMap, errorMessage: "Could not map")
        }
        return result
    }
}

/*

 {
    "messages":[
        {
            "content":"¿Cuál es la fecha de nacimiento de tu cónyuge?",
            "sender":"Chatbot",
            "type":"Text"
        }
    ],
    "action":{
        "type":"Date",
        "dateRange":{
        "startDate":null,
        "endDate":"2023-09-22T00:00:00+00:00"
        }
    },
    "id":"RentersOnboardingSpouseBirthdateStep"
 }
 */
