import KanguroPetDomain
import KanguroSharedDomain
import KanguroNetworkDomain

import Foundation

public struct VeterinariansMapper: ModelMapper {
    public typealias T = [Veterinarian]

    public static func map<T>(_ input: some Codable) throws -> T {
        guard let input: [RemoteVeterinarian] = input as? [RemoteVeterinarian] else {
            throw RequestError(errorType: .couldNotMap, errorMessage: "Could not map")
        }
        let vets: [Veterinarian] = try input.map {
            try VeterinarianMapper.map($0)
        }
        guard let result: T = vets as? T else {
            throw RequestError(errorType: .couldNotMap, errorMessage: "Could not map")
        }
        return result
    }
}

public struct VeterinarianMapper: ModelMapper {
    public typealias T = Veterinarian

    public static func map<T>(_ input: some Codable) throws -> T {
        guard let input: RemoteVeterinarian = input as? RemoteVeterinarian else {
            throw RequestError(errorType: .couldNotMap, errorMessage: "Could not map")
        }

        let vet = Veterinarian(id: input.id,
                               clinicName: input.clinicName,
                               veterinarianName: input.veterinarianName,
                               email: input.email)
        guard let result: T = vet as? T else {
            throw RequestError(errorType: .couldNotMap, errorMessage: "Could not map")
        }
        return result
    }
}
