import Foundation

struct RemotePutPetPictureParameters: Codable {
    var petId: Int
    var petPictureBase64: PetPictureBase64
}
