import Foundation
import KanguroSharedDomain
import KanguroNetworkDomain

public struct DonationCausesMapper: ModelMapper {
    public typealias T = [DonationCause]

    public static func map<T>(_ input: some Codable) throws -> T {
        guard let input: [RemoteDonationCause] = input as? [RemoteDonationCause] else {
            throw RequestError(errorType: .couldNotMap, errorMessage: "Could not map")
        }
        let donationCauses: [DonationCause] = try input.map {
            try DonationCauseMapper.map($0)
        }
        guard let result: T = donationCauses as? T else {
            throw RequestError(errorType: .couldNotMap, errorMessage: "Could not map")
        }
        return result
    }
}

public struct DonationCauseMapper: ModelMapper {
    public typealias T = DonationCause

    public static func map<T>(_ input: some Codable) throws -> T {
        guard let input: RemoteDonationCause = input as? RemoteDonationCause else {
            throw RequestError(errorType: .couldNotMap, errorMessage: "Could not map")
        }

        guard let donationType = DonationType(rawValue: input.attributes.cause.rawValue) else {
            throw RequestError(errorType: .couldNotMap, errorMessage: "Could not map")
        }
        let donationCause = DonationCause(id: input.id,
                                          attributes: DonationAttributes(
                                            title: input.attributes.title,
                                            abreviatedTitle: input.attributes.abreviatedTitle,
                                            description: input.attributes.description,
                                            locale: input.attributes.locale,
                                            charityKey: input.attributes.charityKey,
                                            canBeChosenByUser: input.attributes.canBeChosenByUser,
                                            cause: donationType))
        guard let result: T = donationCause as? T else {
            throw RequestError(errorType: .couldNotMap, errorMessage: "Could not map")
        }
        return result
    }
}
