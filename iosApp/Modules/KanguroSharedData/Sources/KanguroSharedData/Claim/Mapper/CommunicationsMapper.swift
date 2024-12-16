import Foundation
import KanguroSharedDomain
import KanguroNetworkDomain

public struct CommunicationsMapper: ModelMapper {
    public typealias T = [Communication]

    public init() {}
    
    public static func map<T>(_ input: some Codable) throws -> T {
        guard let input: [RemoteCommunication] = input as? [RemoteCommunication] else {
            throw RequestError(errorType: .couldNotMap, errorMessage: "Could not map")
        }
        let communications: [Communication] = try input.map {
            try CommunicationMapper.map($0)
        }
        guard let result: T = communications as? T else {
            throw RequestError(errorType: .couldNotMap, errorMessage: "Could not map")
        }
        return result
    }
}

public struct CommunicationMapper: ModelMapper {
    public typealias T = Communication

    public init() {}

    public static func map<T>(_ input: some Codable) throws -> T {
        guard let input: RemoteCommunication = input as? RemoteCommunication else {
            throw RequestError(errorType: .couldNotMap, errorMessage: "Could not map")
        }
        var type: CommunicationType?
        var sender: CommunicationSender?
        if let remoteType = input.type {
            type = CommunicationType(rawValue: remoteType.rawValue)
        }
        if let remoteSender = input.sender {
            sender = CommunicationSender(rawValue: remoteSender.rawValue)
        }
        var createdAt: Date?
        if let remoteCreatedAt = input.createdAt {
            createdAt = remoteCreatedAt.date
        }

        let communication = Communication(
            id: input.id,
            type: type,
            sender: sender,
            message: input.message,
            fileURL: input.fileURL,
            createdAt: createdAt
        )

        guard let result: T = communication as? T else {
            throw RequestError(errorType: .couldNotMap, errorMessage: "Could not map")
        }
        return result
    }
}
