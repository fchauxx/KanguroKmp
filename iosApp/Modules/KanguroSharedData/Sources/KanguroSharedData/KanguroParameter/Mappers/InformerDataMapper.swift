import Foundation
import KanguroSharedDomain
import KanguroNetworkDomain

public struct InformerDataListMapper: ModelMapper {
    public typealias T = [InformerData]

    public static func map<T>(_ input: some Codable) throws -> T {
        guard let input: [RemoteInformerData] = input as? [RemoteInformerData] else {
            throw RequestError(errorType: .couldNotMap, errorMessage: "Could not map")
        }
        let informerDataList: [InformerData] = try input.map {
            try InformerDataMapper.map($0)
        }
        guard let result: T = informerDataList as? T else {
            throw RequestError(errorType: .couldNotMap, errorMessage: "Could not map")
        }
        return result
    }
}

public struct InformerDataMapper: ModelMapper {
    public typealias T = InformerData

    public static func map<T>(_ input: some Codable) throws -> T {
        guard let input: RemoteInformerData = input as? RemoteInformerData else {
            throw RequestError(errorType: .couldNotMap, errorMessage: "Could not map")
        }
        var type: KanguroParameterType? = nil
        var language: Language? = nil
        if let remoteType: RemoteKanguroParameterType = input.type {
            type = KanguroParameterType(rawValue: remoteType.rawValue)
        }
        if let remoteLanguage: RemoteLanguage = input.language {
            language = Language(rawValue: remoteLanguage.rawValue)
        }
        let informerData = InformerData(key: input.key,
                                        value: input.value,
                                        description: input.description,
                                        type: type,
                                        language: language,
                                        isActive: input.isActive)
        guard let result: T = informerData as? T else {
            throw RequestError(errorType: .couldNotMap, errorMessage: "Could not map")
        }
        return result
    }
}
