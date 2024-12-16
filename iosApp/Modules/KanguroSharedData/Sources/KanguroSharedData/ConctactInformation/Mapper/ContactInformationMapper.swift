import Foundation
import KanguroSharedDomain
import KanguroNetworkDomain

public struct ContactInformationMapper: ModelMapper {
    public typealias T = [ContactInformation]

    public static func map<T>(_ input: some Codable) throws -> T {
        guard let input: [RemoteContactInformation] = input as? [RemoteContactInformation] else {
            throw RequestError(errorType: .couldNotMap, errorMessage: "Could not map")
        }

        let contactInformationArray: [ContactInformation] = try input.map {
            try SingleContactInformationMapper.map($0)
        }

        guard let result: T = contactInformationArray as? T else {
            throw RequestError(errorType: .couldNotMap, errorMessage: "Could not map")
        }

        return result
    }
}

public struct SingleContactInformationMapper: ModelMapper {
    public typealias T = ContactInformation

    public static func map<T>(_ input: some Codable) throws -> T {
        guard let input: RemoteContactInformation = input as? RemoteContactInformation else {
            throw RequestError(errorType: .couldNotMap, errorMessage: "Could not map")
        }

        guard let type = ContactInformationType(rawValue: input.type.rawValue) else {
            throw RequestError(errorType: .couldNotMap, errorMessage: "Invalid type")
        }

        let data = ContactInformationData(
            number: input.data.number,
            text: input.data.text
        )

        let contactInfo = ContactInformation(
            type: type,
            action: input.action,
            data: data
        )

        guard let result: T = contactInfo as? T else {
            throw RequestError(errorType: .couldNotMap, errorMessage: "Could not map")
        }

        return result
    }
}
