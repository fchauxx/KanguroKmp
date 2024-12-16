import KanguroNetworkDomain
import KanguroSharedDomain

public struct ChatbotSessionMapper: ModelMapper {
    public typealias T = String

    public init() { }

    public static func map<T>(_ input: some Codable) throws -> T {
        guard let input: RemoteSessionId = input as? RemoteSessionId else {
            throw RequestError(errorType: .couldNotMap, errorMessage: "Could not map")
        }

        guard let result = input.sessionId as? T else {
            throw RequestError(errorType: .couldNotMap, errorMessage: "Could not map")
        }
        return result
    }
}
