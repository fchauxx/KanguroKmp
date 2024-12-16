//
//  File.swift
//  
//
//  Created by Mateus Vagner on 25/03/24.
//

import Foundation

public struct AnyCodable: Codable {
    let value: Codable
       
    init<T: Codable>(_ value: T) {
        self.value = value
    }
      
    public func encode(to encoder: Encoder) throws {
        var container = encoder.singleValueContainer()

        switch value {
        case let int as Int:
            try container.encode(int)
        case let string as String:
            try container.encode(string)
        case let bool as Bool:
            try container.encode(bool)
        case let array as [AnyCodable]:
            try container.encode(array)
        case let dictionary as [String: AnyCodable]:
            try container.encode(dictionary)
        default:
            throw EncodingError.invalidValue(
                value,
                EncodingError.Context(
                    codingPath: encoder.codingPath,
                    debugDescription: "Invalid value!"
                )
            )
        }
    }
      
    public init(from decoder: Decoder) throws {
        let container = try decoder.singleValueContainer()
         
        if let intValue = try? container.decode(Int.self) {
            self.init(intValue)
        } else if let stringValue = try? container.decode(String.self) {
            self.init(stringValue)
        } else if let boolValue = try? container.decode(Bool.self) {
            self.init(boolValue)
        } else if let arrayValue = try? container.decode([AnyCodable].self) {
            self.init(arrayValue)
        } else if let dictionaryValue = try? container.decode([String: AnyCodable].self) {
            self.init(dictionaryValue)
        } else {
            throw DecodingError.dataCorrupted(
                .init(
                    codingPath: decoder.codingPath,
                    debugDescription: "AnyCodable value cannot be decoded"
                )
            )
        }
    }
}
