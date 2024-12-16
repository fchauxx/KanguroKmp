import Foundation
import KanguroSharedDomain
import KanguroRentersDomain
import KanguroNetworkDomain

public struct RentersAdditionalPartiesMapper: ModelMapper {
    public typealias T = [AdditionalPartie]
    
    public static func map<T>(_ input: some Codable) throws -> T {
        guard let input: [RemoteAdditionalPartie] = input as? [RemoteAdditionalPartie] else {
            throw RequestError(errorType: .couldNotMap, errorMessage: "Could not map")
        }
        let aditionalParties: [AdditionalPartie] = try input.map {
            try RentersAdditionalPartieMapper.map($0)
        }
        guard let result: T = aditionalParties as? T else {
            throw RequestError(errorType: .couldNotMap, errorMessage: "Could not map")
        }
        return result
    }
}

public struct RentersAdditionalPartieMapper: ModelMapper {
    public typealias T = AdditionalPartie
    
    public static func map<T>(_ input: some Codable) throws -> T {
        guard let input: RemoteAdditionalPartie = input as? RemoteAdditionalPartie else {
            throw RequestError(errorType: .couldNotMap, errorMessage: "Could not map")
        }
        
        var address: Address? = nil
        var additionalPartieType: AdditionalPartieType? = nil
        
        if let remoteAddress = input.address {
            address = Address(state: remoteAddress.state,
                              city: remoteAddress.city,
                              streetNumber: remoteAddress.streetNumber,
                              streetName: remoteAddress.streetName,
                              zipCode: remoteAddress.zipCode,
                              complement: remoteAddress.complement)
        }
        
        if let type: RemoteAdditionalPartieType = input.type {
            additionalPartieType = AdditionalPartieType(rawValue: type.rawValue)
        }
        
        let additionalPartie = AdditionalPartie(id: input.id,
                                                type: additionalPartieType,
                                                fullName: input.fullName,
                                                email: input.email,
                                                birthDate: input.birthDate,
                                                address: address)
        
        guard let result: T = additionalPartie as? T else {
            throw RequestError(errorType: .couldNotMap, errorMessage: "Could not map")
        }
        return result
    }
}
