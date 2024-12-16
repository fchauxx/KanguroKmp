import Foundation

public enum RemoteScheduledItemCategoryType: String, Identifiable, Codable {
    
    public var id: UUID {
        return UUID()
    }
    
    case Jewelry
    case FineArtsAndCollectibles
    case Electronics
    case MusicalInstruments
    case DesignerClothingAndAccessories
    case SportsEquipment
    case HighValueAppliances
    case RareBooksAndManuscripts
    case HomeOfficeAndHomeVideoEquipment
    case ElectricScooter
}

public struct RemoteScheduledItemCategory: Codable {

    public var id: RemoteScheduledItemCategoryType
    public var label: String
    
    public init(id: RemoteScheduledItemCategoryType, label: String) {
        self.id = id
        self.label = label
    }
}
