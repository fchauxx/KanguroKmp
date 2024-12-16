import Foundation
import KanguroSharedData
import KanguroSharedDomain
import KanguroPetDomain
import KanguroPetData

struct TestPetVetDirectPaymentParametersFactory {

    static func makeRemotePetVetDirectPaymentParameters(
        petId: Int? = 99,
        type: RemoteClaimType? = .Accident,
        invoiceDate: String? = "2020-08-28T15:07:02+00:00",
        veterinarianEmail: String? = "bob@email.com",
        veterinarianName: String? = "Bob",
        veterinarianClinic: String? = "Bob clinic's",
        description: String = "Some description",
        amount: CGFloat? = 7777,
        veterinarianId: Int? = 10,
        pledgeOfHonor: String? = "pledge",
        pledgeOfHonorExtension: String? = "pledge-extension"
    ) -> RemotePetVetDirectPaymentParameters {
        RemotePetVetDirectPaymentParameters(petId: petId,
                                            type: type,
                                            invoiceDate: invoiceDate,
                                            veterinarianEmail: veterinarianEmail,
                                            veterinarianName: veterinarianName,
                                            veterinarianClinic: veterinarianClinic,
                                            description: description,
                                            amount: amount,
                                            veterinarianId: veterinarianId,
                                            pledgeOfHonor: pledgeOfHonor,
                                            pledgeOfHonorExtension: pledgeOfHonorExtension)
    }

    static func makePetVetDirectPaymentParameters(
        petId: Int? = 99,
        type: PetClaimType? = .Accident,
        invoiceDate: Date? = Date(timeIntervalSince1970: 1598627222),
        veterinarianEmail: String? = "bob@email.com",
        veterinarianName: String? = "Bob",
        veterinarianClinic: String? = "Bob clinic's",
        description: String = "Some description",
        amount: CGFloat? = 7777,
        veterinarianId: Int? = 10,
        pledgeOfHonor: String? = "pledge",
        pledgeOfHonorExtension: String? = "pledge-extension"
    ) -> PetVetDirectPaymentParameters {
        PetVetDirectPaymentParameters(petId: petId,
                                      type: type,
                                      invoiceDate: invoiceDate,
                                      veterinarianEmail: veterinarianEmail,
                                      veterinarianName: veterinarianName,
                                      veterinarianClinic: veterinarianClinic,
                                      description: description,
                                      amount: amount,
                                      veterinarianId: veterinarianId,
                                      pledgeOfHonor: pledgeOfHonor,
                                      pledgeOfHonorExtension: pledgeOfHonorExtension)
    }

    static func makePetVetDirectPaymentParametersWithAllNil(
        petId: Int? = nil,
        type: PetClaimType? = nil,
        invoiceDate: Date? = nil,
        veterinarianEmail: String? = nil,
        veterinarianName: String? = nil,
        veterinarianClinic: String? = nil,
        description: String = "",
        amount: CGFloat? = nil,
        veterinarianId: Int? = nil,
        pledgeOfHonor: String? = nil,
        pledgeOfHonorExtension: String? = nil
    ) -> PetVetDirectPaymentParameters {
        PetVetDirectPaymentParameters(petId: petId,
                                      type: type,
                                      invoiceDate: invoiceDate,
                                      veterinarianEmail: veterinarianEmail,
                                      veterinarianName: veterinarianName,
                                      veterinarianClinic: veterinarianClinic,
                                      description: description,
                                      amount: amount,
                                      veterinarianId: veterinarianId,
                                      pledgeOfHonor: pledgeOfHonor,
                                      pledgeOfHonorExtension: pledgeOfHonorExtension)
    }
}

