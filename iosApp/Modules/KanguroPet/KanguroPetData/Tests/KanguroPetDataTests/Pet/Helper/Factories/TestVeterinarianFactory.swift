import Foundation
import KanguroSharedData
import KanguroSharedDomain
import KanguroPetDomain
import KanguroPetData

struct TestVeterinarianFactory {

    static func makeRemoteVeterinarian(
        id: Int = 71,
        clinicName: String = "Clinic Albert Einsten",
        veterinarianName: String = "Bob",
        email: String = "bob@vetmail.com"
    ) -> RemoteVeterinarian {
        RemoteVeterinarian(id: id,
                           clinicName: clinicName,
                           veterinarianName: veterinarianName,
                           email: email)
    }

    static func makeVeterinarian(
        id: Int = 71,
        clinicName: String = "Clinic Albert Einsten",
        veterinarianName: String = "Bob",
        email: String = "bob@vetmail.com"
    ) -> Veterinarian {
        Veterinarian(id: id,
                     clinicName: clinicName,
                     veterinarianName: veterinarianName,
                     email: email)
    }
}
