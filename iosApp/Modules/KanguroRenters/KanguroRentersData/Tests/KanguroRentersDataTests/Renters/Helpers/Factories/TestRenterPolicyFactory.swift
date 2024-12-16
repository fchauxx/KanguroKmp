import Foundation
import KanguroSharedData
import KanguroSharedDomain
import KanguroRentersDomain
import KanguroRentersData

struct TestRenterPolicyFactory {

    static func makeRemoteRenterPolicy(
        dwellingType: RemoteDwellingType? = .Apartment,
        address: RemoteAddress? = RemoteAddress(state: "RS",
                                                city: "Vacaria",
                                                streetNumber: "77",
                                                streetName: "Street A",
                                                zipCode: "000",
                                                complement: ""),
        status: RemotePolicyStatus? = .ACTIVE,
        createdAt: String? = "2020-08-28T15:07:02+00:00",
        startAt: String? = "2020-08-28T15:07:02+00:00",
        endAt: String? = "2020-08-28T15:07:02+00:00",
        additionalCoverages: [RemoteRenterAdditionalCoverage]? = [RemoteRenterAdditionalCoverage(type: .FraudProtection,
                                                                                                 coverageLimit: 300,
                                                                                                 deductibleLimit: 500)],
        payment: RemotePayment? = RemotePayment(totalFees: 777,
                                                totalPayment: 777,
                                                invoiceInterval: .YEARLY,
                                                totalDiscounts: 100,
                                                totalPaymentWithoutFees: 1000,
                                                firstPayment: 900,
                                                recurringPayment: 900)
    ) -> RemoteRenterPolicy {
        RemoteRenterPolicy(id: "id",
                           dwellingType: dwellingType,
                           address: address,
                           status: status,
                           createdAt: createdAt,
                           startAt: startAt,
                           endAt: endAt,
                           additionalCoverages: additionalCoverages,
                           payment: payment)
    }

    static func makeRenterPolicy(
        dwellingType: RemoteDwellingType = .Apartment,
        address: Address? = Address(state: "RS",
                                    city: "Vacaria",
                                    streetNumber: "77",
                                    streetName: "Street A",
                                    zipCode: "000",
                                    complement: ""),
        status: PolicyStatus? = .ACTIVE,
        createdAt: Date? = Date(timeIntervalSince1970: 1598627222), // "2020-08-28T15:07:02+00:00")
        startAt: Date? = Date(timeIntervalSince1970: 1598627222),
        endAt: Date? = Date(timeIntervalSince1970: 1598627222),
        additionalCoverages: [RenterAdditionalCoverage]? = [RenterAdditionalCoverage(type: .FraudProtection,
                                                                                     coverageLimit: 300,
                                                                                     deductibleLimit: 500)],
        payment: Payment? = Payment(totalFees: 777,
                                    totalPayment: 777,
                                    invoiceInterval: .YEARLY,
                                    totalDiscounts: 100,
                                    totalPaymentWithoutFees: 1000,
                                    firstPayment: 900,
                                    recurringPayment: 900)
    ) -> RenterPolicy? {
        if let dwellingType = DwellingType(rawValue: dwellingType.rawValue) {
            return RenterPolicy(id: "id",
                                dwellingType: dwellingType,
                                address: address,
                                status: status,
                                createdAt: createdAt,
                                startAt: startAt,
                                endAt: endAt,
                                additionalCoverages: additionalCoverages,
                                payment: payment)
        } else {
            return nil
        }
    }
}
