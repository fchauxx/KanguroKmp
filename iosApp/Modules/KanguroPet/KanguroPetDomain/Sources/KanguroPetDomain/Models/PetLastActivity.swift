import Foundation

public struct PetLastActivity {

    public var placeName: String?
    public var rate: Int?
    public var lastVisit: Date?
    public var pet: Pet?

    public init(placeName: String? = nil, rate: Int? = nil, lastVisit: Date? = nil, pet: Pet? = nil) {
        self.placeName = placeName
        self.rate = rate
        self.lastVisit = lastVisit
        self.pet = pet
    }
}
