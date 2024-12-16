import Foundation

public struct UpdatePetPictureParameters {
    public var petId: Int
    public var petPictureBase64: PetPictureBase64

    public init(
        petId: Int,
        petPictureBase64: PetPictureBase64
    ) {
        self.petId = petId
        self.petPictureBase64 = petPictureBase64
    }
}
