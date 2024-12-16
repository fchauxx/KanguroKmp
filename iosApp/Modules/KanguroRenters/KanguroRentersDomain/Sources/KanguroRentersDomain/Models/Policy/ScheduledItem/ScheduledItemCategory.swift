import Foundation

public enum ScheduledItemCategoryType: String, Identifiable {
    
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

public struct ScheduledItemCategory: Identifiable, Equatable, Hashable {

    public let id = UUID()
    public var category: ScheduledItemCategoryType
    public var label: String
    
    public init(category: ScheduledItemCategoryType, label: String) {
        self.category = category
        self.label = label
    }
}
