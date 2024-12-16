import Foundation

public struct RemoteChatbotUserResponseParameters: Encodable {

    private enum CodingKeys: String, CodingKey {
        case input
    }

    public var input: Any

    public init(_ input: Any) {
        self.input = input
    }

    public func encode(to encoder: Encoder) throws {
        var container = encoder.container(keyedBy: CodingKeys.self)
        #warning("add other cases if needed")
        switch input {
        case let value as String:
            try container.encode(value, forKey: .input)
        case let value as Int:
            try container.encode(value, forKey: .input)
        case let value as [Int]:
            try container.encode(value, forKey: .input)
        default:
            throw EncodingError.invalidValue(input, EncodingError.Context(codingPath: [CodingKeys.input], debugDescription: "Unsupported type for input"))
        }
    }
}

